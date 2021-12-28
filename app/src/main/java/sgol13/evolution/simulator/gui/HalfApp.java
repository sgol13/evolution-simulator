package sgol13.evolution.simulator.gui;

import javafx.scene.layout.VBox;
import sgol13.evolution.simulator.SimulationConfig;

public class HalfApp {

    SimulationVisualizer simulationVisualizer = null;
    Configurator configurator = new Configurator(this);
    VBox halfAppBox = new VBox();
    private boolean isBoundedMap;

    public HalfApp(boolean isBoundedMap) {

        this.isBoundedMap = isBoundedMap;

        openConfigurator();
    }

    public void openConfigurator() {

        halfAppBox.getChildren().clear();
        halfAppBox.getChildren().add(configurator.getNode());
    }

    public void startSimulation(SimulationConfig config) {

        simulationVisualizer = new SimulationVisualizer(config, this);
        config.isBoundedMap = isBoundedMap;
        halfAppBox.getChildren().clear();
        halfAppBox.getChildren().add(simulationVisualizer.getNode());
        simulationVisualizer.start();
    }

    public VBox getNode() {
        return halfAppBox;
    }

}
