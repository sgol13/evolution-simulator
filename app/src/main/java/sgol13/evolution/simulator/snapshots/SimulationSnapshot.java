package sgol13.evolution.simulator.snapshots;

public class SimulationSnapshot {

    private final MapSnapshot mapSnapshot;
    private final StatisticsSnapshot statisticsSnapshot;
    private final ObservedAnimalSnapshot observedAnimalSnapshot;
    private final int usedMagicFunction;

    public SimulationSnapshot(
            MapSnapshot mapSnapshot,
            StatisticsSnapshot statisticsSnapshot,
            ObservedAnimalSnapshot observedAnimalSnapshot,
            int usedMagicFunction) {

        this.mapSnapshot = mapSnapshot;
        this.statisticsSnapshot = statisticsSnapshot;
        this.observedAnimalSnapshot = observedAnimalSnapshot;
        this.usedMagicFunction = usedMagicFunction;
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

    public int getUsedMagicFunction() {
        return usedMagicFunction;
    }
}
