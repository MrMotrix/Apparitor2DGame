package temp;

import java.awt.Rectangle;

public class Hitbox {
    private HitboxState state;
    private HitboxType type;
    private Rectangle bounds;

    public Hitbox(int x, int y, int width, int height, HitboxState state,HitboxType type) {
        this.state = state;
        this.type = type;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void updatePosition(int x, int y) {
        bounds.setLocation(x, y);
    }

    public boolean checkCollision(Hitbox other) {

        if(this.state == HitboxState.ACTIVE && other.state == HitboxState.ACTIVE){
            return this.bounds.intersects(other.bounds);
        }
        return false;
    }

    public boolean bothSolid(Hitbox other) {
       return this.isSolid() && other.isSolid();
    }

    public boolean isSolid() {
        return this.state == HitboxState.ACTIVE && this.type == HitboxType.SOLID ;
    }

    public HitboxState getState() {
        return state;
    }

    public void setState(HitboxState state) {
        this.state = state;
    }

    public HitboxType getType() {
         return type;
    }

    public void setType(HitboxType type) {
        this.type = type;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
