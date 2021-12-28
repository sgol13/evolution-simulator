package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import sgol13.evolution.simulator.snapshots.MapSnapshot;

public class MapSnapshotTest {

    @Test
    public void grassTest() {

        var snap = new MapSnapshot(new Vector2d(5, 5));
        snap.addGrassField(new Vector2d(3, 2));
        snap.addGrassField(new Vector2d(2, 2));
        snap.addGrassField(new Vector2d(0, 4));
        snap.addGrassField(new Vector2d(2, 3));
        snap.addGrassField(new Vector2d(1, 3));
        snap.addGrassField(new Vector2d(4, 1));

        assertTrue(snap.isGrassed(3, 4));
        assertTrue(snap.isGrassed(2, 3));
        assertTrue(snap.isGrassed(0, 0));
        assertTrue(snap.isGrassed(1, 1));
        assertFalse(snap.isGrassed(3, 3));
        assertFalse(snap.isGrassed(4, 4));
    }
}
