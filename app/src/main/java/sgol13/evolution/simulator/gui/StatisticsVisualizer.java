package sgol13.evolution.simulator.gui;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sgol13.evolution.simulator.snapshots.StatisticsSnapshot;
import static java.lang.System.out;

public class StatisticsVisualizer {

    private final VBox chartsBox = new VBox();
    private final QueueChart animalsNumChart =
            new QueueChart("Animals", Color.RED);

    private final QueueChart grassFieldsNumChart =
            new QueueChart("Grass", Color.RED);

    private final QueueChart averageEnergyChart =
            new QueueChart("Average energy", Color.RED);

    private final QueueChart averageLifespanChart =
            new QueueChart("Average lifespan", Color.RED);

    private final QueueChart averageChildrenNumChart =
            new QueueChart("Average children number", Color.RED);

    public StatisticsVisualizer() {

        chartsBox.getChildren().add(animalsNumChart.getNode());
        chartsBox.getChildren().add(grassFieldsNumChart.getNode());
        chartsBox.getChildren().add(averageEnergyChart.getNode());
        chartsBox.getChildren().add(averageLifespanChart.getNode());
        chartsBox.getChildren().add(averageChildrenNumChart.getNode());
    }

    public void update(StatisticsSnapshot snapshot) {

        var s = snapshot;
        out.println("animals: " + s.getAnimalsNum());
        out.println("grass: " + s.getGrassFieldsNum());
        out.println("av energy: " + s.getAverageEnergyLevel());
        out.println("av lifespan: " + s.getAverageLifespan());
        out.println("av children: " + s.getAverageChildrenNum());
        out.println("genotype: " + s.getDominantGenotype());
        out.println("");

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
