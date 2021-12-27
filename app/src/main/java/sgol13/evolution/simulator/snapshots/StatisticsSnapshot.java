package sgol13.evolution.simulator.snapshots;

import java.util.HashMap;
import java.util.Map;
import sgol13.evolution.simulator.simulation.Animal;
import sgol13.evolution.simulator.simulation.Genotype;

public class StatisticsSnapshot {

    private int animalsNum = 0;
    private int grassFieldsNum = 0;
    private double totalEnergyLevel = 0;
    private double averageLifespan = 0;
    private double totalChildrenNum = 0;

    private Map<Genotype, Integer> genotypesCounters = new HashMap<Genotype, Integer>();

    public int getAnimalsNum() {
        return animalsNum;
    }

    public int getGrassFieldsNum() {
        return grassFieldsNum;
    }

    public double getAverageEnergyLevel() {
        return totalEnergyLevel / animalsNum;
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }

    public double getAverageChildrenNum() {
        return totalChildrenNum / animalsNum;
    }

    public String getDominantGenotype() {

        Map.Entry<Genotype, Integer> maxEntry = null;
        for (var entry : genotypesCounters.entrySet()) {
            if (maxEntry == null ||
                    entry.getValue().compareTo(maxEntry.getValue()) > 0) {

                maxEntry = entry;
            }
        }

        return maxEntry != null ? maxEntry.getKey().toString() : null;
    }

    public void addAnimal(Animal animal) {

        animalsNum++;
        totalChildrenNum += animal.getChildrenNumber();
        totalEnergyLevel += animal.getEnergy();

        var genotype = animal.getGenotype();
        if (genotypesCounters.containsKey(genotype)) {
            genotypesCounters.put(genotype, genotypesCounters.get(genotype));
        } else {
            genotypesCounters.put(genotype, 1);
        }
    }

    public void setGrassFieldsNum(int grassFieldsNum) {
        this.grassFieldsNum = grassFieldsNum;
    }

    public void setAverageLifespan(double averageLifespan) {
        this.averageLifespan = averageLifespan;
    }
}
