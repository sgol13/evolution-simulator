package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import org.junit.Test;
import sgol13.evolution.simulator.SimulationConfig;
import static java.lang.System.out;

public class MapFieldTest {

    @Test
    public void addRemoveTest() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 0);
        var an2 = new Animal(null, 0);
        var an3 = new Animal(null, 0);

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, null);

        assertTrue(emptyFields.size() == 1);
        assertTrue(fieldsWithoutAnimals.size() == 1);
        assertTrue(fieldsContainingAnimals.size() == 0);

        assertTrue(field.addAnimal(an1));

        assertTrue(emptyFields.size() == 0);
        assertTrue(fieldsWithoutAnimals.size() == 0);
        assertTrue(fieldsContainingAnimals.size() == 1);

        assertTrue(field.addAnimal(an2));
        assertTrue(field.addAnimal(an3));

        assertTrue(emptyFields.size() == 0);
        assertTrue(fieldsWithoutAnimals.size() == 0);
        assertTrue(fieldsContainingAnimals.size() == 1);

        assertTrue(field.removeAnimal(an2));
        assertFalse(field.addGrass());

        assertTrue(emptyFields.size() == 0);
        assertTrue(fieldsWithoutAnimals.size() == 0);
        assertTrue(fieldsContainingAnimals.size() == 1);

        assertTrue(field.removeAnimal(an1));
        assertTrue(field.removeAnimal(an3));

        assertFalse(field.removeAnimal(an2));

        assertTrue(emptyFields.size() == 1);
        assertTrue(fieldsWithoutAnimals.size() == 1);
        assertTrue(fieldsContainingAnimals.size() == 0);

        assertTrue(field.addGrass());

        assertTrue(emptyFields.size() == 0);
        assertTrue(fieldsWithoutAnimals.size() == 1);
        assertTrue(fieldsContainingAnimals.size() == 0);
    }

    @Test
    public void doEatingTest1() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 10);
        var an2 = new Animal(null, 7);
        var an3 = new Animal(null, 9);
        var an4 = new Animal(null, 3);
        var an5 = new Animal(null, 5);
        var an6 = new Animal(null, 10);
        var an7 = new Animal(null, 10);

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
        field.doEating();
        field.doEating();

        assertTrue(an1.getEnergy() == 19);
        assertTrue(an2.getEnergy() == 7);
        assertTrue(an3.getEnergy() == 9);
        assertTrue(an4.getEnergy() == 3);
        assertTrue(an5.getEnergy() == 5);
        assertTrue(an6.getEnergy() == 19);
        assertTrue(an7.getEnergy() == 19);
    }

    @Test
    public void doEatingTest2() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 10);
        var an2 = new Animal(null, 7);
        var an3 = new Animal(null, 9);
        var an4 = new Animal(null, 3);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);

        field.doEating();
        field.doEating();

        assertTrue(an1.getEnergy() == 30);
        assertTrue(an2.getEnergy() == 7);
        assertTrue(an3.getEnergy() == 9);
        assertTrue(an4.getEnergy() == 3);
    }

    @Test
    public void doReproducingTest1() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 10);
        var an2 = new Animal(null, 7);
        var an3 = new Animal(null, 9);
        var an4 = new Animal(null, 3);
        var an5 = new Animal(null, 5);
        var an6 = new Animal(null, 10);
        var an7 = new Animal(null, 10);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 8;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);
        field.addAnimal(an5);
        field.addAnimal(an6);
        field.addAnimal(an7);

        var an0 = field.doReproducing();
        assertFalse(an0 == null);
        out.println(an0.getEnergy());
        assertTrue(an0.getEnergy() == 4);
    }

    @Test
    public void doReproducingTest2() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 20);
        var an2 = new Animal(null, 16);
        var an3 = new Animal(null, 16);
        var an4 = new Animal(null, 3);
        var an5 = new Animal(null, 5);
        var an6 = new Animal(null, 10);
        var an7 = new Animal(null, 10);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 8;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);
        field.addAnimal(an5);
        field.addAnimal(an6);
        field.addAnimal(an7);

        var an0 = field.doReproducing();
        assertFalse(an0 == null);
        out.println(an0.getEnergy());
        assertTrue(an0.getEnergy() == 9);
    }

    @Test
    public void doReproducingTest3() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 20);
        var an2 = new Animal(null, 16);
        var an3 = new Animal(null, 16);
        var an4 = new Animal(null, 3);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 42;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);

        var an0 = field.doReproducing();
        assertTrue(an0 == null);
    }

    @Test
    public void doReproducingTest4() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 20);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 13;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);

        var an0 = field.doReproducing();
        assertTrue(an0 == null);
    }

    @Test
    public void doReproducingTest5() {

        var emptyFields = new HashSet<MapField>();
        var fieldsWithoutAnimals = new HashSet<MapField>();
        var fieldsContainingAnimals = new HashSet<MapField>();

        var an1 = new Animal(null, 20);
        var an2 = new Animal(null, 15);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 10;

        var field = new MapField(emptyFields,
                fieldsWithoutAnimals, fieldsContainingAnimals, config);

        field.addAnimal(an1);
        field.addAnimal(an2);

        var an0 = field.doReproducing();
        assertFalse(an0 == null);
        // out.println(an0.getEnergy());
        assertTrue(an0.getEnergy() == 8);
    }
}
