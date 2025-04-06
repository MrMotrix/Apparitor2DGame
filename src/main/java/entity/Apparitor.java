package entity;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.*;
import tools.SpriteLibrary;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

public class Apparitor extends Character {

    public VisionCone detectionZone;
    public int detectCounter = 0;

    public Apparitor(GamePanel gamePanel, double viewDistance, double width, double initialAngleDegree,double angleRangeDegree,double angleDegreeRotationPerFrame ) {
        super(
                new Vec2(164,100),
                new Vec2(gamePanel.screenWidth/2 - (gamePanel.tileSize/2),gamePanel.screenHeight/2 - (gamePanel.tileSize/2)),
                new Hitbox(
                        13,
                        21,
                        32,
                        32,
                        HitboxState.ACTIVE,
                        HitboxType.NONE,
                        Color.GREEN,
                        Color.YELLOW
                ),
                gamePanel,
                "idle-down",
                true,
                6,
                false
        );
        super.direction = Direction.DOWN;
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY + hitbox.height);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY + hitbox.height);

        detectionZone = new VisionCone(
                0,
                0,
                (int)width,
                0,
                HitboxState.DISABLED,
                HitboxType.SOLID,
                Color.RED,
                Color.RED,
                initialAngleDegree,
                viewDistance,
                angleRangeDegree,
                angleDegreeRotationPerFrame,
                super.gamePanel,
                super.worldPosition
        );

        loadSprites();
    }

    public void onCollision() {
        if(gamePanel.player.hitbox.getType() == HitboxType.NONE || detectCounter == 180) {
            gamePanel.player.hitbox.setType(HitboxType.HURTBOX);
            gamePanel.player.healthPoints--;
            detectCounter = 0;
        }
        detectCounter++;
        System.out.println("HP = "+gamePanel.player.healthPoints);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        Polygon detectionZonePolygon = detectionZone.getTransformedPolygon(screenPosition);

        // Set the opacity and color for filling
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% opacity
        g2.setColor(detectionZone.insideHitBoxColor);
        g2.fill(detectionZonePolygon);  // Draw filled polygon

        // Reset opacity and set the color for the outline
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // 100% opacity
        g2.setColor(detectionZone.outsideHitBoxColor);
        g2.draw(detectionZonePolygon);  // Draw polygon outline
    }

    @Override
    public void update() {
        super.update();
        detectionZone.update(worldPosition.x, worldPosition.y);
    }

    private void loadSprites(){
        String basePath = Paths.get("src/main/resources").toAbsolutePath().toString()+"/";
        Sprite frontSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-3"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-4"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-5")
                ),
                10
        );

        Sprite backSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-3"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-4"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-5")
                ),
                10
        );
        Sprite leftSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-3"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-4"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-5")
                ),
                10
        );
        Sprite rightSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-3"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-4"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-5")
                ),
                10
        );

        addSprite("down", frontSprite);
        addSprite("up", backSprite);
        addSprite("left",leftSprite);
        addSprite("right",rightSprite);
        addSprite("idle-up", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-0")),
                        10
                )
        );

        addSprite("idle-down", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-0")),
                        10
                )
        );

        addSprite("idle-left", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-left-frame-5")),
                        10
                )
        );

        addSprite("idle-right", new Sprite(
                        List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-right-frame-5")),
                        10
                )
        );
    }
}
