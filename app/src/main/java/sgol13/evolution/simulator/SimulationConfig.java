package sgol13.evolution.simulator;

public class SimulationConfig {

    public boolean isBoundedMap = true;

    public int mapWidth = 40;
    public int mapHeight = 40;
    public double jungleRatio = 0.2;

    public int startEnergy = 200;
    public int moveEnergy = 5;
    public int plantEnergy = 200;

    public int initialAnimals = 200;

    public boolean magicStrategy = false;
    public long minSpeedDayTime = 500;
    public double defaultSimulationSpeed = 10;
}
