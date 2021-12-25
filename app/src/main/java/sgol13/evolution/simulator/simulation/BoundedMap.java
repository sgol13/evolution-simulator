package sgol13.evolution.simulator.simulation;

import sgol13.evolution.simulator.SimulationConfig;

public class BoundedMap extends AbstractMap {

    public BoundedMap(SimulationConfig configuration) {
        super(configuration);
    }

    @Override
    public Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction) {

        var newPosition = position.add(direction.toUnitVector());

        if (newPosition.follows(new Vector2d(0, 0)) &&
                newPosition.strictlyPrecedes(mapSize)) {

            fields.get(position).removeAnimal(animal);
            fields.get(newPosition).addAnimal(animal);

            return newPosition;
        }

        return position;
    }

}
