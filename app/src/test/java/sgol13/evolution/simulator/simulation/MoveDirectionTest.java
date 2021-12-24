package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MoveDirectionTest {

    @Test
    public void test() {

        assertEquals(MoveDirection.UP_LEFT.toString(), "7");
        assertEquals(MoveDirection.RIGHT.toString(), "2");

        assertEquals(MoveDirection.UP_LEFT.next().toString(), "0");
        assertEquals(MoveDirection.RIGHT.previous().toString(), "1");

        assertEquals(MoveDirection.UP_LEFT.nextRepeat(7).toString(), "6");
        assertEquals(MoveDirection.RIGHT.nextRepeat(3).toString(), "5");

        assertEquals(MoveDirection.DOWN.toUnitVector(), new Vector2d(0, -1));
        assertEquals(MoveDirection.DOWN_LEFT.toUnitVector(), new Vector2d(-1, -1));
    }
}
