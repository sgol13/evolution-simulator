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
        initGrass();

        while (!finishFlag) {
            simulateDay();
        }
    }

    private void simulateDay() {

        simulateMoving();
        simulateEating();
        simulateReproducing();

        out.print("\033[H\033[2J");
        out.flush();
        out.println(map.getSnapshot());
        out.println(animals.size());

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                map.place(newAnimal);
                animals.add(newAnimal);
            }
        }
    }

    private void initGrass() {

    }

    private void initAnimals() {
        animals.addAll(map.placeRandomAnimals(config.initialAnimals));
    }

    public void finish() {
        finishFlag = true;
    }
}
