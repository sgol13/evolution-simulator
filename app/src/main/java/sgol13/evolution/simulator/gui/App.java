package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.simulation.SimulationEngine;
import sgol13.evolution.simulator.simulation.UnboundedMap;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import static java.lang.System.out;
import java.beans.EventHandler;
import java.io.IOException;

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
