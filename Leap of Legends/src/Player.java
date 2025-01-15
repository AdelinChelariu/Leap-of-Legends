import java.awt.*;
import java.util.List;


public class Player {
    private float x, y;        // Poziția jucătorului
    private float velocityX, velocityY;  // Viteza pe ambele axe
    private boolean isJumping; // Starea de săritură
    private final float WIDTH = 30;
    private final float HEIGHT = 50;
    private boolean canJump = true;
    private boolean isFalling = false;
    private boolean isDead = false;

    // Constante pentru mișcare
    private static final float MOVE_SPEED = 5.0f;
    private static final float JUMP_FORCE = -15.0f;
    private static final float GRAVITY = 0.95f;

    // Variabile pentru efecte
    private boolean isHitEffect;
    private int hitEffectDuration;
    private Color currentColor;
    private static final int MAX_HIT_EFFECT_DURATION = 10; // Frames
    private static final Color NORMAL_COLOR = Color.RED;
    private static final Color HIT_COLOR = Color.WHITE;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
        this.currentColor = NORMAL_COLOR;
        this.isHitEffect = false;
        this.hitEffectDuration = 0;
    }

    public float getWidth() { return WIDTH; }
    public float getHeight() { return HEIGHT; }

    public void moveLeft() {
        velocityX = -MOVE_SPEED;
    }

    public void moveRight() {
        velocityX = MOVE_SPEED;
    }

    public void stop() {
        velocityX = 0;
    }

    public void jump() {
        if (!isJumping && canJump && !isFalling) {
            velocityY = JUMP_FORCE;
            isJumping = true;
            canJump = false;
        }
    }

    private void triggerHitEffect() {
        isHitEffect = true;
        hitEffectDuration = MAX_HIT_EFFECT_DURATION;
        currentColor = HIT_COLOR;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
    }

    public boolean isDead() {
        return isDead;
    }

    // Metoda de actualizare a poziției
    public void update(List<Platform> platforms) {
        velocityY += GRAVITY;

        if (y > GameEngine.getInstance().getHEIGHT()) {
            isDead = true;
        }

        if (isHitEffect) {
            hitEffectDuration--;
            if (hitEffectDuration <= 0) {
                isHitEffect = false;
                currentColor = NORMAL_COLOR;
            }
        }

        float nextX = x + velocityX;

        boolean canMoveHorizontally = true;

        for (Platform platform : GameEngine.getInstance().getPlatforms()) {
            if (velocityX > 0) {
                if (platform.isCollidingLeft(nextX, y, WIDTH, HEIGHT)) {
                    canMoveHorizontally = false;
                    x = platform.getX() - WIDTH - 1;
                    break;
                }
            } else if (velocityX < 0) {
                if (platform.isCollidingRight(nextX, y, WIDTH, HEIGHT)) {
                    canMoveHorizontally = false;
                    x = platform.getX() + platform.getWidth() + 1;
                    break;
                }
            }
        }

        if (canMoveHorizontally) {
            x = nextX;
        }

        float nextY = y + velocityY;
        boolean onPlatform = false;

        if(velocityY > 0){
            isFalling = true;
        }

        for (Platform platform : platforms) {
            if (platform.isOnTop(nextX, nextY, WIDTH, HEIGHT, velocityY)) {
                y = platform.getY() - HEIGHT - 1;
                velocityY = 0;
                isJumping = false;
                onPlatform = true;
                isFalling = false;
                canJump = true;
                break;
            }

            if(platform.isCollidingUnder(nextX, nextY, WIDTH, velocityY)) {
                y = platform.getY() + platform.getHeight() + 2;
                velocityY = 0;
                triggerHitEffect();
                break;
            }
        }

        if (!onPlatform) {
            y = nextY;
        }

        x = Math.max(0, Math.min(x, GameEngine.getInstance().getWIDTH() - WIDTH));
    }

    public void reset(float startX, float startY) {
        x = startX;
        y = startY;
        velocityX = 0;
        velocityY = 0;
        isDead = false;
        isJumping = false;
        canJump = true;
    }

    // Metode pentru randare și acces
    public void render(Graphics2D g) {
        g.setColor(currentColor);
        g.fillRect((int)x, (int)y, (int) WIDTH, (int) HEIGHT); // Desenăm jucătorul ca un dreptunghi

        if (isHitEffect) {
            // Desenăm un cerc de impact
            g.setColor(new Color(255, 255, 255, 128)); // Alb semi-transparent
            int effectRadius = 20;
            g.fillOval((int)(x + WIDTH/2 - effectRadius),
                    (int)(y + HEIGHT/2 - effectRadius),
                    effectRadius * 2,
                    effectRadius * 2);
        }

        // Desenăm liniile de coliziune pentru debugging
        g.setColor(Color.RED);
        g.drawRect((int)x, (int)y, (int)WIDTH, (int)HEIGHT);
    }

    public float getX() { return x; }
    public float getY() { return y; }
}