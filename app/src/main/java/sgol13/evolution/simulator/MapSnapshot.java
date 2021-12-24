package sgol13.evolution.simulator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapSnapshot {

    private class AnimalsField {
        public int animalsNumber;
        public int maxEnergy;

        AnimalsField(int animalsNumber, int maxEnergy) {
            this.animalsNumber = animalsNumber;
            this.maxEnergy = maxEnergy;
        }
    };

    private final Vector2d mapSize;
    private final Set<Vector2d> grassFields = new HashSet<Vector2d>();
    private final Map<Vector2d, AnimalsField> animalsFields =
            new HashMap<Vector2d, AnimalsField>();

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

    public void addGrassField(Vector2d position) {
        grassFields.add(position);
    }

    public void addAnimalsGroup(Vector2d position, int animalsNumber, int maxEnergy) {
        animalsFields.put(position, new AnimalsField(animalsNumber, maxEnergy));
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

        var ss = new StringBuilder();

        for (int row = 0; row < mapSize.y; row++) {
            for (int col = 0; col < mapSize.x; col++) {

                int animalsNum = getAnimalsNumber(row, col);
                if (animalsNum > 0) {
                    if (animalsNum >= 10)
                        ss.append("0");
                    else
                        ss.append(animalsNum);
                } else if (isGrassed(row, col)) {
                    ss.append('#');
                } else {
                    ss.append(' ');
                }
            }
            ss.append("|\n");
        }

        return ss.toString();
    }
}
