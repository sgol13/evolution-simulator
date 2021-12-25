package sgol13.evolution.simulator;

import sgol13.evolution.simulator.simulation.Animal;
import sgol13.evolution.simulator.simulation.MapField;
import static java.lang.System.out;
import java.util.HashSet;

public class App {
    public static void main(String[] args) {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 10);
        var an2 = new Animal(null, 7);
        var an3 = new Animal(null, 10);
        var an4 = new Animal(null, 3);
        var an5 = new Animal(null, 2);
        var an6 = new Animal(null, 10);
        var an7 = new Animal(null, 8);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);
        field.addAnimal(an5);
        field.addAnimal(an6);
        field.addAnimal(an7);

        field.doEating();
    }
}
