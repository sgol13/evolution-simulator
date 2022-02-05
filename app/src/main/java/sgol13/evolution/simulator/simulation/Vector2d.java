// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.simulation;

import java.util.Objects;

public class Vector2d implements Comparable<Vector2d> {
    public final int x, y;

    public Vector2d(Vector2d other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ',' + y + ')';
    }

    @Override
    public Vector2d clone() {
        return new Vector2d(x, y);
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public boolean strictlyPrecedes(Vector2d other) {
        return x < other.x && y < other.y;
    }

    public boolean strictlyFollows(Vector2d other) {
        return x > other.x && y > other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d other_vector = (Vector2d) other;
        return x == other_vector.x && y == other_vector.y;
    }

    @Override
    public int compareTo(Vector2d other) {

        if (x != other.x)
            return other.x - x;
        return other.y - y;
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }
}
