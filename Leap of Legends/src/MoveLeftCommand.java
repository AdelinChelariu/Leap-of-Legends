public class MoveLeftCommand implements Command {
    private final Player player;

    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveLeft();
    }

    @Override
    public void undo() {
        player.stop();
    }
}
