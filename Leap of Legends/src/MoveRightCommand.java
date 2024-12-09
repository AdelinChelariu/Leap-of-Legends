public class MoveRightCommand implements Command {
    private final Player player;

    public MoveRightCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveRight();
    }

    @Override
    public void undo() {
        player.stop();
    }
}
