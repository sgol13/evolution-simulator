// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Vector2dTest {

    @Test
    public void equalsTest() {

        assertFalse(new Vector2d(1, 2).equals(new Vector2d(5, 2)));
        assertFalse(new Vector2d(1, 2).equals(new Vector2d(1, 3)));
        assertTrue(new Vector2d(-1, 2).equals(new Vector2d(-1, 2)));
    }

    @Test
    public void toStringTest() {

        assertEquals("(2,3)", new Vector2d(2, 3).toString());
        assertEquals("(-2,5)", new Vector2d(-2, 5).toString());
    }

    @Test
    public void precedesTest() {

        assertTrue(new Vector2d(2, 5).precedes(new Vector2d(2, 6)));
        assertTrue(new Vector2d(-7, -5).precedes(new Vector2d(4, 2)));
        assertTrue(new Vector2d(-7, -5).precedes(new Vector2d(-7, -5)));
        assertFalse(new Vector2d(2, 3).precedes(new Vector2d(4, 1)));

        assertFalse(new Vector2d(2, 5).strictlyPrecedes(new Vector2d(2, 6)));
        assertTrue(new Vector2d(-7, -5).strictlyPrecedes(new Vector2d(4, 2)));
        assertFalse(new Vector2d(-7, -5).strictlyPrecedes(new Vector2d(-7, -5)));
        assertFalse(new Vector2d(2, 3).strictlyPrecedes(new Vector2d(4, 1)));
    }

    @Test
    public void followsTest() {

        assertFalse(new Vector2d(2, 5).follows(new Vector2d(2, 6)));
        assertFalse(new Vector2d(-7, -5).follows(new Vector2d(4, 2)));
        assertTrue(new Vector2d(-7, -5).follows(new Vector2d(-7, -5)));
        assertFalse(new Vector2d(2, 3).follows(new Vector2d(4, 1)));
        assertTrue(new Vector2d(-7, -5).follows(new Vector2d(-17, -15)));
        assertTrue(new Vector2d(5, 3).follows(new Vector2d(4, 3)));

        assertFalse(new Vector2d(2, 5).strictlyFollows(new Vector2d(2, 6)));
        assertFalse(new Vector2d(-7, -5).strictlyFollows(new Vector2d(4, 2)));
        assertFalse(new Vector2d(-7, -5).strictlyFollows(new Vector2d(-7, -5)));
        assertFalse(new Vector2d(2, 3).strictlyFollows(new Vector2d(4, 1)));
        assertTrue(new Vector2d(-7, -5).strictlyFollows(new Vector2d(-17, -15)));
        assertFalse(new Vector2d(5, 3).strictlyFollows(new Vector2d(4, 3)));
    }

    @Test
    public void upperRightTest() {

        assertEquals(new Vector2d(3, 3),
                new Vector2d(-2, 3).upperRight(new Vector2d(3, 3)));

        assertEquals(new Vector2d(-5, 2),
                new Vector2d(-5, -4).upperRight(new Vector2d(-7, 2)));

        assertEquals(new Vector2d(4, 9),
                new Vector2d(4, 8).upperRight(new Vector2d(3, 9)));
    }

    @Test
    public void lowerLeftTest() {

        assertEquals(new Vector2d(-2, 3),
                new Vector2d(-2, 3).lowerLeft(new Vector2d(3, 3)));

        assertEquals(new Vector2d(-7, -4),
                new Vector2d(-5, -4).lowerLeft(new Vector2d(-7, 2)));

        assertEquals(new Vector2d(3, 8),
                new Vector2d(4, 8).lowerLeft(new Vector2d(3, 9)));
    }

    @Test
    public void addTest() {

        assertEquals(new Vector2d(1, 6),
                new Vector2d(-2, 3).add(new Vector2d(3, 3)));

        assertEquals(new Vector2d(-12, -2),
                new Vector2d(-5, -4).add(new Vector2d(-7, 2)));

        assertEquals(new Vector2d(7, 17),
                new Vector2d(4, 8).add(new Vector2d(3, 9)));
    }

    @Test
    public void subtractTest() {

        assertEquals(new Vector2d(-5, 0),
                new Vector2d(-2, 3).subtract(new Vector2d(3, 3)));

        assertEquals(new Vector2d(2, -6),
                new Vector2d(-5, -4).subtract(new Vector2d(-7, 2)));

        assertEquals(new Vector2d(1, -1),
                new Vector2d(4, 8).subtract(new Vector2d(3, 9)));
    }

    @Test
    public void oppositeTest() {

        assertEquals(new Vector2d(2, -3),
                new Vector2d(-2, 3).opposite());

        assertEquals(new Vector2d(5, 0),
                new Vector2d(-5, 0).opposite());

        assertEquals(new Vector2d(-4, -8),
                new Vector2d(4, 8).opposite());
    }
}
