package objects;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import tools.SpriteLibrary;

import java.awt.*;

public class OBJ_FinalItem extends SuperObject{

    public OBJ_FinalItem(GamePanel gamePanel,Vec2 worldPosition) {
        super(
                worldPosition,
                new Vec2(0,0),
                new Hitbox(
                        0,
                        0,
                        64,
                        64,
                        HitboxState.DISABLED,
                        HitboxType.SOLID,
                        Color.BLUE,
                        Color.RED
                ),
                gamePanel,
                "FinalItem",
                false,
                true,
                "FinalItem"
        );

        hitbox.getBounds().addPoint(0,0);
        hitbox.getBounds().addPoint(gamePanel.tileSize,0);
        hitbox.getBounds().addPoint(gamePanel.tileSize,gamePanel.tileSize);
        hitbox.getBounds().addPoint(0,gamePanel.tileSize);

        addSprite(
                name,
                new Sprite(
                        SpriteLibrary.getInstance("").getSprite("world","player-front-frame-0")
                )
        );
    }

    @Override
    public void onPickUp(){
        System.out.println("GG C'EST LA FIN DU JEU");
    }

}
