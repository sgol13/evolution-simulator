package sgol13.evolution.simulator.simulation;

public enum MoveDirection {

    FORWARD, FORWARD_RIGHT, RIGHT, BACKWARD_RIGHT, // 
    BACKWARD, BACKWARD_LEFT, LEFT, FORWARD_LEFT;

    private static final MoveDirection[] values = values();

    public static MoveDirection toDirection(int n) {
        return values[n % values.length];
    }

    public String toString() {
        return String.valueOf(ordinal());
    }

    public MoveDirection add(MoveDirection other) {
        return nextRepeat(ordinal());
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
        case FORWARD -> new Vector2d(0, 1);
        case FORWARD_RIGHT -> new Vector2d(1, 1);
        case RIGHT -> new Vector2d(1, 0);
        case BACKWARD_RIGHT -> new Vector2d(1, -1);
        case BACKWARD -> new Vector2d(0, -1);
        case BACKWARD_LEFT -> new Vector2d(-1, -1);
        case LEFT -> new Vector2d(-1, 0);
        case FORWARD_LEFT -> new Vector2d(-1, 1);
        };
    }

    public static int getValuesNum() {
        return values.length;
    }
}
