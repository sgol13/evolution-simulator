package sgol13.evolution.simulator.gui;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sgol13.evolution.simulator.snapshots.StatisticsSnapshot;
import static java.lang.System.out;

public class StatisticsVisualizer {

    private final VBox chartsBox = new VBox();
    private final QueueChart animalsNumChart =
            new QueueChart("Animals", "#00ccff");

    private final QueueChart grassFieldsNumChart =
            new QueueChart("Grass", "#009933");

    private final QueueChart averageEnergyChart =
            new QueueChart("Average energy", "##cc0099");

    private final QueueChart averageLifespanChart =
            new QueueChart("Average lifespan", "#ff9900");

    private final QueueChart averageChildrenNumChart =
            new QueueChart("Average children number", "#99cc00");

    public StatisticsVisualizer() {

        chartsBox.getChildren().add(animalsNumChart.getNode());
        chartsBox.getChildren().add(grassFieldsNumChart.getNode());
        chartsBox.getChildren().add(averageEnergyChart.getNode());
        chartsBox.getChildren().add(averageLifespanChart.getNode());
        chartsBox.getChildren().add(averageChildrenNumChart.getNode());
    }

    public void update(StatisticsSnapshot snapshot) {

        animalsNumChart.update(snapshot.getAnimalsNum());
        grassFieldsNumChart.update(snapshot.getGrassFieldsNum());
        averageEnergyChart.update(snapshot.getAverageEnergyLevel());
        averageLifespanChart.update(snapshot.getAverageLifespan());
        averageChildrenNumChart.update(snapshot.getAverageChildrenNum());
    }

    public VBox getNode() {
        return chartsBox;
    }
}
