package objects;

import entity.VisualEntity;
import main.GamePanel;
import math.Vec2;
import temp.Hitbox;

import java.awt.*;

public abstract class SuperObject extends VisualEntity {

    String name;

    public SuperObject(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox,String name) {
        super(worldPosition,screenPosition,hitbox,gamePanel,currentSpriteKey,drawHitbox);
        this.name = name;
    }

    public abstract void onPickUp();

}
