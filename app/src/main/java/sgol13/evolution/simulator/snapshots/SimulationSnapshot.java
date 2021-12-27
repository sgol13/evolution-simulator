package sgol13.evolution.simulator.snapshots;

public class SimulationSnapshot {

    private final MapSnapshot mapSnapshot;
    private final StatisticsSnapshot statisticsSnapshot;

    public SimulationSnapshot(MapSnapshot mapSnapshot,
            StatisticsSnapshot statisticsSnapshot) {

        this.mapSnapshot = mapSnapshot;
        this.statisticsSnapshot = statisticsSnapshot;
    }

    public MapSnapshot getMapSnapshot() {
        return mapSnapshot;
    }

    public StatisticsSnapshot getStatisticsSnapshot() {
        return statisticsSnapshot;
    }
}
