package sgol13.evolution.simulator.simulation;

import java.util.Collection;
import java.util.LinkedList;
import sgol13.evolution.simulator.snapshots.MapSnapshot;

public interface IMap {

    Vector2d updatePosition(Animal animal, Vector2d position, MoveDirection direction);

    boolean placeAnimal(Animal animal);

    void placeAnimalOnRandomField(Animal animal);

    LinkedList<Animal> placeRandomAnimals(int animalsNum);

    boolean removeAnimal(Animal animal);

    void placeTwoRandomGrassFields();

    MapField getField(Vector2d position);

    MapSnapshot getMapSnapshot();

    Collection<MapField> getAllFields();

    int getGrassFieldsNum();
}
