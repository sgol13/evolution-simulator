package sgol13.evolution.simulator.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.snapshots.MapSnapshot;
import static java.lang.System.out;

public class MapVisualizer {

    private static final Color GRASS_COLOR = Color.BLUE;
    private static final Color[] ENERGY_COLORS = {
            Color.BROWN, Color.RED, Color.ORANGERED, Color.DARKORANGE,
            Color.ORANGE, Color.YELLOW, Color.GREENYELLOW, Color.LIME};

    private static final int MAP_WIDTH_PX = 600;
    private static final int GAP_BETWEEN_FIELDS_PX = 2;
    private static final double[] RADIUS_MULTIPLIERS = {0.9, 1.0, 1.1, 1.2, 1.3, 1.4};
    private int squareSide;

    private final SimulationConfig config;
    private final GridPane mapGrid = new GridPane();

    public MapVisualizer(SimulationConfig config) {

        this.config = config;
        initMapGrid();
    }

    public void update(MapSnapshot snapshot) {

        mapGrid.getChildren().clear();

        for (int row = 0; row < config.mapHeight; row++)
            for (int col = 0; col < config.mapWidth; col++)
                updateField(snapshot, row, col);
    }

    private void updateField(MapSnapshot snapshot, int row, int col) {

        int animalsNum = snapshot.getAnimalsNumber(row, col);
        if (animalsNum > 0) {


            var animalSquare = new Circle(calculateCircleRadius(animalsNum));
            var color = calculateFieldColor(snapshot.getMaxEnergy(row, col));
            animalSquare.setFill(color);
            mapGrid.add(animalSquare, col, row);

        } else if (snapshot.isGrassed(row, col)) {

            var grassSquare = new Rectangle(squareSide, squareSide);
            grassSquare.setFill(GRASS_COLOR);
            grassSquare.setArcHeight(squareSide / 2);
            grassSquare.setArcWidth(squareSide / 2);
            mapGrid.add(grassSquare, col, row);
        }
    }

    private int calculateCircleRadius(int animalsNum) {

        double multiplier = 1;
        if (animalsNum >= RADIUS_MULTIPLIERS.length)
            multiplier = RADIUS_MULTIPLIERS[RADIUS_MULTIPLIERS.length - 1];
        else
            multiplier = RADIUS_MULTIPLIERS[animalsNum - 1];

        return (int) (multiplier * squareSide / 2.0);
    }

    private Color calculateFieldColor(int energy) {
        return Color.GREEN;
    }

    public GridPane getNode() {
        return mapGrid;
    }

    private void initMapGrid() {

        mapGrid.setAlignment(Pos.CENTER);
        mapGrid.setVgap(GAP_BETWEEN_FIELDS_PX);
        mapGrid.setHgap(GAP_BETWEEN_FIELDS_PX);

        // calculate dimensions of a singe square
        squareSide = (MAP_WIDTH_PX - (config.mapWidth - 1) * GAP_BETWEEN_FIELDS_PX)
                / config.mapWidth;

        out.println(squareSide);

        for (int row = 0; row < config.mapHeight; row++)
            mapGrid.getRowConstraints().add(new RowConstraints(squareSide));

        for (int col = 0; col < config.mapWidth; col++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(squareSide));
    }
}
