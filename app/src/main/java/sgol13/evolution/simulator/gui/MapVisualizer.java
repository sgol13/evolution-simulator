package sgol13.evolution.simulator.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sgol13.evolution.simulator.snapshots.MapSnapshot;

public class MapVisualizer {

    private final GridPane mapGrid = new GridPane();

    public MapVisualizer() {}

    public void update(MapSnapshot snapshot) {

        mapGrid.setGridLinesVisible(true);
    }

    public GridPane getNode() {
        return mapGrid;
    }
}
