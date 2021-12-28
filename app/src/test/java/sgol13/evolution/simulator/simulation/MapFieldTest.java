package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static java.lang.System.out;

public class MapFieldTest {

    @Test
    public void addRemoveTest() {

        var an1 = new Animal(null, null, 0, 0);
        var an2 = new Animal(null, null, 0, 0);
        var an3 = new Animal(null, null, 0, 0);

        var field = new MapField(new Vector2d(0, 0), null);

        assertTrue(field.addAnimal(an1));
        assertTrue(field.addAnimal(an2));
        assertTrue(field.addAnimal(an3));
        assertTrue(field.removeAnimal(an2));
        assertFalse(field.addGrass());
        assertTrue(field.removeAnimal(an1));
        assertTrue(field.removeAnimal(an3));
        assertFalse(field.removeAnimal(an2));
        assertTrue(field.addGrass());
    }

    @Test
    public void doEatingTest1() {

        var an1 = new Animal(null, null, 10, 0);
        var an2 = new Animal(null, null, 7, 0);
        var an3 = new Animal(null, null, 9, 0);
        var an4 = new Animal(null, null, 3, 0);
        var an5 = new Animal(null, null, 5, 0);
        var an6 = new Animal(null, null, 10, 0);
        var an7 = new Animal(null, null, 10, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;

        var field = new MapField(new Vector2d(0, 0), config);
        field.addGrass();

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);
        field.addAnimal(an5);
        field.addAnimal(an6);
        field.addAnimal(an7);

        field.doEating();

        assertTrue(an1.getEnergy() == 13);
        assertTrue(an2.getEnergy() == 7);
        assertTrue(an3.getEnergy() == 9);
        assertTrue(an4.getEnergy() == 3);
        assertTrue(an5.getEnergy() == 5);
        assertTrue(an6.getEnergy() == 13);
        assertTrue(an7.getEnergy() == 13);
    }

    @Test
    public void doEatingTest2() {

        var an1 = new Animal(null, null, 10, 0);
        var an2 = new Animal(null, null, 7, 0);
        var an3 = new Animal(null, null, 9, 0);
        var an4 = new Animal(null, null, 3, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;

        var field = new MapField(new Vector2d(0, 0), config);
        field.addGrass();

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);

        field.doEating();
        field.doEating();

        assertTrue(an1.getEnergy() == 20);
        assertTrue(an2.getEnergy() == 7);
        assertTrue(an3.getEnergy() == 9);
        assertTrue(an4.getEnergy() == 3);
    }

    @Test
    public void doReproducingTest1() {

        var an1 = new Animal(null, null, 10, 0);
        var an2 = new Animal(null, null, 7, 0);
        var an3 = new Animal(null, null, 9, 0);
        var an4 = new Animal(null, null, 3, 0);
        var an5 = new Animal(null, null, 5, 0);
        var an6 = new Animal(null, null, 10, 0);
        var an7 = new Animal(null, null, 10, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 8;

        var field = new MapField(new Vector2d(0, 0), config);

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

        var an1 = new Animal(null, null, 20, 0);
        var an2 = new Animal(null, null, 16, 0);
        var an3 = new Animal(null, null, 16, 0);
        var an4 = new Animal(null, null, 3, 0);
        var an5 = new Animal(null, null, 5, 0);
        var an6 = new Animal(null, null, 10, 0);
        var an7 = new Animal(null, null, 10, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 8;

        var field = new MapField(new Vector2d(0, 0), config);

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

        var an1 = new Animal(null, null, 20, 0);
        var an2 = new Animal(null, null, 16, 0);
        var an3 = new Animal(null, null, 16, 0);
        var an4 = new Animal(null, null, 3, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 42;

        var field = new MapField(new Vector2d(0, 0), config);

        field.addAnimal(an1);
        field.addAnimal(an2);
        field.addAnimal(an3);
        field.addAnimal(an4);

        var an0 = field.doReproducing();
        assertTrue(an0 == null);
    }

    @Test
    public void doReproducingTest4() {

        var an1 = new Animal(null, null, 20, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 13;

        var field = new MapField(new Vector2d(0, 0), config);

        field.addAnimal(an1);

        var an0 = field.doReproducing();
        assertTrue(an0 == null);
    }

    @Test
    public void doReproducingTest5() {

        var an1 = new Animal(null, null, 20, 0);
        var an2 = new Animal(null, null, 15, 0);

        SimulationConfig config = new SimulationConfig();
        config.plantEnergy = 10;
        config.startEnergy = 10;

        var field = new MapField(new Vector2d(0, 0), config);

        field.addAnimal(an1);
        field.addAnimal(an2);

        var an0 = field.doReproducing();
        assertFalse(an0 == null);
        // out.println(an0.getEnergy());
        assertTrue(an0.getEnergy() == 8);
    }
}
