package entity;
import math.Vec2;
import temp.Hitbox;
public abstract class Entity {
    public Vec2 worldPosition;
    public Hitbox hitbox;

    public Entity(Vec2 position) {
        this.worldPosition = position;
    }
    public Entity(Vec2 position,Hitbox hitbox) {
        this.worldPosition = position;
        this.hitbox = hitbox;
    }

    public abstract void update();
}