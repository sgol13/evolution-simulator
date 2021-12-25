package sgol13.evolution.simulator.simulation;

import sgol13.evolution.simulator.SimulationConfig;

public class UnboundedMap extends AbstractMap {

    public UnboundedMap(SimulationConfig configuration) {
        super(configuration);
    }

    @Override
    public Vector2d updatePosition(Animal animal,
            Vector2d position, MoveDirection direction) {

        var plannedPosition = position.add(direction.toUnitVector());
        int x = (plannedPosition.x + mapSize.x) % mapSize.x;
        int y = (plannedPosition.y + mapSize.y) % mapSize.y;

        var newPosition = new Vector2d(x, y);

        fields.get(position).removeAnimal(animal);
        fields.get(newPosition).addAnimal(animal);

        return newPosition;
    }

}
