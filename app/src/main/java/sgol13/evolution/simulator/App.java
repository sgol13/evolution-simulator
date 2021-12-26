package sgol13.evolution.simulator;

import sgol13.evolution.simulator.simulation.Animal;
import sgol13.evolution.simulator.simulation.BoundedMap;
import sgol13.evolution.simulator.simulation.IMap;
import sgol13.evolution.simulator.simulation.SimulationEngine;
import sgol13.evolution.simulator.simulation.UnboundedMap;
import static java.lang.System.out;
import sgol13.evolution.simulator.simulation.Vector2d;

public class App {
    public static void main(String[] args) {

        SimulationConfig config = new SimulationConfig();
        config.mapWidth = 40;
        config.mapHeight = 25;
        config.initialAnimals = 100;
        config.initialGrass = 4;
        config.jungleRatio = 0.2;

        config.startEnergy = 200;
        config.moveEnergy = 1;
        config.plantEnergy = 1;
        config.magicStrategy = true;
        config.defaultDaytime = 200;

        var engine = new SimulationEngine(config, new UnboundedMap(config));
        Thread thread = new Thread(engine);

        thread.start();

        int m = 500;
        while (m-- > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            engine.getSimulationSnapshot();
        }

        engine.finish();

        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
