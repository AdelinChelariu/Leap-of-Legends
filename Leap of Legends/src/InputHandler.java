import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener {
    private Map<Integer, Command> keyPressedCommands;
    private Map<Integer, Command> keyReleasedCommands;
    private Player player;

    public InputHandler(Player player) {
        this.player = player;
        keyPressedCommands = new HashMap<>();
        keyReleasedCommands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        // Comenzi pentru apăsare taste
        keyPressedCommands.put(KeyEvent.VK_LEFT, new MoveLeftCommand(player));
        keyPressedCommands.put(KeyEvent.VK_RIGHT, new MoveRightCommand(player));
        keyPressedCommands.put(KeyEvent.VK_SPACE, new JumpCommand(player));

        // Comenzi pentru eliberare taste
        keyReleasedCommands.put(KeyEvent.VK_LEFT, new Command() {
            @Override
            public void execute() { player.stop(); }
            @Override
            public void undo() {}
        });
        keyReleasedCommands.put(KeyEvent.VK_RIGHT, new Command() {
            @Override
            public void execute() { player.stop(); }
            @Override
            public void undo() {}
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Command command = keyPressedCommands.get(e.getKeyCode());
        if (command != null) {
            command.execute();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Command command = keyReleasedCommands.get(e.getKeyCode());
        if (command != null) {
            command.execute();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nu folosim acest eveniment
    }
}