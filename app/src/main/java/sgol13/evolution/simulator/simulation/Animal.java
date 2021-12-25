package sgol13.evolution.simulator.simulation;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    private static final Random randomGenerator = new Random();
    private static int animalsCounter = 0;

    private final int id;
    private Genotype genotype;
    private int energy;
    private MoveDirection direction;
    private Vector2d position;
    private final IMap map;

    public Animal(IMap map) {

        this.id = animalsCounter++;
        this.map = map;
        setRandomDirection();
    }

    public static Animal reproduce(Animal animal1, Animal animal2) {

        // random direction (in constructor), position is copied from parent
        Animal newAnimal = new Animal(animal1.map);
        newAnimal.position = animal1.position;

        // genotype is a mix of parents' genotypes
        newAnimal.genotype = Genotype.createMixedGenotype(
                animal1.genotype, animal1.energy,
                animal2.genotype, animal2.energy);

        // each parent gives 1/4 of its energy to a child
        newAnimal.energy = animal1.giveEnergyForChild() + animal2.giveEnergyForChild();
        return newAnimal;
    }

    public void eat(int addEnergy) {
        energy += addEnergy;
    }

    public void move() {

        var relativeDirection = genotype.randomDirection();
        direction = direction.add(relativeDirection);

        // move only if the direction was FORWARD or BACKWARD
        if (relativeDirection == MoveDirection.FORWARD ||
                relativeDirection == MoveDirection.BACKWARD) {

            Vector2d newPosition = position.add(direction.toUnitVector());

            if (map.canMoveTo(newPosition)) {


                // move
                // change position in map
            }

        }

    }

    @Override
    public int compareTo(Animal other) {

        if (energy == other.energy) // each id is globally unique
            return id - other.id;

        return other.energy - energy;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    private void setRandomDirection() {

        int randDir = randomGenerator.nextInt(MoveDirection.getValuesNum());
        direction = MoveDirection.toDirection(randDir);
    }

    private int giveEnergyForChild() {

        energy -= energy / 4;
        return energy;
    }
}
