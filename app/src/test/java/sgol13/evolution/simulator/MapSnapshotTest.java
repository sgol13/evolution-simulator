package sgol13.evolution.simulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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

    @Test
    public void animalsTest() {

        var snap = new MapSnapshot(new Vector2d(5, 5));
        snap.addAnimalsGroup(new Vector2d(2, 0), 4, 2);
        snap.addAnimalsGroup(new Vector2d(3, 3), 3, 8);

        assertEquals(snap.getAnimalsNumber(4, 2), 4);
        assertEquals(snap.getMaxEnergy(4, 2), 2);

        assertEquals(snap.getAnimalsNumber(1, 3), 3);
        assertEquals(snap.getMaxEnergy(1, 3), 8);
    }

}
