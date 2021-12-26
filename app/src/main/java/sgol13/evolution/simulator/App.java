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

        var engine = new SimulationEngine(config, new UnboundedMap(config));
        engine.run();
    }
}
