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
import java.nio.file.Paths;
import java.util.List;

public class OBJ_Doormats extends SuperObject {

    private long playerTime = -2;
    public Vec2 tp;

    public OBJ_Doormats(Vec2 worldPosition, GamePanel gamePanel, Vec2 tp) {
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
                    "Doormats",
                    true,
                    false,
                    "Doormats"
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
            this.tp = tp;
        }


    public void onPickUp(Vec2 tp) {
        if (playerTime == -2) {
            playerTime = System.currentTimeMillis();
            return;
        }

        long now = System.currentTimeMillis();
        if (now - playerTime >= 1000) {
            gamePanel.player.setWorldPosition(tp);
            playerTime = -2;
        }
    }

    private void loadSprites(){
        String basePath = Paths.get("src/main/resources").toAbsolutePath().toString()+"/";
        Sprite frontSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("world","Doormats-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("world","Doormats-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("world","Doormats-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("world","Doormats-3")

                ),
                10
        );
        addSprite("Doormats", frontSprite);
    }
}
