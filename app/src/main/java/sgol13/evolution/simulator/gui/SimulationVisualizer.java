package sgol13.evolution.simulator.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private boolean pauseFlag = true;
    private boolean finishFlag = false;
    private boolean beforeStart = true;
    private final Thread simThread;

    private final GridPane mainGrid = new GridPane();
    private final VBox mapControlsBox = new VBox();
    private final MapVisualizer mapVisualizer;
    private final StatisticsVisualizer statisticsVisualizer = new StatisticsVisualizer();

    public SimulationVisualizer(SimulationConfig config) {

        this.config = config;
        IMap map = config.boundedMap ? new BoundedMap(config) : new UnboundedMap(config);
        this.engine = new SimulationEngine(this, config, map);
        this.simThread = new Thread(engine);
        this.mapVisualizer = new MapVisualizer(config);

        mapControlsBox.getChildren().add(mapVisualizer.getNode());
        mapControlsBox.setSpacing(20);

        mainGrid.add(mapControlsBox, 1, 0);
        mainGrid.add(statisticsVisualizer.getNode(), 0, 0, 1, 2);

        initControls();
    }

    public void update(SimulationSnapshot snapshot) {

        mapVisualizer.update(snapshot.getMapSnapshot());
        statisticsVisualizer.update(snapshot.getStatisticsSnapshot());
    }

    public void start() {
        simThread.start();
    }

    public void finish() throws InterruptedException {

        engine.finishSimulation();
        simThread.join();
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

        var button = new Button("Start");
        button.setStyle("-fx-font-size:20");
        button.setMinWidth(150);

        button.setOnAction(event -> {

            if (pauseFlag) { // resume
                button.setText("Pause");
                engine.resumeSimulation();
                pauseFlag = false;

            } else { // pause
                button.setText("Resume");
                engine.pauseSimulation();
                pauseFlag = true;
            }
        });

        controlsBox.getChildren().add(button);
    }
}
