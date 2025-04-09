package main;

import entity.Apparitor;
import math.Vec2;
import objects.OBJ_Camera;
import objects.OBJ_Doormats;
import objects.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }


    //METHODE JUSTE POUR GENERER DES OBJETS A DES POS ALEATOIRE SUR LA MAP
    private int getRandom(int min, int max) {

        return (int) (Math.random() * (max - min +1) + min);
    }

    public void setObjects() {
        gp.obj[0] = new OBJ_Key(
                gp,
                new Vec2(
                        9 * gp.tileSize,
                        5 * gp.tileSize
                )
        );

        gp.obj[1] = new OBJ_Key(
                gp,
                new Vec2(
                        9 * gp.tileSize,
                        5 * gp.tileSize
                )
        );

        gp.obj[2] = new OBJ_Key(
                gp,
                new Vec2(
                        9 * gp.tileSize,
                        5 * gp.tileSize
                )
        );

        //CAMERA

        gp.cameras[3] = new OBJ_Camera(
                gp,
                new Vec2(
                        10 * gp.tileSize,
                        5 * gp.tileSize
                ),
                6,
                128,
                0,
                180,
                0

        );

        gp.cameras[4] = new OBJ_Camera(
                gp,
                new Vec2(
                        16 * gp.tileSize,
                        5 * gp.tileSize
                ),
                9,
                90,
                90,
                90,
                0

        );

        gp.cameras[5] = new OBJ_Camera(
                gp,
                new Vec2(
                        10 * gp.tileSize,
                        20 * gp.tileSize
                ),
                2,
                1,
                0,
                0,
                0

        );
            gp.apparitors[0] = new Apparitor(
                    gp,
                    new Vec2(320,430),
                    2,
                    64,
                    0,
                    1,
                    2

        );

        gp.apparitors[1] = new Apparitor(
                gp,
                new Vec2(
                        22 * gp.tileSize,
                        5 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );

        gp.apparitors[2] = new Apparitor(
                gp,
                new Vec2(
                        33 * gp.tileSize,
                        5 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );

        gp.apparitors[3] = new Apparitor(
                gp,
                new Vec2(
                        8 * gp.tileSize,
                        26 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );

        gp.apparitors[4] = new Apparitor(
                gp,
                new Vec2(
                        22 * gp.tileSize,
                        26 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );

        gp.apparitors[5] = new Apparitor(
                gp,
                new Vec2(
                        33 * gp.tileSize,
                        26 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );
        gp.apparitors[5] = new Apparitor(
                gp,
                new Vec2(
                        20 * gp.tileSize,
                        15 * gp.tileSize
                ),
                2,
                64,
                0,
                1,
                2

        );



            gp.doormats[0] = new OBJ_Doormats(
                    gp,
                    new Vec2(
                            8 * gp.tileSize,
                            11 * gp.tileSize
                    ),
                    new Vec2(
                            8 * gp.tileSize,
                            14 * gp.tileSize
                    )
            );
        gp.doormats[1] = new OBJ_Doormats(
                gp,
                new Vec2(
                        20 * gp.tileSize,
                        11 * gp.tileSize
                ),
                new Vec2(
                        20 * gp.tileSize,
                        14 * gp.tileSize
                )
        );
        gp.doormats[2] = new OBJ_Doormats(
                gp,
                new Vec2(
                        8 * gp.tileSize,
                        15 * gp.tileSize
                ),
                new Vec2(
                        8 * gp.tileSize,
                        10 * gp.tileSize
                )
        );
        gp.doormats[3] = new OBJ_Doormats(
                gp,
                new Vec2(
                        6 * gp.tileSize,
                        6 * gp.tileSize
                ),
                new Vec2(
                        8 * gp.tileSize,
                        6 * gp.tileSize
                )
        );
        gp.doormats[4] = new OBJ_Doormats(
                gp,
                new Vec2(
                        20 * gp.tileSize,
                        15 * gp.tileSize
                ),
                new Vec2(
                        20 * gp.tileSize,
                        10 * gp.tileSize
                )
        );

        gp.doormats[5] = new OBJ_Doormats(
                gp,
                new Vec2(
                        32 * gp.tileSize,
                        11 * gp.tileSize
                ),
                new Vec2(
                        32 * gp.tileSize,
                        14 * gp.tileSize
                )
        );
        gp.doormats[6] = new OBJ_Doormats(
                gp,
                new Vec2(
                        32 * gp.tileSize,
                        15 * gp.tileSize
                ),
                new Vec2(
                        31 * gp.tileSize,
                        11 * gp.tileSize
                )
        );
        gp.doormats[7] = new OBJ_Doormats(
                gp,
                new Vec2(
                        36 * gp.tileSize,
                        20 * gp.tileSize
                ),
                new Vec2(
                        36 * gp.tileSize,
                        16 * gp.tileSize
                )
        );
        gp.doormats[8] = new OBJ_Doormats(
                gp,
                new Vec2(
                        36 * gp.tileSize,
                        17 * gp.tileSize
                ),
                new Vec2(
                        36 * gp.tileSize,
                        21 * gp.tileSize
                )
        );
        gp.doormats[8] = new OBJ_Doormats(
                gp,
                new Vec2(
                        18 * gp.tileSize,
                        17 * gp.tileSize
                ),
                new Vec2(
                        18 * gp.tileSize,
                        21 * gp.tileSize
                )
        );
        gp.doormats[9] = new OBJ_Doormats(
                gp,
                new Vec2(
                        18 * gp.tileSize,
                        20 * gp.tileSize
                ),
                new Vec2(
                        18 * gp.tileSize,
                        16 * gp.tileSize
                )
        );
        gp.doormats[10] = new OBJ_Doormats(
                gp,
                new Vec2(
                        12 * gp.tileSize,
                        17 * gp.tileSize
                ),
                new Vec2(
                        12 * gp.tileSize,
                        21 * gp.tileSize
                )
        );
        gp.doormats[11] = new OBJ_Doormats(
                gp,
                new Vec2(
                        12 * gp.tileSize,
                        20 * gp.tileSize
                ),
                new Vec2(
                        12 * gp.tileSize,
                        16 * gp.tileSize
                )
        );



    }
}

