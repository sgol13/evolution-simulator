package sgol13.evolution.simulator.simulation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import org.junit.Test;

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

}
