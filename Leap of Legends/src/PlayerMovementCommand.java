public class PlayerMovementCommand implements Command {
    private Player player;
    private MovementType type;

    @Override
    public void execute() {
        switch(type) {
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case JUMP:
                player.jump();
                break;
            case STOP:
                player.stop();
                break;
        }
    }

    @Override
    public void undo() {
        if (type == MovementType.LEFT || type == MovementType.RIGHT) {
            player.stop();
        }
    }

    public PlayerMovementCommand(Player player, MovementType type) {
        this.player = player;
        this.type = type;
    }

    public enum MovementType {
        LEFT,
        RIGHT,
        JUMP,
        STOP
    }
}
