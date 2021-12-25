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
        config.mapWidth = 8;
        config.mapHeight = 5;

        var engine = new SimulationEngine(config, new BoundedMap(config));
        engine.run();
    }
}
