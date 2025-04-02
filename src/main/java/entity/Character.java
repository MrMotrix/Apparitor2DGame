package entity;

import main.GamePanel;
import math.Vec2;
import temp.Direction;
import temp.Hitbox;

public class Character extends VisualEntity {

    public boolean isStatic; // false = can move, true = fix
    public Vec2 velocity;
    public double defaultSpeed;
    public double speed;
    public boolean sprint;
    public Direction direction;
    public String dialogue;

    public Character(Vec2 worldPosition,Vec2 screenPosition, GamePanel gamePanel, int speed, boolean isStatic) {
        super(worldPosition,screenPosition, gamePanel);
        this.speed = speed;
        this.velocity = new Vec2(0, 0);
        this.isStatic = isStatic;
    }

    public Character(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox, int speed, boolean isStatic) {
        super(worldPosition,screenPosition ,hitbox,gamePanel,currentSpriteKey,drawHitbox);
        this.defaultSpeed = speed;
        this.speed = speed;
        this.velocity = new Vec2(0, 0);
        this.isStatic = isStatic;
        this.sprint = false;
    }

    public Character(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox,String dialogue, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox, int speed, boolean isStatic) {
        super(worldPosition,screenPosition ,hitbox,gamePanel,currentSpriteKey,drawHitbox);
        this.speed = speed;
        this.velocity = new Vec2(0, 0);
        this.isStatic = isStatic;
        this.dialogue = dialogue;
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
        worldPosition.add(velocity);
    }

    @Override
    public void update() {
        if (canMove()) {
            move();
        }
        super.update();
    }
}
