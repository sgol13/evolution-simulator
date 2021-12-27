package sgol13.evolution.simulator.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class QueueChart {

    private static final int CHART_DAYS_SPAN = 200;

    private final VBox chartBox = new VBox();
    private final LineChart<Number, Number> chart;
    private final XYChart.Series<Number, Number> dataSeries =
            new XYChart.Series<Number, Number>();
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private int day = 0;

    public QueueChart(String label, Color color) {

        xAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(false);
        xAxis.setAnimated(false);

        yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setAnimated((false));

        chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chart.getData().add(dataSeries);
        chartBox.getChildren().add(new Label(label));
        chartBox.getChildren().add(chart);
    }

    public void update(double value) {

        dataSeries.getData().add(new XYChart.Data<Number, Number>(day, value));
        day++;

        if (day > CHART_DAYS_SPAN) {
            dataSeries.getData().remove(0);
            // xAxis.autoRange()
        }

    }

    public VBox getNode() {
        return chartBox;
    }
}
