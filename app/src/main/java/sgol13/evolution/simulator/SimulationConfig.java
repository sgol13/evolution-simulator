package sgol13.evolution.simulator;

public class SimulationConfig {

    public boolean boundedMap = true;

    public int mapWidth = 40;
    public int mapHeight = 25;
    public double jungleRatio = 0.2;

    public int startEnergy = 200;
    public int moveEnergy = 1;
    public int plantEnergy = 30;

    public int initialAnimals = 100;

    public boolean magicStrategy = false;
    public long defaultDaytime = 50;
    public int defaultDaysPerFrame = 1;
}
