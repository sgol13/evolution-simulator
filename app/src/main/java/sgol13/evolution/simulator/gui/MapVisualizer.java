package sgol13.evolution.simulator.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import org.checkerframework.checker.units.qual.g;

public class MapVisualizer {

    //colors
    private static final Color GRASS_COLOR = Color.GREEN;
    private static final Color MAX_ENERGY_COLOR = Color.color(1, 0, 1);
    private static final Color MIN_ENERGY_COLOR = Color.color(0, 1, 1);
    private static final double MAX_ENERGY_TO_START_ENERGY_RATIO = 2.0;

    // sizes
    private static final int MAP_WIDTH_PX = 600;
    private static final int INTERNAL_MARGIN_WIDTH = 2;
    private static final int GAP_BETWEEN_FIELDS_PX = 2;
    private static final double[] RADIUS_MULTIPLIERS = {1.0, 1.2, 1.4, 1.6, 1.8};
    private int squareSide;

    private final SimulationConfig config;
    private final GridPane mapGrid = new GridPane();
    private final VBox mapBox = new VBox();

    public MapVisualizer(SimulationConfig config) {

        this.config = config;

        mapBox.getChildren().add(mapGrid);
        mapBox.setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: black;" +
                "-fx-background-color: whitesmoke");


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

        // calculates the color proportionally to its energy
        // MAX_ENERGY_COLOR is when an animal has
        // MAX_ENERGY_TO_START_ENERGY_RATIO * config.startEnergy energy points
        // MIN_ENERGY_COLOR is when an animal has 0 energy points

        double maxEnergy = MAX_ENERGY_TO_START_ENERGY_RATIO * config.startEnergy;
        double energyLevel = energy / maxEnergy;

        energyLevel = Math.min(1.0, energyLevel);

        double maxR = MAX_ENERGY_COLOR.getRed();
        double maxG = MAX_ENERGY_COLOR.getGreen();
        double maxB = MAX_ENERGY_COLOR.getBlue();

        double minR = MIN_ENERGY_COLOR.getRed();
        double minG = MIN_ENERGY_COLOR.getGreen();
        double minB = MIN_ENERGY_COLOR.getBlue();

        double r = minR + (energyLevel * (maxR - minR));
        double g = minG + (energyLevel * (maxG - minG));
        double b = minB + (energyLevel * (maxB - minB));

        return Color.color(r, g, b);
    }

    public VBox getNode() {
        return mapBox;
    }

    private void initMapGrid() {

        mapGrid.setAlignment(Pos.CENTER);

        mapGrid.setVgap(GAP_BETWEEN_FIELDS_PX);
        mapGrid.setHgap(GAP_BETWEEN_FIELDS_PX);
        mapGrid.setPadding(new Insets(INTERNAL_MARGIN_WIDTH, INTERNAL_MARGIN_WIDTH,
                INTERNAL_MARGIN_WIDTH, INTERNAL_MARGIN_WIDTH));

        // calculate dimensions of a singe square
        squareSide = (MAP_WIDTH_PX - (config.mapWidth - 1) * GAP_BETWEEN_FIELDS_PX)
                / config.mapWidth;

        for (int row = 0; row < config.mapHeight; row++)
            mapGrid.getRowConstraints().add(new RowConstraints(squareSide));

        for (int col = 0; col < config.mapWidth; col++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(squareSide));
    }

}
