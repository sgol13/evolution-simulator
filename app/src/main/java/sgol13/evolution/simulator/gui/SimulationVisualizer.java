package sgol13.evolution.simulator.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.simulation.BoundedMap;
import sgol13.evolution.simulator.simulation.IMap;
import sgol13.evolution.simulator.simulation.SimulationEngine;
import sgol13.evolution.simulator.simulation.UnboundedMap;
import sgol13.evolution.simulator.snapshots.SimulationSnapshot;
import static java.lang.System.out;

public class SimulationVisualizer {

    private final SimulationConfig config;
    private final SimulationEngine engine;
    private final HalfApp myApp;
    private boolean pauseFlag = true;
    private boolean finishFlag = false;
    private boolean beforeStart = true;
    private final Thread simThread;

    private final GridPane mainGrid = new GridPane();
    private final VBox mapControlsBox = new VBox();
    private final HBox genotypeBox = new HBox();
    private final Text genotypeText = new Text();

    private final MapVisualizer mapVisualizer;
    private final StatisticsVisualizer statisticsVisualizer =
            new StatisticsVisualizer();
    private final ObservedAnimalVisualizer observedAnimalVisualizer =
            new ObservedAnimalVisualizer();

    public SimulationVisualizer(SimulationConfig config, HalfApp myApp) {

        this.myApp = myApp;
        this.config = config;
        IMap map = config.isBoundedMap ? new BoundedMap(config) : new UnboundedMap(config);
        this.engine = new SimulationEngine(this, config, map);
        this.simThread = new Thread(engine);
        this.mapVisualizer = new MapVisualizer(engine, config);

        mapControlsBox.getChildren().add(mapVisualizer.getNode());
        mapControlsBox.setSpacing(40);

        mainGrid.add(mapControlsBox, 1, 0);
        mainGrid.setPadding(new Insets(10, 10, 80, 10));
        mainGrid.add(statisticsVisualizer.getNode(), 0, 0, 1, 2);
        mainGrid.add(observedAnimalVisualizer.getNode(), 1, 1);
        mainGrid.setAlignment(Pos.CENTER);

        mainGrid.getColumnConstraints().add(new ColumnConstraints(390));
        mainGrid.getColumnConstraints().add(new ColumnConstraints(550));

        initControls();
        initDominantGenotype();
    }

    public void update(SimulationSnapshot snapshot) {

        mapVisualizer.update(snapshot.getMapSnapshot());
        statisticsVisualizer.update(snapshot.getStatisticsSnapshot());

        if (snapshot.getObservedAnimalSnapshot() != null) {

            if (!observedAnimalVisualizer.isOpened())
                observedAnimalVisualizer.open();

            observedAnimalVisualizer.update(snapshot.getObservedAnimalSnapshot());

        } else if (observedAnimalVisualizer.isOpened()) {
            observedAnimalVisualizer.died();
        }

        // genotype
        var genotype = snapshot.getMapSnapshot().getDominantGenotype();
        if (genotype != null)
            genotypeText.setText(genotype);
        else
            genotypeText.setText("[- - - - - - - -]");

        //magic function
        // int magicUsed = snapshot.getUsedMagicFunction();
        // if (magicUsed > 0) {

        //     var alert = new Alert(AlertType.INFORMATION);
        //     alert.setContentText("Used a magic function " + magicUsed + "/3!");
        //     alert.show();
        // }
    }

    public void start() {
        simThread.start();
    }

    public void finish() {

        engine.finishSimulation();
        try {
            simThread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public GridPane getNode() {
        return mainGrid;
    }

    private void initControls() {

        var controlsBox = new HBox();
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setSpacing(20);
        mapControlsBox.getChildren().add(controlsBox);

        var speedLabel = new Label("Speed");
        speedLabel.setStyle("-fx-font-size:20");
        controlsBox.getChildren().add(speedLabel);

        var slider = new Slider(1, 40, 10);
        controlsBox.getChildren().add(slider);
        slider.setPrefWidth(200);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                engine.changeSpeed((double) newValue);
            }
        });

        var pauseButton = new Button("Start");
        pauseButton.setStyle("-fx-font-size:20");
        pauseButton.setMinWidth(130);

        pauseButton.setOnAction(event -> {

            if (pauseFlag) { // resume
                pauseButton.setText("Pause");
                engine.resumeSimulation();
                pauseFlag = false;

            } else { // pause
                pauseButton.setText("Resume");
                engine.pauseSimulation();
                pauseFlag = true;
            }
        });

        var exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size:20");
        exitButton.setMinWidth(80);

        exitButton.setOnAction(event -> myApp.openConfigurator());

        controlsBox.getChildren().addAll(pauseButton, exitButton);
    }

    private void initDominantGenotype() {

        genotypeText.setFont(Font.font("Monospaced", 20));

        var label = new Label("Dominant genotype");
        label.setStyle("-fx-font-size:20");
        var button = new Button("Show");
        button.setStyle("-fx-font-size:20");
        button.setMinWidth(100);
        button.setOnAction(event -> {

            mapVisualizer.toggleShowDominantGenotype();
            if (mapVisualizer.isShowingDominantGenotype()) {
                button.setText("Hide");
            } else {
                button.setText("Show");
            }
        });

        genotypeBox.setAlignment(Pos.CENTER);
        genotypeBox.setSpacing(20);
        genotypeBox.getChildren().addAll(label, genotypeText, button);
        mapControlsBox.getChildren().add(genotypeBox);
    }
}
