package sgol13.evolution.simulator.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public abstract class AbstractMap implements IMap {

    private final SimulationConfig config;
    private final Random randomGenerator = new Random();
    protected final Vector2d mapSize;

    protected final LinkedHashMap<Vector2d, MapField> fields =
            new LinkedHashMap<Vector2d, MapField>();

    public AbstractMap(SimulationConfig config) {

        this.config = config;
        this.mapSize = new Vector2d(config.mapWidth, config.mapHeight);
        initFields(config);
    }

    // create MapField object for every field on the map
    private void initFields(SimulationConfig config) {

        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {

                var newField = new MapField(new Vector2d(x, y), config);

                fields.put(new Vector2d(x, y), newField);
            }
        }
    }

    @Override
    public abstract Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction);

    @Override
    public boolean placeAnimal(Animal animal) {
        return fields.get(animal.getPosition()).addAnimal(animal);
    }

    @Override
    public LinkedList<Animal> placeRandomAnimals(int animalsNum) {

        // get all empty positions on the map
        var emptyPositions = new ArrayList<Vector2d>();
        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {
                var position = new Vector2d(x, y);
                if (fields.get(position).isEmpty())
                    emptyPositions.add(position);
            }
        }

        // randomly select animalsNum empty positions (if there are enough)
        animalsNum = Math.min(animalsNum, emptyPositions.size());

        Collections.shuffle(emptyPositions);
        var newPositions = emptyPositions.subList(0, animalsNum);

        // place an animal on every selected empty position
        var newAnimals = new LinkedList<Animal>();
        for (var position : newPositions) {

            var newAnimal = new Animal(this, position,
                    config.startEnergy, config.moveEnergy);
            placeAnimal(newAnimal);
            newAnimals.add(newAnimal);
        }

        return newAnimals;
    }

    @Override
    public boolean removeAnimal(Animal animal) {
        return fields.get(animal.getPosition()).removeAnimal(animal);
    }

    @Override
    public MapField getField(Vector2d position) {
        return fields.get(position);
    }

    @Override
    public MapSnapshot getSnapshot() {

        var snapshot = new MapSnapshot(mapSize);
        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {

                var position = new Vector2d(x, y);
                var field = fields.get(position);
                if (field.isGrassed())
                    snapshot.addGrassField(position);
                else {

                    var animalsGroup = field.getAnimalsGroup();
                    if (animalsGroup.length > 0) {

                        out.println(position + " " + animalsGroup[0].getID());
                        snapshot.addAnimalsGroup(position, animalsGroup.length,
                                animalsGroup[0].getEnergy());
                    }
                }
            }
        }

        return snapshot;
    }

    @Override
    public Collection<MapField> getAllFields() {
        return fields.values();
    }
}
