public class Character extends VisualEntity {

    public boolean isStatic; // false = can move, true = fix
    public Vec2 velocity;
    public int speed;
    public Direction direction;

    public Character(Vec2 position,GamePanel gamePanel, int speed, boolean isStatic) {
        super(position, gamePanel);
        this.speed = speed;
        this.velocity = new Vec2(0, 0);
        this.isStatic = isStatic;
    }

    public Character(Vec2 position,GamePanel gamePanel,String currentSpriteKey, int speed, boolean isStatic) {
        super(position, gamePanel,currentSpriteKey);
        this.speed = speed;
        this.velocity = new Vec2(0, 0);
        this.isStatic = isStatic;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean canMove() {
        return !isStatic;
    }

    public void move() {
        velocity.normalize();
        velocity.scale(speed);
        position.add(velocity);
    }

    @Override
    public void update() {
        if (canMove()) {
            move();
        }
        super.update();
    }
}
