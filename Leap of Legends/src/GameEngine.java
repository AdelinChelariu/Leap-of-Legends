import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;


public class GameEngine {
    // Singleton instance
    private static GameEngine instance;
    private Player player;
    private InputHandler inputHandler;
    private List<Platform> platforms;

    private Level currentLevel;
    private int currentLevelNumber = 1;

    // Starea principală a jocului
    private boolean running;

    // Dimensiunile ferestrei de joc
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public int getWIDTH(){
        return WIDTH;
    }
    public int getHEIGHT(){
        return HEIGHT;
    }

    public List<Platform> getPlatforms(){
        return platforms;
    }

    // Componente principale ale jocului
    private JFrame gameWindow;
    private Canvas gameCanvas;
    private BufferStrategy bufferStrategy;

    // Constructor privat pentru Singleton
    private GameEngine() {
        initializeWindow();
        initializeGame();
    }

    private void initializeGame() {
        loadLevel(currentLevelNumber);
        player = new Player(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());
        inputHandler = new InputHandler(player);
        gameCanvas.addKeyListener(inputHandler);
        gameCanvas.setFocusable(true);
        gameCanvas.requestFocus();
        platforms = new ArrayList<>();
    }

    private void loadLevel(int levelNumber) {
        currentLevel = new Level(levelNumber);
    }

    private void nextLevel() {
        currentLevelNumber++;
        platforms.clear();
        loadLevel(currentLevelNumber);
        player.setPosition(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());
    }

    private void createPlatforms() {
//        // Adăugăm câteva platforme pentru test
//        platforms.add(new Platform(100, 400, 200, 20));   // Platformă orizontală
//        platforms.add(new Platform(100, 300, 20, 100));   // Platformă verticală
//        platforms.add(new Platform(400, 300, 200, 20));   // Altă platformă orizontală
//        platforms.add(new Platform(580, 200, 20, 100));   // Altă platformă verticală
//        platforms.add(new Platform(0, HEIGHT - 20, WIDTH, 20));  // Podeaua

        for (Platform platform : currentLevel.getPlatforms()) {
            platforms.add(platform);
        }
    }

    // Metodă pentru obținerea instanței
    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    // Inițializarea ferestrei de joc
    private void initializeWindow() {
        gameWindow = new JFrame("Leap of Legends");
        gameWindow.setSize(WIDTH, HEIGHT);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        gameCanvas = new Canvas();
        gameCanvas.setSize(WIDTH, HEIGHT);
        gameWindow.add(gameCanvas);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);

        gameCanvas.createBufferStrategy(3);
        bufferStrategy = gameCanvas.getBufferStrategy();
    }

    // Game loop principal
    public void startGame() {
        running = true;
        gameWindow.setVisible(true);

        // Game loop
        while (running) {
            update();
            render();

            try {
                Thread.sleep(16); // Aproximativ 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // Aici vom adăuga ulterior toată logica jocului
        createPlatforms();
        player.update(platforms);
        for (Key key : currentLevel.getKeys()) {
            if (key.checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                // Când toate cheile sunt colectate, deschidem ușa
                boolean allKeysCollected = currentLevel.getKeys().stream()
                        .allMatch(Key::isCollected);
                if (allKeysCollected) {
                    currentLevel.getDoor().unlock();
                }
            }
        }
        if (currentLevel.getDoor().checkCollision(player.getX(), player.getY(),
                player.getWidth(), player.getHeight())) {
            nextLevel();
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        // Curăță ecranul
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Desenează platformele
        for (Platform platform : currentLevel.getPlatforms()) {
            platform.render(g);
        }

        // Desenează cheile
        for (Key key : currentLevel.getKeys()) {
            key.render(g);
        }
        // Desenează ușa
        currentLevel.getDoor().render(g);
        // Desenează jucătorul
        player.render(g);
        // - Desenare inamici
        // etc.

        g.dispose();
        bufferStrategy.show();
    }
}