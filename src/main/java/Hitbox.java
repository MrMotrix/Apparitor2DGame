import java.awt.Rectangle;

public class Hitbox {
    private HitboxState state;
    private Rectangle bounds;

    public Hitbox(int x, int y, int width, int height, HitboxState state) {
        this.state = state;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void updatePosition(int x, int y) {
        bounds.setLocation(x, y);
    }

    public boolean checkCollision(Hitbox other) {
        // Collision only occurs if both hitboxes are ACTIVE or GHOST
        if ((this.state == HitboxState.ACTIVE || this.state == HitboxState.GHOST) &&
                (other.state == HitboxState.ACTIVE || other.state == HitboxState.GHOST)) {
            return this.bounds.intersects(other.bounds);
        }
        return false;
    }

    public HitboxState getState() {
        return state;
    }

    public void setState(HitboxState state) {
        this.state = state;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
