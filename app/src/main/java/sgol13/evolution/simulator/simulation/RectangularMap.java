package sgol13.evolution.simulator.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
import sgol13.evolution.simulator.SimulationConfig;
import sgol13.evolution.simulator.snapshots.MapSnapshot;
import static java.lang.System.out;

public abstract class RectangularMap implements IMap {

    private final SimulationConfig config;
    private final Random randomGenerator = new Random();
    protected final Vector2d mapSize;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;

    protected final LinkedHashMap<Vector2d, MapField> fields =
            new LinkedHashMap<Vector2d, MapField>();

    public RectangularMap(SimulationConfig config) {

        this.config = config;
        this.mapSize = new Vector2d(config.mapWidth, config.mapHeight);
        initJungle();
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

    // calculates and saves jungle rectangle
    private void initJungle() {

        double sqrtRatio = Math.sqrt(config.jungleRatio);
        int x1 = (int) Math.round(mapSize.x * (1.0 - sqrtRatio) / 2.0);
        int y1 = (int) Math.round(mapSize.y * (1.0 - sqrtRatio) / 2.0);

        int x2 = mapSize.x - x1;
        int y2 = mapSize.y - y1;

        jungleLowerLeft = new Vector2d(x1, y1);
        jungleUpperRight = new Vector2d(x2, y2);
    }

    @Override
    public abstract Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction);

    @Override
    public boolean placeAnimal(Animal animal) {
        return fields.get(animal.getPosition()).addAnimal(animal);
    }

    @Override
    public void placeAnimalOnRandomField(Animal animal) {

        Vector2d position;
        do {
            int x = randomGenerator.nextInt(mapSize.x);
            int y = randomGenerator.nextInt(mapSize.y);
            position = new Vector2d(x, y);

        } while (!fields.get(position).isEmpty());

        animal.setPosition(position);
        placeAnimal(animal);
    }

    @Override
    public LinkedList<Animal> placeRandomAnimals(int animalsNum) {

        // get all empty positions on the map
        var emptyPositions = new LinkedList<Vector2d>();
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
    public void placeTwoRandomGrassFields() {

        // get all empty positions on the map
        var emptyJunglePositions = new ArrayList<Vector2d>();
        var emptySteppePositions = new ArrayList<Vector2d>();

        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {
                var position = new Vector2d(x, y);
                var field = fields.get(position);
                if (field.isEmpty() && !field.isGrassed()) {

                    if (isJungle(position))
                        emptyJunglePositions.add(position);
                    else
                        emptySteppePositions.add(position);
                }
            }
        }


        if (!emptyJunglePositions.isEmpty())
            placeGrassOnRandomPosition(emptyJunglePositions);

        if (!emptySteppePositions.isEmpty())
            placeGrassOnRandomPosition(emptySteppePositions);
    }

    private void placeGrassOnRandomPosition(ArrayList<Vector2d> positions) {

        int index = randomGenerator.nextInt(positions.size());
        var position = positions.get(index);

        fields.get(position).addGrass();
    }

    private boolean isJungle(Vector2d position) {

        return position.follows(jungleLowerLeft) &&
                position.strictlyPrecedes(jungleUpperRight);
    }

    @Override
    public MapField getField(Vector2d position) {
        return fields.get(position);
    }

    @Override
    public MapSnapshot getMapSnapshot() {

        var snapshot = new MapSnapshot(mapSize);
        snapshot.setJungle(jungleLowerLeft, jungleUpperRight);
        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {

                var position = new Vector2d(x, y);
                var field = fields.get(position);
                if (field.isGrassed())
                    snapshot.addGrassField(position);
                else {

                    var animalsGroup = field.getAnimalsGroup();
                    if (animalsGroup.length > 0) {
                        snapshot.addAnimalsGroup(animalsGroup);
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

    @Override
    public int getGrassFieldsNum() {

        int grassFieldsNum = 0;
        for (int x = 0; x < mapSize.x; x++)
            for (int y = 0; y < mapSize.y; y++)
                if (fields.get(new Vector2d(x, y)).isGrassed())
                    grassFieldsNum++;

        return grassFieldsNum;
    }
}
