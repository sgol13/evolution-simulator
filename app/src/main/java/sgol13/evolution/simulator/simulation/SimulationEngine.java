package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class SimulationEngine {

    private final SimulationConfig config;
    private final IMap map;
    private boolean finishFlag = false;
    private final LinkedList<Animal> animals = new LinkedList<Animal>();

    public SimulationEngine(SimulationConfig config, IMap map) {
        this.config = config;
        this.map = map;
    }

    public void run() {

        initAnimals();

        while (!finishFlag) {
            simulateDay();
        }
    }

    private void simulateDay() {

        removeDead();
        simulateMoving();
        simulateEating();
        simulateReproducing();
        map.placeTwoRandomGrassFields();

        out.print("\033[H\033[2J");
        out.flush();
        out.println(map.getSnapshot());
        out.println(animals.size());

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void removeDead() {

        var it = animals.iterator();
        var deadAnimals = new LinkedList<Animal>();
        while (it.hasNext()) {

            var animal = it.next();

            // check if the animal is dead
            if (animal.getEnergy() <= 0) {
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
                animals.add(animal);
                map.placeAnimalOnRandomField(animal);
            }
        }
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
        animals.addAll(map.placeRandomAnimals(config.initialAnimals));
    }

    public void finish() {
        finishFlag = true;
    }
}
