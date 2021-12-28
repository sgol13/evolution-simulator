package sgol13.evolution.simulator.gui;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.HBox;
import static java.lang.System.out;

public class App extends Application {

    private static final String WINDOW_TITLE = "Evolution Simulator";
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;

    HalfApp leftApp = new HalfApp(false);
    HalfApp rightApp = new HalfApp(true);
    HBox appsBox = new HBox();

    @Override
    public void init() {
        appsBox.getChildren().addAll(leftApp.getNode(), rightApp.getNode());
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(new Scene(appsBox, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.setTitle(WINDOW_TITLE);

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

        leftApp.stop();
        rightApp.stop();

        super.stop();
    }

}
