package entity;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.*;
import tools.SpriteLibrary;

import java.awt.*;
import java.awt.geom.Area;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Apparitor extends Character {

    public VisionCone detectionZone;
    private int detectCounter = 0;
    private long lastWalkTime;
    private long lastScanTime;
    private long randomWalkTime;
    private long scanTime;
    private boolean isScanning;
    private Random random;
    private Direction currentDirection;

    public Apparitor(GamePanel gamePanel,Vec2 worldStartPosition, double viewDistance, double width, double initialAngleDegree, double angleRangeDegree, double angleDegreeRotationPerFrame) {
        super(
                worldStartPosition,
                new Vec2(gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2), gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2)),
                new Hitbox(
                        13,
                        21,
                        32,
                        32,
                        HitboxState.DISABLED,
                        HitboxType.SOLID,
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
                (int) width,
                0,
                HitboxState.DISABLED,
                HitboxType.SOLID,
                new Color(140, 120, 50),
                new Color(20, 18, 10),
                initialAngleDegree,
                viewDistance,
                angleRangeDegree,
                angleDegreeRotationPerFrame,
                super.gamePanel,
                super.worldPosition
        );

        random = new Random();
        randomWalkTime = random.nextInt(3000) + 1000; // Temps de marche initial entre 1 et 4 secondes
        scanTime = random.nextInt(2000) + 1000; // Temps de scan initial entre 1 et 3 secondes
        isScanning = false;
        currentDirection = getRandomDirection();

        loadSprites();
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();

        // Si l'Apparitor est en train de scanner
        if (isScanning) {
            // Vérifie si le temps de scan est écoulé
            if (currentTime - lastScanTime >= scanTime) {
                // Si le temps de scan est écoulé, on recommence à marcher
                isScanning = false;
                randomWalkTime = random.nextInt(2000) + 1000; // Nouveau temps de marche
                currentDirection = getRandomDirection(); // Choisir une nouvelle direction
                lastWalkTime = currentTime; // Commence à compter le temps de marche
            }
            handleIdleState();
        } else {
            // Comportement de marche aléatoire
            if (currentTime - lastWalkTime >= randomWalkTime) {
                // Temps de marcher est écoulé, on arrête de marcher et on commence à scanner
                stopAndScan();
                lastScanTime = currentTime; // Commence à compter le temps de scan
                isScanning = true; // L'Apparitor commence à scanner
                lastWalkTime = currentTime; // Reprend le temps de marche
            }else{
                // Déplacement dans la direction choisie
                moveInDirection(currentDirection);
            }
        }

        // Mise à jour de la zone de détection
        detectionZone.update(worldPosition.x, worldPosition.y);

        this.hitbox.setState(HitboxState.DISABLED);

        gamePanel.cChecker.checkTile(this);

        if(gamePanel.cChecker.checkPlayerCollision(this))
            onCollision();

        if (this.hitbox.getState() == HitboxState.ACTIVE)
            velocity.set(0, 0);

        super.update();
    }

    private void setMovementInfo(Direction direction, String spriteKey, int vx, int vy) {
        super.setDirection(direction);
        super.setCurrentSpriteKey(spriteKey);
        velocity.set(vx, vy);
    }

    private void moveInDirection(Direction direction) {
        switch (direction) {
            case UP:
                setMovementInfo(Direction.UP,"up",0,-1);
                detectionZone.angle = Math.toRadians(270);
                break;
            case DOWN:
                setMovementInfo(Direction.DOWN,"down",0,1);
                detectionZone.angle = Math.toRadians(90);
                break;
            case LEFT:
                setMovementInfo(Direction.LEFT,"left",-1,0);
                detectionZone.angle = Math.toRadians(180);
                break;
            case RIGHT:
                setMovementInfo(Direction.RIGHT,"right",1,0);
                detectionZone.angle = Math.toRadians(0);
                break;
        }
    }

    private void handleIdleState() {
        switch (direction) {
            case Direction.UP -> super.setCurrentSpriteKey("idle-up");
            case Direction.DOWN -> super.setCurrentSpriteKey("idle-down");
            case Direction.LEFT -> super.setCurrentSpriteKey("idle-left");
            case Direction.RIGHT -> super.setCurrentSpriteKey("idle-right");
            //default -> super.setCurrentSpriteKey("idle");
        }
        super.setDirection(Direction.IDLE);
        velocity.set(0, 0);
    }

    private void stopAndScan() {
        // Stop the Apparitor by making it idle
        handleIdleState();

        // Randomly change the detection angle range
        double randomAngleRange = random.nextInt(90) + 30;  // Random range between 30 and 120 degrees
        detectionZone.angleOffset = (randomAngleRange);

        // Optionally, add some randomness to the direction of the scan (rotate the detection zone)
        double randomRotationAngle = random.nextInt(360);
        detectionZone.angle = (randomRotationAngle);
    }

    private Direction getRandomDirection() {
        // Get a random direction (UP, DOWN, LEFT, RIGHT)
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        Vec2 screenWorldPosition = new Vec2(
                gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt(),
                gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt()
        );
        Polygon detectionZonePolygonWorld = detectionZone.getTransformedPolygon(worldPosition);
        Polygon screenPoly = new Polygon();
        screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt());
        screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth, screenWorldPosition.getYInt());
        screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth, screenWorldPosition.getYInt() + gamePanel.screenHeight);
        screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt() + gamePanel.screenHeight);

        // Convert to Area for collision check
        Area screenArea = new Area(screenPoly);
        Area detectionZoneArea = new Area(detectionZonePolygonWorld);

        // Check intersection
        screenArea.intersect(detectionZoneArea);
        if (!screenArea.isEmpty()) {// Collision detected

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
    }

    public void onCollision() {
        if (gamePanel.player.hitbox.getType() == HitboxType.NONE || detectCounter == 180) {
            gamePanel.player.hitbox.setType(HitboxType.HURTBOX);
            gamePanel.player.healthPoints--;
            detectCounter = 0;
        }
        detectCounter++;
        System.out.println("HP = " + gamePanel.player.healthPoints);
    }

    private void loadSprites() {
        String basePath = Paths.get("src/main/resources").toAbsolutePath().toString() + "/";
        Sprite frontSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-front-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-front-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-front-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-front-frame-3")
                ),
                10
        );

        Sprite backSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-back-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-back-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-back-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-back-frame-3")
                ),
                10
        );
        Sprite leftSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-left-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-left-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-left-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-left-frame-3")
                ),
                10
        );
        Sprite rightSprite = new Sprite(
                java.util.List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-right-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-right-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-right-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-right-frame-3")
                ),
                10
        );

        addSprite("down", frontSprite);
        addSprite("up", backSprite);
        addSprite("left", leftSprite);
        addSprite("right", rightSprite);
        addSprite("idle-up", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-back-frame-0")
                        ),
                        10
                )
        );
        addSprite("idle-down", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-front-frame-0")
                        ),
                        10
                )
        );
        addSprite("idle-left", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-left-frame-2")
                        ),
                        10
                )
        );
        addSprite("idle-right", new Sprite(
                        java.util.List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters", "apparitor-right-frame-0")
                        ),
                        10
                )
        );
    }

}
