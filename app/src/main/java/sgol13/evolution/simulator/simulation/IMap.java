package sgol13.evolution.simulator.simulation;

import sgol13.evolution.simulator.SimulationConfig;

public interface IMap {

    Vector2d updatePosition(Animal animal, Vector2d position, MoveDirection direction);

    boolean place(Animal animal);

    MapField getField(Vector2d position);
}
