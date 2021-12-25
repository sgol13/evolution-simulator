package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class SimulationEngine {

    private final SimulationConfig configuration;
    private final IMap map;
    private boolean finishFlag = false;
    private final LinkedList<Animal> animals = new LinkedList<Animal>();

    public SimulationEngine(SimulationConfig configuration, IMap map) {
        this.configuration = configuration;
        this.map = map;
    }

    public void run() {

        var an1 = new Animal(map, new Vector2d(3, 3), 10);
        map.place(an1);
        animals.add(an1);

        while (!finishFlag) {

            an1.move();
            out.println(an1.getPosition());

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void finish() {
        finishFlag = true;
    }
}
