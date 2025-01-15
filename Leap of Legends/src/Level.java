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
            case 3:
                createLevel3();
                break;
            case 4:
                createLevel4();
                break;
            case 5:
                createLevel5();
                break;
            default:
                break;
        }
    }

    private void createLevel1() {
        platforms.add(new Platform(0, 550, 800, 20));

        platforms.add(new Platform(200, 450, 100, 20));
        platforms.add(new Platform(400, 450, 100, 20));

        keys.add(new Key(430, 400));

        door = new Door(700, 490);

        playerStartX = 50;
        playerStartY = 500;
    }

    // Nivel 2 - Platforme ascendente
    private void createLevel2() {
        platforms.add(new Platform(0, 550, 400, 20));

        platforms.add(new Platform(150, 450, 100, 20));
        platforms.add(new Platform(300, 350, 100, 20));
        platforms.add(new Platform(450, 250, 100, 20));
        platforms.add(new Platform(600, 150, 100, 20));

        keys.add(new Key(650, 100));

        platforms.add(new Platform(700, 350, 100, 20));
        door = new Door(730, 290);

        playerStartX = 50;
        playerStartY = 500;
    }

    private void createLevel3() {
        platforms.add(new Platform(0, 550, 150, 20));
        platforms.add(new Platform(250, 550, 150, 20));
        platforms.add(new Platform(500, 550, 150, 20));
        platforms.add(new Platform(700, 550, 100, 20));

        platforms.add(new Platform(150, 500, 130, 20));
        platforms.add(new Platform(360, 400, 130, 20));
        platforms.add(new Platform(600, 300, 130, 20));

        keys.add(new Key(420, 300));

        door = new Door(730, 490);

        playerStartX = 50;
        playerStartY = 500;
    }

    private void createLevel4() {
        platforms.add(new Platform(0, 550, 800, 20));
        platforms.add(new Platform(50, 150, 100, 20));
        platforms.add(new Platform(200, 200, 100, 20));
        platforms.add(new Platform(350, 250, 100, 20));
        platforms.add(new Platform(500, 300, 90, 20));
        platforms.add(new Platform(670, 370, 100, 20));

        platforms.add(new Platform(500, 450, 110, 20));

        keys.add(new Key(90, 100));

        door = new Door(680, 310);

        playerStartX = 50;
        playerStartY = 500;
    }

    private void createLevel5() {
        platforms.add(new Platform(0, 550, 200, 20));
        platforms.add(new Platform(100, 500, 80, 20));
        platforms.add(new Platform(200, 400, 80, 20));
        platforms.add(new Platform(300, 300, 80, 20));
        platforms.add(new Platform(200, 200, 80, 20));
        platforms.add(new Platform(300, 100, 80, 20));
        platforms.add(new Platform(500, 200, 150, 20));

        keys.add(new Key(320, 50));

        door = new Door(580, 140);

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