package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import javafx.application.Platform;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.gui.SimulationVisualizer;
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

            Platform.runLater(() -> visualizer.update(getSimulationSnapshot()));

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
        var statisticsSnapshot = getStatisticsSnapshot();

        return new SimulationSnapshot(mapSnapshot, statisticsSnapshot);
    }

    private StatisticsSnapshot getStatisticsSnapshot() {

        var snapshot = new StatisticsSnapshot();
        snapshot.setGrassFieldsNum(map.getGrassFieldsNum());
        snapshot.setAverageLifespan(getAverageLifespan());
        animals.forEach(animal -> snapshot.addAnimal(animal));

        return snapshot;
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
}
