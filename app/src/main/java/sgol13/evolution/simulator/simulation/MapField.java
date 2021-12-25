package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class MapField {

    private static final Random randomGenerator = new Random();

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

    public boolean doEating() {

        if (animals.isEmpty() || !isGrassed)
            return false;

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

        isGrassed = false;
        return true;
    }

    // returns an animal that was born (null if the animals didn't reproduce)
    public Animal doReproducing() {

        if (animals.size() < 2)
            return null;

        // get the list of animals with max energy (at least two)
        var reproducingAnimals = new LinkedList<Animal>();
        var it = animals.iterator();
        reproducingAnimals.add(it.next());

        boolean equalEnergy = true;
        while (it.hasNext() && (equalEnergy || reproducingAnimals.size() < 2)) {

            var animal = it.next();
            out.println(animal.getEnergy());
            equalEnergy = animal.getEnergy() == reproducingAnimals.getLast().getEnergy();

            if (equalEnergy || reproducingAnimals.size() < 2)
                reproducingAnimals.add(animal);

            equalEnergy = animal.getEnergy() == reproducingAnimals.getLast().getEnergy();
        }

        for (var a : reproducingAnimals)
            out.print(a.getEnergy() + " ");
        out.println("");

        // find indices for random selection
        int equalsFirst = 0;
        while (equalsFirst < reproducingAnimals.size() &&
                reproducingAnimals.get(equalsFirst).getEnergy() == reproducingAnimals.getFirst()
                        .getEnergy()) {
            equalsFirst++;
        }

        int equalsLast = reproducingAnimals.size() - equalsFirst;

        // choose reproducing animals randomly if the list is longer than 2
        int index1 = randomGenerator.nextInt(equalsFirst);
        int index2 = 0;

        if (equalsLast == 0)
            index2 = randomGenerator.nextInt(equalsFirst);
        else
            index2 = randomGenerator.nextInt(equalsLast) + equalsFirst;

        var animal1 = reproducingAnimals.get(index1);
        var animal2 = reproducingAnimals.get(index2);

        // reproduce on condition that the energy level is sufficient
        // (at least 50% of the startEnergy)
        Animal newAnimal = null;
        if (2 * animal2.getEnergy() >= configuration.startEnergy)
            newAnimal = Animal.reproduce(animal1, animal2);

        return newAnimal;
    }

    public Animal[] getAnimals() {
        return animals.toArray(new Animal[0]);
    }
}
