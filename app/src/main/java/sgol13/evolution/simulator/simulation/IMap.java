package sgol13.evolution.simulator.simulation;


public interface IMap {

    Vector2d updatePosition(Animal animal, Vector2d position, MoveDirection direction);

    boolean place(Animal animal);

    Animal placeRandomAnimal();

    MapField getField(Vector2d position);

    MapSnapshot getSnapshot();
}
