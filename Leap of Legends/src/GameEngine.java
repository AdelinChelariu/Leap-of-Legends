import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GameEngine {
    // Singleton instance
    private static GameEngine instance;
    private Player player;
    private InputHandler inputHandler;
    private List<Platform> platforms;

    private Level currentLevel;
    private int currentLevelNumber = 1;

    private boolean gameOver = false;
    private static final Color GAME_OVER_BACKGROUND = new Color(0, 0, 0, 180);

    private boolean running;

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

    private JFrame gameWindow;
    private Canvas gameCanvas;
    private BufferStrategy bufferStrategy;

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
        if(currentLevelNumber==5)
            currentLevelNumber=0;
        currentLevelNumber++;
        platforms.clear();
        loadLevel(currentLevelNumber);
        player.setPosition(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());
    }

    private void createPlatforms() {
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

    public void startGame() {
        running = true;
        gameWindow.setVisible(true);

        while (running) {
            update();
            render();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        handleInput();
        if (!gameOver) {
            player.update(currentLevel.getPlatforms());

            if (player.isDead()) {
                gameOver = true;
            }

            createPlatforms();

            for (Key key : currentLevel.getKeys()) {
                if (key.checkCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
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


    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Platform platform : currentLevel.getPlatforms()) {
            platform.render(g);
        }

        g.drawString("Level" + currentLevelNumber, 10, 10);

        for (Key key : currentLevel.getKeys()) {
            key.render(g);
        }

        currentLevel.getDoor().render(g);

        player.render(g);
        if (gameOver) {
            renderGameOver(g);
        }

        g.dispose();
        bufferStrategy.show();
    }

    private void renderGameOver(Graphics2D g) {

        g.setColor(GAME_OVER_BACKGROUND);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        g.drawString(gameOverText, WIDTH/2 - textWidth/2, HEIGHT/2);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String restartText = "Press SPACE to restart";
        textWidth = fm.stringWidth(restartText);
        g.drawString(restartText, WIDTH/2 - textWidth/2, HEIGHT/2 + 50);
    }

    private void handleInput() {
        if (gameOver) {
            if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
                restartLevel();
            }
        }
    }

    private void restartLevel() {
        gameOver = false;
        player.reset(currentLevel.getPlayerStartX(), currentLevel.getPlayerStartY());
        for (Key key : currentLevel.getKeys()) {
            key.reset();
        }
        currentLevel.getDoor().reset();
        gameCanvas.requestFocus();
    }

    static class Keyboard {
        private static final Set<Integer> pressedKeys = new HashSet<>();

        public static void setKeyPressed(int keyCode) {
            pressedKeys.add(keyCode);
        }

        public static void setKeyReleased(int keyCode) {
            pressedKeys.remove(keyCode);
        }

        public static boolean isKeyPressed(int keyCode) {
            return pressedKeys.contains(keyCode);
        }
    }
}