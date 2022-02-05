// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.snapshots;

import sgol13.evolution.simulator.simulation.Animal;
import sgol13.evolution.simulator.simulation.Genotype;

public class ObservedAnimalSnapshot {

    private int id;
    private Genotype genotype;
    private int energy;
    private int childrenNumber;

    public ObservedAnimalSnapshot(Animal animal) {

        id = animal.getID();
        genotype = animal.getGenotype();
        energy = animal.getEnergy();
        childrenNumber = animal.getChildrenNumber();
    }

    public int getID() {
        return id;
    }

    public String getGenotype() {
        return genotype.toString();
    }

    public int getEnergy() {
        return energy;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }
}
