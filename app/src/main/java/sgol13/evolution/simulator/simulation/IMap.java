package sgol13.evolution.simulator.simulation;


public interface IMap {

    boolean canMoveTo(Vector2d position);

    void updatePosition(Animal animal, Vector2d oldPosition, Vector2d newPosition);

    boolean place(Animal animal);

    boolean isOccupied(Vector2d position);

    Object objectAt(Vector2d position);
}
