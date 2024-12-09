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

    // Constantele pentru mișcare
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

    // Metodele de mișcare
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

    // Metoda de actualizare a poziției
    public void update(List<Platform> platforms) {
        // Aplicăm gravitația
        velocityY += GRAVITY;

        if (isHitEffect) {
            hitEffectDuration--;
            if (hitEffectDuration <= 0) {
                isHitEffect = false;
                currentColor = NORMAL_COLOR;
            }
        }

        // Calculăm următoarea poziție pe X înainte de a o aplica
        float nextX = x + velocityX;

        // Verificăm coliziunile laterale
        boolean canMoveHorizontally = true;

        for (Platform platform : GameEngine.getInstance().getPlatforms()) {
            // Verificăm coliziunea în funcție de direcția de mișcare
            if (velocityX > 0) {  // Ne mișcăm spre dreapta
                if (platform.isCollidingLeft(nextX, y, WIDTH, HEIGHT)) {
                    canMoveHorizontally = false;
                    x = platform.getX() - WIDTH - 1;  // Plasăm jucătorul lângă platformă
                    break;
                }
            } else if (velocityX < 0) {  // Ne mișcăm spre stânga
                if (platform.isCollidingRight(nextX, y, WIDTH, HEIGHT)) {
                    canMoveHorizontally = false;
                    x = platform.getX() + platform.getWidth() + 1;  // Plasăm jucătorul lângă platformă
                    break;
                }
            }
        }

        // Aplicăm mișcarea pe X doar dacă nu există coliziuni
        if (canMoveHorizontally) {
            x = nextX;
        } else {
            velocityX = 0;  // Oprim mișcarea când lovim o platformă
        }

        // Gestionăm mișcarea pe axa Y
        float nextY = y + velocityY;
        boolean onPlatform = false;

        if(velocityY > 0){
            isFalling = true;
        }

        // Verificăm coliziunile cu toate platformele
        for (Platform platform : platforms) {
            // Verificăm dacă jucătorul va ateriza pe platformă
            if (platform.isOnTop(nextX, nextY, WIDTH, HEIGHT, velocityY)) {
                y = platform.getY() - HEIGHT - 1; // Așezăm jucătorul pe platformă
                velocityY = 0;
                isJumping = false;
                onPlatform = true;
                isFalling = false;
                canJump = true;
                break;
            }

            if(platform.isCollidingUnder(nextX, nextY, WIDTH, velocityY)) {
                y = platform.getY() + platform.getHeight() + 1;
                // Activăm efectul de lovitură
                triggerHitEffect();
                // Adăugăm un mic "bounce" pentru feedback vizual
                velocityY = 2.0f; // Un mic impuls în jos
                //velocityY = 0;
                break;
            }
        }

        // Dacă nu suntem pe nicio platformă, aplicăm mișcarea pe axa Y
        if (!onPlatform) {
            y = nextY;
        }

        // Limităm mișcarea la marginile ecranului (opțional)
        x = Math.max(0, Math.min(x, GameEngine.getInstance().getWIDTH() - WIDTH));
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