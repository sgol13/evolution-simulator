package sgol13.evolution.simulator;

public class SimulationConfig {

    public boolean boundedMap = true;

    public int mapWidth = 40;
    public int mapHeight = 30;
    public double jungleRatio = 0.2;

    public int startEnergy = 200;
    public int moveEnergy = 5;
    public int plantEnergy = 200;

    public int initialAnimals = 400;

    public boolean magicStrategy = false;
    public long defaultDaytime = 50;
    public int defaultDaysPerFrame = 1;
}
