package sgol13.evolution.simulator.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import sgol13.evolution.simulator.SimulationConfig;

public class HalfApp {

    SimulationVisualizer simulationVisualizer = null;
    Configurator configurator = new Configurator(this);
    VBox halfAppBox = new VBox();
    private boolean isBoundedMap;

    public HalfApp(boolean isBoundedMap) {

        this.isBoundedMap = isBoundedMap;
        halfAppBox.setAlignment(Pos.CENTER);

        openConfigurator();
    }

    public void openConfigurator() {

        if (simulationVisualizer != null)
            simulationVisualizer.finish();

        halfAppBox.getChildren().clear();
        halfAppBox.getChildren().add(configurator.getNode());
    }

    public void startSimulation(SimulationConfig config) {

        simulationVisualizer = new SimulationVisualizer(config, this);
        config.isBoundedMap = isBoundedMap;
        config = new SimulationConfig();
        halfAppBox.getChildren().clear();
        halfAppBox.getChildren().add(simulationVisualizer.getNode());
        simulationVisualizer.start();
    }

    public VBox getNode() {
        return halfAppBox;
    }

}
