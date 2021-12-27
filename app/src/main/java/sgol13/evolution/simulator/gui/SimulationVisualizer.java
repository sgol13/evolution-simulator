package sgol13.evolution.simulator.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.simulation.BoundedMap;
import sgol13.evolution.simulator.simulation.IMap;
import sgol13.evolution.simulator.simulation.SimulationEngine;
import sgol13.evolution.simulator.simulation.UnboundedMap;
import sgol13.evolution.simulator.snapshots.SimulationSnapshot;

public class SimulationVisualizer {

    private final SimulationConfig config;
    private final SimulationEngine engine;
    private boolean pauseFlag = true;
    private boolean finishFlag = false;
    private boolean beforeStart = true;
    private final Thread simThread;

    private final GridPane mainGrid = new GridPane();
    private final VBox mapButtonBox = new VBox();
    private final MapVisualizer mapVisualizer;

    public SimulationVisualizer(SimulationConfig config) {

        this.config = config;
        IMap map = config.boundedMap ? new BoundedMap(config) : new UnboundedMap(config);
        this.engine = new SimulationEngine(this, config, map);
        this.simThread = new Thread(engine);
        this.mapVisualizer = new MapVisualizer(config);

        mapButtonBox.getChildren().add(mapVisualizer.getNode());
        mapButtonBox.setSpacing(20);

        mainGrid.add(mapButtonBox, 0, 0);

        initStartButton();
    }

    public void update(SimulationSnapshot snapshot) {

        mapVisualizer.update(snapshot.getMapSnapshot());
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

    private void initStartButton() {

        var button = new Button("Start");
        button.setStyle("-fx-font-size:20");

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

        mapButtonBox.getChildren().add(button);
        mapButtonBox.setAlignment(Pos.CENTER);
    }
}
