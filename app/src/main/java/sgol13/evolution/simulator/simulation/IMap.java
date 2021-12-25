package sgol13.evolution.simulator.simulation;

import java.util.Collection;
import java.util.LinkedList;

public interface IMap {

    Vector2d updatePosition(Animal animal, Vector2d position, MoveDirection direction);

    boolean placeAnimal(Animal animal);

    LinkedList<Animal> placeRandomAnimals(int animalsNum);

    boolean removeAnimal(Animal animal);

    MapField getField(Vector2d position);

    MapSnapshot getSnapshot();

    Collection<MapField> getAllFields();
}
