package sgol13.evolution.simulator.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import sgol13.evolution.simulator.SimulationConfig;

public abstract class AbstractMap implements IMap {

    private final SimulationConfig config;
    private final Random randomGenerator = new Random();
    private final SortedSet<MapField> jungleEmptyFields = new TreeSet<MapField>();
    // private final Set<MapField> steppeEmptyFields = new HashSet<MapField>();
    private final SortedSet<MapField> fieldsWithoutAnimals = new TreeSet<MapField>();
    private final SortedSet<MapField> fieldsContainingAnimals = new TreeSet<MapField>();
    protected final Vector2d mapSize;

    protected final Map<Vector2d, MapField> fields = new HashMap<Vector2d, MapField>();

    public AbstractMap(SimulationConfig config) {

        this.config = config;
        this.mapSize = new Vector2d(config.mapWidth, config.mapHeight);
        initFields(config);
    }

    // create MapField object for every field on the map
    private void initFields(SimulationConfig config) {

        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {

                var newField = new MapField(new Vector2d(x, y),
                        jungleEmptyFields, fieldsWithoutAnimals,
                        fieldsContainingAnimals, config);

                fields.put(new Vector2d(x, y), newField);
            }
        }
    }

    @Override
    public abstract Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction);

    @Override
    public boolean place(Animal animal) {
        return fields.get(animal.getPosition()).addAnimal(animal);
    }

    @Override
    public Animal placeRandomAnimal() {


        //  var newAnimal = new Animal(map, position, config.start)
        return null;
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

                        snapshot.addAnimalsGroup(position, animalsGroup.length,
                                animalsGroup[0].getEnergy());
                    }
                }
            }
        }

        return snapshot;
    }
}
