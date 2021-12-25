package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class MapField {

    private final SimulationConfig configuration;
    private final SortedSet<Animal> animals = new TreeSet<Animal>();
    private final Set<MapField> emptyFields;
    private final Set<MapField> fieldsWithoutAnimals;
    private final Set<MapField> fieldsContainingAnimals;
    private boolean isGrassed = false;

    public MapField(Set<MapField> emptyFields,
            Set<MapField> fieldsWithoutAnimals,
            Set<MapField> fieldsContainingAnimals,
            SimulationConfig configuration) {

        this.emptyFields = emptyFields;
        this.fieldsWithoutAnimals = fieldsWithoutAnimals;
        this.fieldsContainingAnimals = fieldsContainingAnimals;
        this.configuration = configuration;

        emptyFields.add(this);
        fieldsWithoutAnimals.add(this);
    }

    public boolean addAnimal(Animal animal) {

        boolean result = animals.add(animal);
        if (result && animals.size() == 1) {

            if (!isGrassed)
                emptyFields.remove(this);

            fieldsWithoutAnimals.remove(this);
            fieldsContainingAnimals.add(this);
        }
        return result;
    }

    public boolean removeAnimal(Animal animal) {

        boolean result = animals.remove(animal);
        if (result && animals.isEmpty()) {

            if (!isGrassed)
                emptyFields.add(this);

            fieldsWithoutAnimals.add(this);
            fieldsContainingAnimals.remove(this);
        }
        return result;
    }

    public boolean addGrass() {

        if (animals.isEmpty() && !isGrassed) {
            isGrassed = true;
            emptyFields.remove(this);
            return true;
        }
        return false;
    }

    public void doEating() {

        if (animals.isEmpty())
            return;

        // get the list of animals with max energy
        var eatingAnimals = new LinkedList<Animal>();
        var it = animals.iterator();
        eatingAnimals.add(it.next());

        boolean equalEnergy = true;
        while (it.hasNext() && equalEnergy) {

            var animal = it.next();
            if (animal.getEnergy() == eatingAnimals.getLast().getEnergy())
                eatingAnimals.add(animal);
            else
                equalEnergy = false;
        }

        // divide plant energy equally among all animals with max energy
        int energyForEach = configuration.plantEnergy / eatingAnimals.size();
        eatingAnimals.forEach(animal -> animal.eat(energyForEach));

        /* out.println(eatingAnimals.toString());
        
        var s = animals.toArray(new Animal[0]);
        for (var a : s)
            out.println(a.getEnergy() + " "); */
    }

    //public Animal doReproducing() {}
}
