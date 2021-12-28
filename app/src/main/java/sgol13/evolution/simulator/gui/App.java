package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.HBox;

public class App extends Application {

    private static final String WINDOW_TITLE = "Evolution Simulator";
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;

    @Override
    public void start(Stage primaryStage) {

        var leftApp = new HalfApp(false);
        var rightApp = new HalfApp(true);

        var appsBox = new HBox();
        appsBox.getChildren().addAll(leftApp.getNode(), rightApp.getNode());

        primaryStage.setScene(new Scene(appsBox, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle(WINDOW_TITLE);

        primaryStage.show();
    }

}
