package sgol13.evolution.simulator.simulation;

import java.util.Arrays;
import java.util.Random;
import static java.lang.System.arraycopy;

public class Genotype {

    private static final int GENOTYPE_SIZE = 32;
    private static final int GENES_VALUES_NUM = 8;

    private final int[] genes = new int[GENOTYPE_SIZE];
    private static final Random randomGenerator = new Random();

    // private constructor because new genotypes can be created only by static functions
    private Genotype() {}

    public static Genotype createRandomGenotype() {

        var new_genotype = new Genotype();

        for (int i = 0; i < GENOTYPE_SIZE; i++)
            new_genotype.genes[i] = randomGenerator.nextInt(GENES_VALUES_NUM);

        Arrays.sort(new_genotype.genes);

        return new_genotype;
    }

    public static Genotype createMixedGenotype(Genotype genotype1, int energy1,
            Genotype genotype2, int energy2) {

        var new_genotype = new Genotype();

        // division point, number of genes from genotype1
        // set in proportion to genotypes' energies
        int div = (energy1 * GENOTYPE_SIZE) / (energy1 + energy2);

        // random part from genotype1 (left if true or rigth if false)
        if (randomGenerator.nextInt(2) % 2 == 0) {
            arraycopy(genotype1.genes, 0, new_genotype.genes, 0, div);
            arraycopy(genotype2.genes, div, new_genotype.genes, div, GENOTYPE_SIZE - div);

        } else {
            div = GENOTYPE_SIZE - div;
            arraycopy(genotype2.genes, 0, new_genotype.genes, 0, div);
            arraycopy(genotype1.genes, div, new_genotype.genes, div, GENOTYPE_SIZE - div);
        }
        Arrays.sort(new_genotype.genes);

        return new_genotype;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public boolean equals(Object other) {

        if (other == null)
            return false;

        if (other == this)
            return true;

        if (other instanceof Genotype)
            return Arrays.equals(genes, ((Genotype) other).genes);

        return false;
    }

    public MoveDirection randomDirection() {

        int gene = genes[randomGenerator.nextInt(GENOTYPE_SIZE)];
        return MoveDirection.toDirection(gene);
    }

    public String toString() {

        var counters = new int[GENES_VALUES_NUM];
        for (int genValue = 0; genValue < GENES_VALUES_NUM; genValue++) {

            counters[genValue] = 0;
            for (int i = 0; i < GENOTYPE_SIZE; i++) {
                if (genes[i] == genValue)
                    counters[genValue]++;
                else if (genes[i] > genValue)
                    break;
            }
        }

        return Arrays.toString(counters);
    }
}
