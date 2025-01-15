import java.awt.*;

public class Door {
    private float x, y;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 60;
    private boolean isLocked = true;

    public Door(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(float playerX, float playerY, float playerWidth, float playerHeight) {
        return !isLocked &&
                playerX < x + WIDTH &&
                playerX + playerWidth > x &&
                playerY < y + HEIGHT &&
                playerY + playerHeight > y;
    }

    public void unlock() {
        isLocked = false;
    }

    public void render(Graphics2D g) {
        if (isLocked) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.CYAN);
        }
        g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
    }

    public void reset() {
        isLocked = true;
    }
}