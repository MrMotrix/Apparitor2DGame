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

public class OBJ_Doormats extends SuperObject {

    private long playerTime = -2;
    private Vec2 worldPositionTeleportation;

    public OBJ_Doormats(GamePanel gamePanel,Vec2 worldPosition,Vec2 worldPositionTeleportation) {
        super(
                    worldPosition,
                    new Vec2(0,0),
                    new Hitbox(
                            0,
                            0,
                            64,
                            64,
                            HitboxState.DISABLED,
                            HitboxType.TRIGGER,
                            Color.BLUE,
                            Color.RED
                    ),
                    gamePanel,
                    "key",
                    false,
                    false,
                    "key"
            );

            hitbox.getBounds().addPoint(0,0);
            hitbox.getBounds().addPoint(gamePanel.tileSize,0);
            hitbox.getBounds().addPoint(gamePanel.tileSize,gamePanel.tileSize);
            hitbox.getBounds().addPoint(0,gamePanel.tileSize);

            this.worldPositionTeleportation = worldPositionTeleportation;

            addSprite(
                    name,
                    new Sprite(
                            SpriteLibrary.getInstance("").getSprite("world",super.name)
                    )
            );
        }
    @Override
    public void onPickUp() {
        if (!gamePanel.player.onTeleportation) {
            playerTime = System.currentTimeMillis();
        }

        gamePanel.player.onTeleportation = true;

        long now = System.currentTimeMillis();
        if (now - playerTime >= 1000) {
            gamePanel.player.setWorldPosition(new Vec2(worldPositionTeleportation.x, worldPositionTeleportation.y));
        }
    }
}
