package entity;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.List;
import main.Vec2;

public abstract class Entity {
    public Vec2 position;

    public Entity(Vec2 position) {
        this.position = position;
    }

    public abstract void update();
}
