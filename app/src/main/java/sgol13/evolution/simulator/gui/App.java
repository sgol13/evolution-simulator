package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.stage.*;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.simulation.SimulationEngine;
import sgol13.evolution.simulator.simulation.UnboundedMap;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import static java.lang.System.out;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {


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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        engine.finishSimulation();

        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
