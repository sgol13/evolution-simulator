package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.HBox;

public class App extends Application {

    private static final String WINDOW_TITLE = "Evolution Simulator";

    @Override
    public void start(Stage primaryStage) throws Exception {

        var leftApp = new HalfApp(false);
        var rightApp = new HalfApp(true);

        var appsBox = new HBox();
        appsBox.setAlignment(Pos.CENTER);
        appsBox.getChildren().addAll(leftApp.getNode(), rightApp.getNode());

        primaryStage.setScene(new Scene(appsBox, 1920, 1080));
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setResizable(false);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.show();
    }

}
