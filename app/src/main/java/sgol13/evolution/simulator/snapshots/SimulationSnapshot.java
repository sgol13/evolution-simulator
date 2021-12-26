package sgol13.evolution.simulator.snapshots;

public class SimulationSnapshot {

    private final MapSnapshot mapSnapshot;

    public SimulationSnapshot(MapSnapshot mapSnapshot) {
        this.mapSnapshot = mapSnapshot;
    }

    public MapSnapshot getMapSnapshot() {
        return mapSnapshot;
    }
}
