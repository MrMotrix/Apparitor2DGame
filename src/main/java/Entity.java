

public class Entity {
    protected Vec2 position;
    protected int speed;
    protected String direction;
    protected int spriteCounter;
    protected int spriteNum;

    public Entity() {
        position = new Vec2(0, 0);
        direction = "down";
        spriteCounter = 0;
        spriteNum = 1;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String dir) {
        direction = dir;
    }
}
