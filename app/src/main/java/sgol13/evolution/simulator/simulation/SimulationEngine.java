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

    public void simulateDay() {

        out.print("\033[H\033[2J");
        out.flush();
        out.println(map.getSnapshot());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void initGrass() {}

    public void initAnimals() {}

    public void finish() {
        finishFlag = true;
    }
}
