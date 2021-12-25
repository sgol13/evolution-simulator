package sgol13.evolution.simulator.simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import sgol13.evolution.simulator.SimulationConfig;

public abstract class AbstractMap implements IMap {

    private final Set<MapField> jungleEmptyFields = new HashSet<MapField>();
    private final Set<MapField> steppeEmptyFields = new HashSet<MapField>();
    private final Set<MapField> fieldsWithoutAnimals = new HashSet<MapField>();
    private final Set<MapField> fieldsContainingAnimals = new HashSet<MapField>();
    protected final Vector2d mapSize;

    protected final Map<Vector2d, MapField> fields = new HashMap<Vector2d, MapField>();

    public AbstractMap(SimulationConfig configuration) {

        this.mapSize = new Vector2d(configuration.mapWidth, configuration.mapHeight);
        initFields(configuration);
    }

    // create MapField object for every field on the map
    private void initFields(SimulationConfig configuration) {

        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {

                var newField = new MapField(jungleEmptyFields, fieldsWithoutAnimals,
                        fieldsContainingAnimals, configuration);

                fields.put(new Vector2d(x, y), newField);
            }
        }
    }

    @Override
    public abstract Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction);

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public MapField getField(Vector2d position) {
        return null;
    }
}
