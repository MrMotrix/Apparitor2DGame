import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.List;

public abstract class Entity {
    public Vec2 position;

    public Entity(Vec2 position) {
        this.position = position;
    }

    public abstract void update();
}
