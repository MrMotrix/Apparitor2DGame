package main;

import math.Vec2;
import objects.OBJ_Camera;
import objects.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects(){
        gp.obj[0] = new OBJ_Key(
                gp,
                new Vec2(
                        10*gp.tileSize,
                        10*gp.tileSize
                )
        );

        gp.obj[1] = new OBJ_Key(
                gp,
                new Vec2(
                        16*gp.tileSize,
                        16*gp.tileSize
                )
        );

        gp.obj[2] = new OBJ_Key(
                gp,
                new Vec2(
                        5*gp.tileSize,
                        5*gp.tileSize
                )
        );

        //CAMERA

        gp.obj[3] = new OBJ_Camera(
                gp,
                new Vec2(
                        10*gp.tileSize,
                        5*gp.tileSize
                ),
                6,
                128,
                0,
                180,
                2

        );

        gp.obj[4] = new OBJ_Camera(
                gp,
                new Vec2(
                        16*gp.tileSize,
                        5*gp.tileSize
                ),
                9,
                90,
                90,
                90,
                2

        );

        gp.obj[5] = new OBJ_Camera(
                gp,
                new Vec2(
                        10*gp.tileSize,
                        20*gp.tileSize
                ),
                2,
                1,
                0,
                0,
                3

        );
    }
}
