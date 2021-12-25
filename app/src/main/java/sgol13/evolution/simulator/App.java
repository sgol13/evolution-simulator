package sgol13.evolution.simulator;

import sgol13.evolution.simulator.simulation.Genotype;
import static java.lang.System.out;

public class App {
    public static void main(String[] args) {

        var genotype1 = Genotype.createRandomGenotype();
        var genotype2 = Genotype.createRandomGenotype();

        out.println(genotype1);
        out.println(genotype2);
        var genotype3 = Genotype.createMixedGenotype(genotype1, 0, genotype2, 1);
        out.println(genotype3);

        // Integer[] t = {4, 5, 7, 2, 5, 7, 9};
        // Arrays.sort(t);
        // out.print(Arrays.toString(t));
    }
}
