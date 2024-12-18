import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Platform> platforms;
    private List<Key> keys;
    private Door door;
    private float playerStartX;
    private float playerStartY;
    private int levelNumber;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        platforms = new ArrayList<>();
        keys = new ArrayList<>();
        initializeLevel();
    }

    private void initializeLevel() {
        switch (levelNumber) {
            case 1:
                createLevel1();
                break;
            case 2:
                createLevel2();
                break;
            // Adaugă mai multe nivele aici
        }
    }

    private void createLevel1() {
        // Platforme nivel 1
        platforms.add(new Platform(0, 580, 800, 20));  // Podea
        platforms.add(new Platform(100, 500, 200, 20));
        platforms.add(new Platform(400, 400, 200, 20));

        // Cheie
        keys.add(new Key(500, 300));

        // Ușă
        door = new Door(700, 520);

        // Poziția de start a jucătorului
        playerStartX = 50;
        playerStartY = 500;
    }

    private void createLevel2() {
        // Platforme nivel 2
        platforms.add(new Platform(0, 580, 800, 20));
        platforms.add(new Platform(200, 500, 100, 20));
        platforms.add(new Platform(400, 400, 100, 20));
        platforms.add(new Platform(600, 300, 100, 20));

        // Cheie
        keys.add(new Key(650, 250));

        // Ușă
        door = new Door(700, 520);

        // Poziția de start a jucătorului
        playerStartX = 50;
        playerStartY = 500;
    }

    // Getteri
    public List<Platform> getPlatforms() { return platforms; }
    public List<Key> getKeys() { return keys; }
    public Door getDoor() { return door; }
    public float getPlayerStartX() { return playerStartX; }
    public float getPlayerStartY() { return playerStartY; }
}