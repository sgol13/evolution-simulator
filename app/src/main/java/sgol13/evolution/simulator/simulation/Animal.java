package sgol13.evolution.simulator.simulation;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    private static final Random randomGenerator = new Random();
    private static int animalsCounter = 0;

    private final int id;
    private Genotype genotype;
    private int energy = 0;
    private MoveDirection direction;
    private Vector2d position;
    private final IMap map;
    private final int moveEnergy;
    private int childrenNumber = 0;
    private long birthDay;

    // create an animal with random genotype
    public Animal(IMap map, Vector2d position, int startEnergy, int moveEnergy) {
        this(map, position, startEnergy, moveEnergy, Genotype.createRandomGenotype());
    }

    // create an animal with given genotype
    public Animal(IMap map, Vector2d position,
            int startEnergy, int moveEnergy, Genotype genotype) {

        this.id = animalsCounter++;
        this.map = map;
        this.position = position;
        this.energy = startEnergy;
        this.genotype = genotype;
        this.moveEnergy = moveEnergy;

        setRandomDirection();
    }

    public static Animal reproduce(Animal animal1, Animal animal2) {

        // random direction (in constructor), position is copied from parent
        Animal newAnimal =
                new Animal(animal1.map, animal1.position, 0, animal1.moveEnergy);
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

    public int getEnergy() {
        return energy;
    }

    public int getID() {
        return id;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setBirthDay(long daysCounter) {
        this.birthDay = daysCounter;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void move() {

        var relativeDirection = genotype.randomDirection();
        direction = direction.add(relativeDirection);

        // move only if the direction was FORWARD or BACKWARD
        if (relativeDirection == MoveDirection.FORWARD ||
                relativeDirection == MoveDirection.BACKWARD) {

            position = map.updatePosition(this, position, direction);
        }

        energy -= moveEnergy;
    }

    @Override
    public int compareTo(Animal other) {

        if (energy == other.energy) // each id is globally unique
            return id - other.id;

        return other.energy - energy;
    }

    @Override
    public boolean equals(Object other) {

        if (other instanceof Animal) {

            Animal otherAnimal = (Animal) other;
            return otherAnimal.id == id;
        }
        return false;
    }

    @Override
    public Animal clone() {
        return new Animal(map, position, 0, moveEnergy, genotype);
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    private void setRandomDirection() {

        int randDir = randomGenerator.nextInt(MoveDirection.getValuesNum());
        direction = MoveDirection.toDirection(randDir);
    }

    private int giveEnergyForChild() {

        childrenNumber++;
        int energyForChild = energy / 4;
        energy -= energyForChild;
        return energyForChild;
    }
}
