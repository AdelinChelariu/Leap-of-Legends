import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener {
    private Map<Integer, Command> keyPressedCommands;
    private Map<Integer, Command> keyReleasedCommands;
    private Player player;
    private boolean spacePressed = false;

    public InputHandler(Player player) {
        this.player = player;
        keyPressedCommands = new HashMap<>();
        keyReleasedCommands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        // Comenzi pentru apăsare taste
        keyPressedCommands.put(KeyEvent.VK_LEFT,
                new PlayerMovementCommand(player, PlayerMovementCommand.MovementType.LEFT));
        keyPressedCommands.put(KeyEvent.VK_RIGHT,
                new PlayerMovementCommand(player, PlayerMovementCommand.MovementType.RIGHT));
        keyPressedCommands.put(KeyEvent.VK_SPACE,
                new PlayerMovementCommand(player, PlayerMovementCommand.MovementType.JUMP));

        // Comenzi pentru eliberare taste
        keyReleasedCommands.put(KeyEvent.VK_LEFT,
                new PlayerMovementCommand(player, PlayerMovementCommand.MovementType.STOP));
        keyReleasedCommands.put(KeyEvent.VK_RIGHT,
                new PlayerMovementCommand(player, PlayerMovementCommand.MovementType.STOP));
    }

    @Override
    public void keyPressed(KeyEvent e) {

        GameEngine.Keyboard.setKeyPressed(e.getKeyCode());

        Command command = keyPressedCommands.get(e.getKeyCode());
        if (command != null) {
            command.execute();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        GameEngine.Keyboard.setKeyReleased(e.getKeyCode());

        Command command = keyReleasedCommands.get(e.getKeyCode());
        if (command != null) {
            command.execute();
        }
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}