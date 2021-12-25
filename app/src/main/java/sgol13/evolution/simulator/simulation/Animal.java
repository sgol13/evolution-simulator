package sgol13.evolution.simulator.simulation;

import java.util.Random;

public class Animal {

    private static final Random randomGenerator = new Random();

    private Genotype genotype;
    private int energy;
    private MoveDirection direction;
    private Vector2d position;
    private final IWorldMap map;

    public Animal(IWorldMap map) {

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

    private void setRandomDirection() {

        int randDir = randomGenerator.nextInt(MoveDirection.getValuesNum());
        direction = MoveDirection.toDirection(randDir);
    }

    private int giveEnergyForChild() {

        energy -= energy / 4;
        return energy;
    }

    public void move() {

        direction = direction.add(genotype.randomDirection());

    }

    /*     private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (var observer : observersList)
            observer.positionChanged(new Vector2d(oldPosition), new Vector2d(newPosition));
    }
     */

}
