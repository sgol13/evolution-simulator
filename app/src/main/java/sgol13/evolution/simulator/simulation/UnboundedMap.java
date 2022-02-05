// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.simulation;

public class UnboundedMap extends RectangularMap {

    public UnboundedMap(SimulationConfig config) {
        super(config);
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
