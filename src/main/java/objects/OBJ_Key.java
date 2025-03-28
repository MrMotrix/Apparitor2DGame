package objects;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import tools.SpriteLibrary;

public class OBJ_Key extends SuperObject{

    public OBJ_Key(GamePanel gamePanel,Vec2 worldPosition) {
        super(
                worldPosition,
                new Vec2(0,0),
                new Hitbox(
                        0,
                        0,
                        64,
                        64,
                        HitboxState.DISABLED,
                        HitboxType.SOLID
                ),
                gamePanel,
                "key",
                "key"
        );

        addSprite(
                name,
                new Sprite(
                        SpriteLibrary.getInstance("").getSprite("world",super.name)
                )
        );
    }

    @Override
    public void onPickUp(){
        gamePanel.player.nbKey++;
    }

}
