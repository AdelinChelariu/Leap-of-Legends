import java.awt.*;

public class Key {
    private float x, y;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private boolean isCollected = false;

    public Key(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        if (!isCollected) {
            boolean collision = playerX < x + WIDTH &&
                    playerX + playerWidth > x &&
                    playerY < y + HEIGHT &&
                    playerY + playerHeight > y;
            if (collision) {
                isCollected = true;
            }
            return collision;
        }
        return false;
    }

    public void render(Graphics2D g) {
        if (!isCollected) {
            g.setColor(Color.YELLOW);
            // Desenăm o cheie simplă
            g.fillOval((int)x, (int)y, WIDTH, HEIGHT);
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void reset() {
        isCollected = false;
    }
}