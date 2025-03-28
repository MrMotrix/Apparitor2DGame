package objects;

import entity.VisualEntity;
import main.GamePanel;
import math.Vec2;
import temp.Hitbox;

import java.awt.*;

public abstract class SuperObject extends VisualEntity {

    String name;

    public SuperObject(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,String name) {
        super(worldPosition,screenPosition,hitbox,gamePanel,currentSpriteKey);
        this.name = name;
    }

    public abstract void onPickUp();

    @Override
    public void update() {
        super.update();
        screenPosition.set(
                this.worldPosition.getXInt() - gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt(),
                this.worldPosition.getYInt() - gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
        );
    }

    @Override
    public void draw(Graphics2D g2){
        if(     this.worldPosition.getXInt() + gamePanel.tileSize > gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt() &&
                this.worldPosition.getXInt() - gamePanel.tileSize < gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt() &&
                this.worldPosition.getYInt() + gamePanel.tileSize > gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt() &&
                this.worldPosition.getYInt() - gamePanel.tileSize < gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
        ){
            super.draw(g2);
        }
    }


}
