package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.stage.*;
import sgol13.evolution.simulator.SimulationConfig;
import javafx.scene.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        var config = new SimulationConfig();
        var visualizer = new SimulationVisualizer(config);

        var scene = new Scene(visualizer.getNode(), 950, 1080);
        primaryStage.setX(0);
        primaryStage.setY(0);

        visualizer.start();

        primaryStage.setScene(scene);
        primaryStage.show();

        // visualizer.finish();
    }

}
