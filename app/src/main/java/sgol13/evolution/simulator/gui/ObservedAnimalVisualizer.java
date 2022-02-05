// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sgol13.evolution.simulator.snapshots.ObservedAnimalSnapshot;
import java.util.ArrayList;

public class ObservedAnimalVisualizer {

    private static final int FONT_SIZE = 20;

    private final GridPane grid = new GridPane();
    private boolean openFlag = false;
    private ArrayList<Text> values = new ArrayList<Text>();

    public ObservedAnimalVisualizer() {

        grid.setVisible(false);
        grid.setPadding(new Insets(50, 0, 0, 50));
        grid.setHgap(20);
        grid.setVgap(20);

        String[] labelsArray = {"Animal ID", "Genotype", "Energy", "Children"};

        for (int i = 0; i < labelsArray.length; i++) {

            var label = new Label(labelsArray[i]);
            label.setFont(new Font(FONT_SIZE));
            grid.add(label, 0, i + 1);

            var textValue = new Text("");
            textValue.setFont(new Font(FONT_SIZE));
            grid.add(textValue, 1, i + 1);
            values.add(textValue);
        }
    }

    public void update(ObservedAnimalSnapshot snapshot) {

        values.get(0).setText(String.valueOf(snapshot.getID()));
        values.get(1).setText(snapshot.getGenotype().toString());
        values.get(2).setText(String.valueOf(snapshot.getEnergy()));
        values.get(2).setFill(Color.BLACK);
        values.get(3).setText(String.valueOf(snapshot.getChildrenNumber()));
    }

    public GridPane getNode() {
        return grid;
    }

    public void open() {
        openFlag = true;
        grid.setVisible(true);
    }

    public void died() {
        openFlag = false;

        // animal is dead
        values.get(2).setText("DEAD");
        values.get(2).setFill(Color.RED);
    }

    public boolean isOpened() {
        return openFlag;
    }
}
