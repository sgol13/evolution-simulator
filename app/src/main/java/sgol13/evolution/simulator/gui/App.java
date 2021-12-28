package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import sgol13.evolution.simulator.SimulationConfig;
import javafx.scene.*;
import javafx.scene.layout.HBox;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        var leftApp = new HalfApp(false);
        var rightApp = new HalfApp(true);

        var appsBox = new HBox();
        appsBox.setAlignment(Pos.CENTER);
        appsBox.getChildren().addAll(leftApp.getNode(), rightApp.getNode());

        primaryStage.setScene(new Scene(appsBox, 950, 1080));
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }

}
