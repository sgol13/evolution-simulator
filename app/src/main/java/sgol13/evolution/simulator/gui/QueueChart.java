package sgol13.evolution.simulator.gui;

import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class QueueChart {

    private static final int CHART_DAYS_SPAN = 500;

    private final VBox chartBox = new VBox();
    private final LineChart<Number, Number> chart;
    private final XYChart.Series<Number, Number> dataSeries =
            new XYChart.Series<Number, Number>();
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private int day = 0;

    public QueueChart(String label, String color) {

        xAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        xAxis.setAnimated(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        xAxis.setVisible(false);

        yAxis = new NumberAxis();
        yAxis.setVisible(false);
        yAxis.setAutoRanging(true);
        yAxis.setAnimated((false));

        chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chart.getData().add(dataSeries);
        chart.setStyle("CHART_COLOR_1:" + color + ";");

        chart.setLegendVisible(false);

        // chart.minHeight(HEIGHT_PX);
        // chart.maxHeight(HEIGHT_PX);
        // chart.minWidth(WIDTH_PX);
        // chart.maxWidth(WIDTH_PX);

        chartBox.getChildren().add(new Label(label));
        chartBox.getChildren().add(chart);
        chartBox.setAlignment(Pos.CENTER);
    }

    public void update(double value) {

        dataSeries.getData().add(
                new XYChart.Data<Number, Number>(day + CHART_DAYS_SPAN, value));
        day++;

        if (day > CHART_DAYS_SPAN) {
            dataSeries.getData().remove(0);
        }

        xAxis.setLowerBound(day);
        xAxis.setUpperBound(day + CHART_DAYS_SPAN);

    }

    public VBox getNode() {
        return chartBox;
    }
}
