package objects;

import temp.HitboxState;
import temp.HitboxType;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import tools.SpriteLibrary;
import entity.Player;


import java.awt.*;

public class OBJ_DoormatsUp extends SuperObject {

    private javax.swing.Timer presenceTimer;
    private boolean playerIsOn = false;

    public OBJ_DoormatsUp(Vec2 worldPosition, GamePanel gamePanel) {
        super(
                worldPosition,
                new Vec2(0,0),
                new Hitbox(
                        0,
                        0,
                        64,
                        64,
                        HitboxState.DISABLED,
                        HitboxType.DOOR,
                        Color.BLUE,
                        Color.RED
                ),
                gamePanel,
                "key",
                true,
                false,
                "key"
        );

        hitbox.getBounds().addPoint(0,0);
        hitbox.getBounds().addPoint(gamePanel.tileSize,0);
        hitbox.getBounds().addPoint(gamePanel.tileSize,gamePanel.tileSize);
        hitbox.getBounds().addPoint(0,gamePanel.tileSize);

        addSprite(
                name,
                new Sprite(
                        SpriteLibrary.getInstance("").getSprite("world",super.name)
                )
        );
    }

    public void onPickUp() {
        if (playerTime == -2) {
            playerTime = System.currentTimeMillis();
            return;
        }

        long now = System.currentTimeMillis();
        if (now - playerTime >= 1000) {
            gamePanel.player.setWorldPosition(new Vec2(this.worldPosition.getXInt(), this.worldPosition.getYInt() - 3 * gamePanel.tileSize));
            playerTime = -2;
        }
    }


}
