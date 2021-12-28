package sgol13.evolution.simulator.gui;


import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import sgol13.evolution.simulator.simulation.SimulationConfig;

public class Configurator {

    private static final int LABELS_FONT_SIZE = 25;
    private static final int VALUES_FONT_SIZE = 20;
    private static final double[] SLIDERS_DEFAULT_VALUES = {30, 20, 0.2, 100, 1, 30};
    private static final double[] SLIDERS_MIN_VALUES = {20, 1, 0, 1, 1, 1};
    private static final double[] SLIDERS_MAX_VALUES = {40, 400, 0.5, 500, 10, 400};

    private final GridPane grid = new GridPane();
    private static final String[] labelsTexts = {"Map size", "Initial animals",
            "Jungle ratio", "Start energy", "Move energy",
            "Plant energy", "Magic evolution"};

    private final Slider[] sliders = new Slider[6];
    private final CheckBox magicStrategyCheckBox = new CheckBox();
    private final Button acceptButton = new Button();
    private final Button defaultButton = new Button();

    private final HalfApp myApp;

    public Configurator(HalfApp myApp) {

        this.myApp = myApp;

        initGrid();

        initLabels();
        initControls();
        initButtons();
        initValueLabels();

        setDefaultValues();
    }

    public GridPane getNode() {
        return grid;
    }

    private void initGrid() {

        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(0, 160, 100, 160));
        grid.setVgap(40);
        grid.setHgap(35);

        grid.getColumnConstraints().add(new ColumnConstraints(220));
        grid.getColumnConstraints().add(new ColumnConstraints(250));
        grid.getColumnConstraints().add(new ColumnConstraints(100));
    }

    private void initLabels() {

        for (int i = 0; i < labelsTexts.length; i++) {
            var label = new Label(labelsTexts[i]);
            label.setFont(new Font(LABELS_FONT_SIZE));
            grid.add(label, 0, i);
        }
    }

    private void initControls() {

        for (int i = 0; i < sliders.length; i++) {

            sliders[i] = new Slider();
            sliders[i].setMin(SLIDERS_MIN_VALUES[i]);
            sliders[i].setMax(SLIDERS_MAX_VALUES[i]);
            sliders[i].setMinWidth(250);
            grid.add(sliders[i], 1, i);
        }

        // magic strategy
        grid.add(magicStrategyCheckBox, 1, 6);
        magicStrategyCheckBox.setScaleX(1.5);
        magicStrategyCheckBox.setScaleY(1.5);

    }

    private void initValueLabels() {

        var labels = new Label[6];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label();
            labels[i].setFont(new Font(VALUES_FONT_SIZE));
            grid.add(labels[i], 2, i);

            labels[i].textProperty().bind(
                    Bindings.format("%.0f", sliders[i].valueProperty()));
        }

        labels[2].textProperty().bind(
                Bindings.format("%.2f", sliders[2].valueProperty()));
    }

    private void initButtons() {

        // start button
        acceptButton.setText("Accept");
        acceptButton.setMinHeight(40);
        acceptButton.setMinWidth(150);
        acceptButton.setStyle("-fx-font-size:20");
        acceptButton.setOnAction(event -> finishConfiguration());
        grid.add(acceptButton, 1, 7);


        // default button
        defaultButton.setText("Default");
        defaultButton.setMinHeight(40);
        defaultButton.setMinWidth(150);
        defaultButton.setStyle("-fx-font-size:20");
        defaultButton.setOnAction(event -> setDefaultValues());

        grid.add(defaultButton, 0, 7);
    }

    private void setDefaultValues() {

        for (int i = 0; i < sliders.length; i++) {
            sliders[i].setValue(SLIDERS_DEFAULT_VALUES[i]);
        }

        magicStrategyCheckBox.setSelected(false);
    }

    private void finishConfiguration() {

        var config = new SimulationConfig();

        config.mapHeight = config.mapWidth = (int) sliders[0].getValue();
        config.initialAnimals = (int) sliders[1].getValue();
        config.jungleRatio = sliders[2].getValue();
        config.startEnergy = (int) sliders[3].getValue();
        config.moveEnergy = (int) sliders[4].getValue();
        config.plantEnergy = (int) sliders[5].getValue();
        config.magicStrategy = magicStrategyCheckBox.isSelected();

        myApp.startSimulation(config);
    }
}
