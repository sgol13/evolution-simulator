package sgol13.evolution.simulator.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javafx.application.Platform;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.gui.SimulationVisualizer;
import sgol13.evolution.simulator.snapshots.ObservedAnimalSnapshot;
import sgol13.evolution.simulator.snapshots.SimulationSnapshot;
import sgol13.evolution.simulator.snapshots.StatisticsSnapshot;
import static java.lang.System.out;
import java.time.Duration;
import java.time.Instant;

public class SimulationEngine implements Runnable {

    private final SimulationConfig config;
    private final SimulationVisualizer visualizer;
    private final IMap map;
    private boolean finishFlag = false;
    private boolean pauseFlag = true;
    private final LinkedList<Animal> animals = new LinkedList<Animal>();
    private Instant previousTime;
    private double simulationSpeed;
    private long daysCounter = 0;
    private long deathAnimalsCounter = 0;
    private long totalDeathAnimalsLifespan = 0;
    private Animal observedAnimal = null;

    public SimulationEngine(SimulationVisualizer visualizer,
            SimulationConfig config, IMap map) {

        this.visualizer = visualizer;
        this.config = config;
        this.map = map;
        this.simulationSpeed = config.defaultSimulationSpeed;
    }

    @Override
    public void run() {

        initAnimals();
        previousTime = Instant.now();

        while (!finishFlag) {

            simulateDay();

            sendSimulationSnapshot();

            // wait for the next day
            var currentTime = Instant.now();
            var elapsedTime = Duration.between(previousTime, currentTime);
            previousTime = currentTime;

            long dayTime = (long) (config.minSpeedDayTime / simulationSpeed);
            long leftTime = dayTime - elapsedTime.toMillis();
            if (leftTime > 0) {

                try {
                    Thread.sleep(leftTime);
                } catch (InterruptedException e) {
                    // chill out, really no serious consequences if interrupted
                }
            }

            // pause if the flag is set
            synchronized (this) {
                try {
                    while (pauseFlag && !finishFlag)
                        wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            daysCounter++;
        }
    }

    synchronized private void simulateDay() {

        simulateMoving();
        removeDead();
        simulateEating();
        simulateReproducing();
        map.placeTwoRandomGrassFields();
    }

    synchronized private SimulationSnapshot getSimulationSnapshot() {

        var mapSnapshot = map.getMapSnapshot();
        mapSnapshot.setObservedAnimal(observedAnimal);
        var dominantGenotype = findDominantGenotype();
        if (dominantGenotype != null) {
            mapSnapshot.setDominantGenotype(dominantGenotype);
            mapSnapshot.addDominantGenotypePositions(
                    findAllDominantGenotypes(dominantGenotype));
        }
        var statisticsSnapshot = getStatisticsSnapshot();
        var observedAnimalSnapshot = getObservedAnimalSnapshot();

        return new SimulationSnapshot(mapSnapshot,
                statisticsSnapshot, observedAnimalSnapshot);
    }

    private StatisticsSnapshot getStatisticsSnapshot() {

        var snapshot = new StatisticsSnapshot();
        snapshot.setGrassFieldsNum(map.getGrassFieldsNum());
        snapshot.setAverageLifespan(getAverageLifespan());
        animals.forEach(animal -> snapshot.addAnimal(animal));

        return snapshot;
    }

    private Genotype findDominantGenotype() {

        var genotypesCounters = new HashMap<Genotype, Integer>();

        for (var animal : animals) {
            var genotype = animal.getGenotype();
            if (genotypesCounters.containsKey(genotype)) {
                genotypesCounters.put(genotype, genotypesCounters.get(genotype) + 1);
            } else {
                genotypesCounters.put(genotype, 1);
            }
        }

        Map.Entry<Genotype, Integer> maxEntry = null;
        for (var entry : genotypesCounters.entrySet()) {
            if (maxEntry == null ||
                    entry.getValue().compareTo(maxEntry.getValue()) > 0) {

                maxEntry = entry;
            }
        }

        if (maxEntry == null || maxEntry.getValue() < 2)
            return null;

        return maxEntry.getKey();
    }

    private Set<Vector2d> findAllDominantGenotypes(Genotype dominantGenotype) {

        var allDominant = new HashSet<Vector2d>();
        for (var animal : animals)
            if (animal.getGenotype().equals(dominantGenotype))
                allDominant.add(animal.getPosition());

        return allDominant;
    }

    private ObservedAnimalSnapshot getObservedAnimalSnapshot() {

        if (observedAnimal != null)
            return new ObservedAnimalSnapshot(observedAnimal);

        return null;
    }

    private void removeDead() {

        var it = animals.iterator();
        var deadAnimals = new LinkedList<Animal>();
        while (it.hasNext()) {

            var animal = it.next();

            // check if the animal is dead
            if (animal.getEnergy() <= 0) {

                totalDeathAnimalsLifespan += daysCounter - animal.getBirthDay();
                deathAnimalsCounter++;

                map.removeAnimal(animal);
                it.remove();
                deadAnimals.add(animal);

                if (animal == observedAnimal)
                    observedAnimal = null;
            }
        }

        // magic strategy - add 5 new animals if not more than 5 left
        if (config.magicStrategy && animals.size() <= 5) {

            var newAnimals = new LinkedList<Animal>();
            for (var animal : animals)
                newAnimals.add(animal.clone());

            it = deadAnimals.iterator();
            while (it.hasNext() && newAnimals.size() < 5)
                newAnimals.add(it.next());

            for (var animal : newAnimals) {
                animal.eat(config.startEnergy);
                animal.setBirthDay(daysCounter);
                animals.add(animal);
                map.placeAnimalOnRandomField(animal);
            }
        }
    }

    private double getAverageLifespan() {

        if (deathAnimalsCounter == 0)
            return 0;

        return totalDeathAnimalsLifespan / deathAnimalsCounter;
    }

    private void simulateMoving() {
        animals.forEach(animal -> animal.move());
    }

    private int simulateEating() {

        int eatenGrass = 0;
        for (var field : map.getAllFields())
            if (field.doEating())
                eatenGrass++;

        return eatenGrass;
    }

    private void simulateReproducing() {

        for (var field : map.getAllFields()) {

            var newAnimal = field.doReproducing();
            if (newAnimal != null) {
                map.placeAnimal(newAnimal);
                animals.add(newAnimal);
            }
        }
    }

    public void sendSimulationSnapshot() {
        Platform.runLater(() -> visualizer.update(getSimulationSnapshot()));
    }

    private void initAnimals() {

        var newAnimals = map.placeRandomAnimals(config.initialAnimals);
        newAnimals.forEach(animal -> animal.setBirthDay(daysCounter));
        animals.addAll(newAnimals);
    }

    synchronized public void finishSimulation() {
        finishFlag = true;
        notify();
    }

    synchronized public void pauseSimulation() {
        pauseFlag = true;
    }

    synchronized public void resumeSimulation() {
        pauseFlag = false;
        notify();
    }

    public void changeSpeed(double newValue) {
        simulationSpeed = newValue;
    }

    synchronized public void setObservedAnimal(int row, int col) {

        var field = map.getField(new Vector2d(col, config.mapHeight - row - 1));
        var animalsOnPosition = field.getAnimalsGroup();

        if (animalsOnPosition.length > 0) {
            observedAnimal = animalsOnPosition[0];
            sendSimulationSnapshot();
        } else
            observedAnimal = null;
    }

    synchronized public void removeObservedAnimal() {
        observedAnimal = null;
    }
}
