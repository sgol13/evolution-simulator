package sgol13.evolution.simulator.snapshots;

public class SimulationSnapshot {

    private final MapSnapshot mapSnapshot;
    private final StatisticsSnapshot statisticsSnapshot;
    private final ObservedAnimalSnapshot observedAnimalSnapshot;

    public SimulationSnapshot(
            MapSnapshot mapSnapshot,
            StatisticsSnapshot statisticsSnapshot,
            ObservedAnimalSnapshot observedAnimalSnapshot) {

        this.mapSnapshot = mapSnapshot;
        this.statisticsSnapshot = statisticsSnapshot;
        this.observedAnimalSnapshot = observedAnimalSnapshot;
    }

    public MapSnapshot getMapSnapshot() {
        return mapSnapshot;
    }

    public StatisticsSnapshot getStatisticsSnapshot() {
        return statisticsSnapshot;
    }

    public ObservedAnimalSnapshot getObservedAnimalSnapshot() {
        return observedAnimalSnapshot;
    }
}
