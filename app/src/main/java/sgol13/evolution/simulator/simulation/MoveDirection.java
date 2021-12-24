package sgol13.evolution.simulator.simulation;

public enum MoveDirection {
    UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT;

    private static final MoveDirection[] values = values();

    public String toString() {
        return String.valueOf(ordinal());
    }

    public MoveDirection next() {
        return values[(ordinal() + 1) % values.length];
    }

    public MoveDirection nextRepeat(int n) {
        return values[(ordinal() + n) % values.length];
    }

    public MoveDirection previous() {
        return values[(ordinal() - 1 + values.length) % values.length];
    }

    public Vector2d toUnitVector() {
        return switch (this) {
        case UP -> new Vector2d(0, 1);
        case UP_RIGHT -> new Vector2d(1, 1);
        case RIGHT -> new Vector2d(1, 0);
        case DOWN_RIGHT -> new Vector2d(1, -1);
        case DOWN -> new Vector2d(0, -1);
        case DOWN_LEFT -> new Vector2d(-1, -1);
        case LEFT -> new Vector2d(-1, 0);
        case UP_LEFT -> new Vector2d(-1, 1);
        };
    }
}
