package objects;

import entity.VisualEntity;
import main.GamePanel;
import math.Vec2;
import temp.Hitbox;
import sprite.Sprite;

import java.awt.*;

public abstract class SuperObject extends VisualEntity {

    public String name;
    boolean peakable;
    public SuperObject(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox,boolean peakable,String name) {
        super(worldPosition,screenPosition,hitbox,gamePanel,currentSpriteKey,drawHitbox);
        this.name = name;
        this.peakable = peakable;
        screenPosition.set(
                this.worldPosition.getXInt() - gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt(),
                this.worldPosition.getYInt() - gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
        );
    }

    public abstract void onPickUp();

    public boolean isPeakable() {
        return peakable;
    }

    public String getName() {
        return name;
    }
    public Sprite getSprite() {
        return getCurrentSprite();
    }

}
