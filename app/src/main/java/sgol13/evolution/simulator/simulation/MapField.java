package sgol13.evolution.simulator.simulation;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class MapField implements Comparable<MapField> {

    private static final Random randomGenerator = new Random();

    private final SimulationConfig config;
    private final Vector2d position;
    private final SortedSet<Animal> animals = new TreeSet<Animal>();
    private boolean isGrassed = false;

    public MapField(Vector2d position, SimulationConfig config) {

        this.position = position;
        this.config = config;
    }

    @Override
    public int compareTo(MapField other) {

        if (position.x != other.position.x)
            return position.x - other.position.x;

        return position.y - other.position.y;
    }

    public boolean addAnimal(Animal animal) {
        return animals.add(animal);
    }

    public boolean removeAnimal(Animal animal) {
        return animals.remove(animal);
    }

    public boolean addGrass() {

        if (animals.isEmpty() && !isGrassed) {
            isGrassed = true;
            return true;
        }
        return false;
    }

    public boolean isGrassed() {
        return isGrassed;
    }

    public boolean isEmpty() {
        return animals.isEmpty();
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
        int energyForEach = config.plantEnergy / eatingAnimals.size();
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
        if (2 * animal2.getEnergy() >= config.startEnergy) {
            newAnimal = Animal.reproduce(animal1, animal2);
            animals.remove(animal1);
            animals.remove(animal2);

            animals.add(animal1);
            animals.add(animal2);
        }

        return newAnimal;
    }

    public Animal[] getAnimalsGroup() {
        return animals.toArray(new Animal[0]);
    }
}
