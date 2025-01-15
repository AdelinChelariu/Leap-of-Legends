import java.awt.*;

public class Platform {
    private int x, y;
    private int width, height;
    private Color color;

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
    }

    public boolean intersects(float playerX, float playerY, float playerWidth, float playerHeight) {
        return playerX < x + width &&
                playerX + playerWidth > x &&
                playerY < y + height &&
                playerY + playerHeight > y;
    }

    public boolean isOnTop(float playerX, float playerY, float playerWidth, float playerHeight, float playerVelocityY) {
        boolean isFalling = playerVelocityY > 0;
        boolean feetNearTop = Math.abs((playerY + playerHeight) - y) < 10;
        boolean withinXBounds = playerX + playerWidth > x && playerX < x + width;

        return isFalling && feetNearTop && withinXBounds;
    }

    public boolean isCollidingLeft(float playerX, float playerY, float playerWidth, float playerHeight) {
        boolean withinYBounds = playerY + playerHeight > y && playerY < y + height;
        boolean touchingLeft = Math.abs((playerX + playerWidth) - x) < 2;
        return withinYBounds && touchingLeft;
    }

    public boolean isCollidingRight(float playerX, float playerY, float playerWidth, float playerHeight) {
        boolean withinYBounds = playerY + playerHeight > y && playerY < y + height;
        boolean touchingRight = Math.abs(playerX - (x + width)) < 2;
        return withinYBounds && touchingRight;
    }

    public boolean isCollidingUnder(float playerX, float playerY, float playerWidth, float playerVelocityY) {
        boolean isJumping = playerVelocityY < 0;
        boolean withinXBounds = playerX + playerWidth > x && playerX < x + width;
        boolean headNearTop = Math.abs(playerY - (y + height)) < 5;
        return withinXBounds && headNearTop && isJumping;
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);

        g.setColor(Color.RED);
        g.drawRect((int)x, (int)y, (int)width, (int)height);
    }

    // Getteri pentru poziție și dimensiuni
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}