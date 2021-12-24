package sgol13.evolution.simulator;

public class App {
    public static void main(String[] args) {

        var snap = new MapSnapshot(new Vector2d(5, 5));
        snap.addGrassField(new Vector2d(3, 3));
        System.out.println(snap);
    }
}
