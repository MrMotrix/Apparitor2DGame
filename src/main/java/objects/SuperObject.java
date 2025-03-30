package objects;

import entity.VisualEntity;
import main.GamePanel;
import math.Vec2;
import temp.Hitbox;

import java.awt.*;

public abstract class SuperObject extends VisualEntity {

    String name;
    boolean peakable;
    public SuperObject(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox,boolean peakable,String name) {
        super(worldPosition,screenPosition,hitbox,gamePanel,currentSpriteKey,drawHitbox);
        this.name = name;
        this.peakable = peakable;
    }

    public abstract void onPickUp();

    public boolean isPeakable() {
        return peakable;
    }
}
