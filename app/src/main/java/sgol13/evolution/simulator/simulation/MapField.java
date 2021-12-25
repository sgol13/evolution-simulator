package sgol13.evolution.simulator.simulation;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapField {

    private final SortedSet<Animal> animals = new TreeSet<Animal>();
    private final Set<MapField> emptyFields;
    private final Set<MapField> fieldsWithoutAnimals;
    private final Set<MapField> fieldsContainingAnimals;
    private boolean isGrassed = false;

    public MapField(Set<MapField> emptyFields,
            Set<MapField> fieldsWithoutAnimals,
            Set<MapField> fieldsContainingAnimals) {

        this.emptyFields = emptyFields;
        this.fieldsWithoutAnimals = fieldsWithoutAnimals;
        this.fieldsContainingAnimals = fieldsContainingAnimals;

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

    public boolean putGrass() {

        if (animals.isEmpty() && !isGrassed) {
            isGrassed = true;
            return true;
        }
        return false;
    }

    public void doEating() {}

    //public Animal doReproducing() {}
}
