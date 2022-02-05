// Szymon Golebiowski
// Evolution Simulator

package sgol13.evolution.simulator.snapshots;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import sgol13.evolution.simulator.simulation.Animal;
import sgol13.evolution.simulator.simulation.Genotype;
import sgol13.evolution.simulator.simulation.Vector2d;

public class MapSnapshot {

    private class AnimalsField {
        public int animalsNumber;
        public int maxEnergy;
        public boolean isDominantGenotype = false;

        AnimalsField(int animalsNumber, int maxEnergy) {

            this.animalsNumber = animalsNumber;
            this.maxEnergy = maxEnergy;
        }
    };

    private final Vector2d mapSize;
    private final Set<Vector2d> grassFields = new HashSet<Vector2d>();
    private final Map<Vector2d, AnimalsField> animalsFields =
            new HashMap<Vector2d, AnimalsField>();
    private Vector2d observedAnimalPosition;
    private Genotype dominantGenotype;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;

    public MapSnapshot(Vector2d mapSize) {
        this.mapSize = mapSize;
    }

    public MapSnapshot(int height, int width) {
        this.mapSize = new Vector2d(width, height);
    }

    public Vector2d getDimensions() {
        return mapSize;
    }

    public int getHeight() {
        return mapSize.y;
    }

    public int getWidth() {
        return mapSize.x;
    }

    public int getObservedAnimalRow() {
        if (observedAnimalPosition != null)
            return mapSize.y - observedAnimalPosition.y - 1;
        return -1;
    }

    public int getObservedAnimalColumn() {
        if (observedAnimalPosition != null)
            return observedAnimalPosition.x;
        return -1;
    }

    public void addGrassField(Vector2d position) {
        grassFields.add(position);
    }

    public void addAnimalsGroup(Animal[] animalsGroup) {

        var position = animalsGroup[0].getPosition();
        Arrays.sort(animalsGroup);

        animalsFields.put(
                position,
                new AnimalsField(animalsGroup.length, animalsGroup[0].getEnergy()));
    }

    public void addDominantGenotypePositions(Set<Vector2d> dominantPositions) {

        for (var position : dominantPositions)
            animalsFields.get(position).isDominantGenotype = true;
    }

    public boolean isDominantGenotype(int row, int col) {

        var field = animalsFields.get(calculateVector(row, col));
        if (field != null)
            return field.isDominantGenotype;

        return false;
    }

    public void setObservedAnimal(Animal observedAnimal) {
        if (observedAnimal != null)
            observedAnimalPosition = observedAnimal.getPosition();
    }

    public int getAnimalsNumber(int row, int col) {

        var field = animalsFields.get(calculateVector(row, col));
        return field != null ? field.animalsNumber : 0;
    }

    public int getMaxEnergy(int row, int col) {

        var field = animalsFields.get(calculateVector(row, col));
        return field != null ? field.maxEnergy : null;
    }

    public boolean isGrassed(int row, int col) {
        return grassFields.contains(calculateVector(row, col));
    }

    private Vector2d calculateVector(int row, int col) {
        return new Vector2d(col, mapSize.y - row - 1);
    }

    public String toString() {

        var sb = new StringBuilder();

        for (int row = 0; row < mapSize.y; row++) {
            for (int col = 0; col < mapSize.x; col++) {

                int animalsNum = getAnimalsNumber(row, col);
                if (animalsNum > 0) {
                    if (animalsNum >= 10)
                        sb.append("0");
                    else
                        sb.append(animalsNum);
                } else if (isGrassed(row, col)) {
                    sb.append('#');
                } else {
                    sb.append(' ');
                }
            }
            sb.append("|\n");
        }

        return sb.toString();
    }

    public void setDominantGenotype(Genotype genotype) {
        dominantGenotype = genotype;
    }

    public String getDominantGenotype() {

        if (dominantGenotype == null)
            return null;

        return dominantGenotype.toString();
    }

    public void setJungle(Vector2d jungleLowerLeft, Vector2d jungleUpperRight) {

        this.jungleLowerLeft = jungleLowerLeft;
        this.jungleUpperRight = jungleUpperRight;
    }

    public int[] getJungleSpan() {

        int x1 = jungleLowerLeft.x;
        int y1 = jungleLowerLeft.y;
        int x2 = jungleUpperRight.x - 1;
        int y2 = jungleUpperRight.y - 1;

        int[] jungleSpan = {x1, mapSize.y - y2 - 1, x2 - x1 + 1, y2 - y1 + 1};
        return jungleSpan;
    }
}
