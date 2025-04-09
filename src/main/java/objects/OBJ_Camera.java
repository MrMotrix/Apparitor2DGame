package objects;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import temp.VisionCone;
import tools.SpriteLibrary;

import java.awt.*;
import java.awt.geom.Area;

public class OBJ_Camera extends SuperObject {

    public int detectCounter = 0;
    public VisionCone detectionZone;

    /***********************

     * angleDegreeRotationPerFrame = 0 => static camera
     * angleRangeDegree = 0 && angleDegreeRotationPerFrame > 0 => 360 camera
     * angleRangeDegree > 0 && angleDegreeRotationPerFrame > 0 => ping-pong camera

     ************************/

    public OBJ_Camera(GamePanel gamePanel, Vec2 worldPosition,double viewDistance,double width,double initialAngleDegree,double angleRangeDegree, double angleDegreeRotationPerFrame) {
        super(
                worldPosition,
                new Vec2(0,0),
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
                "camera",
                false,
                false,
                "camera"
        );

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

        addSprite(
                name,
                new Sprite(
                        SpriteLibrary.getInstance("").getSprite("world",super.name)
                )
        );
    }

    /*
    //HITBOX CARRE
    @Override
    public void update() {
        super.update();

        // Augmenter progressivement l'angle de rotation (ajuste la vitesse si nécessaire)
        angle += Math.toRadians(2); // 2 degrés par update, ajuste la vitesse si nécessaire

        // Calculer le centre de la rotation (centre de la tile)
        int centerX = (int) worldPosition.x + (gamePanel.tileSize / 2);
        int centerY = (int) worldPosition.y + (gamePanel.tileSize / 2);

        // Largeur et hauteur de la hitbox
        int hitboxWidth = hitbox.width;
        int hitboxHeight = hitbox.height;

        // Définir les coins du rectangle avant la rotation
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        // Coin supérieur gauche
        xPoints[0] = (int) worldPosition.x;
        yPoints[0] = (int) worldPosition.y;

        // Coin supérieur droit
        xPoints[1] = (int) worldPosition.x + hitboxWidth;
        yPoints[1] = (int) worldPosition.y;

        // Coin inférieur droit
        xPoints[2] = (int) worldPosition.x + hitboxWidth;
        yPoints[2] = (int) worldPosition.y + hitboxHeight;

        // Coin inférieur gauche
        xPoints[3] = (int) worldPosition.x;
        yPoints[3] = (int) worldPosition.y + hitboxHeight;

        // Appliquer la rotation des points autour du centre de la tile
        for (int i = 0; i < 4; i++) {
            // Décalage par rapport au centre (translation)
            int offsetX = xPoints[i] - centerX;
            int offsetY = yPoints[i] - centerY;

            // Appliquer la matrice de rotation (rotation autour du centre)
            int rotatedX = (int) (centerX + (offsetX * Math.cos(angle) - offsetY * Math.sin(angle)));
            int rotatedY = (int) (centerY + (offsetX * Math.sin(angle) + offsetY * Math.cos(angle)));

            // Mettre à jour les coordonnées après rotation
            xPoints[i] = rotatedX - worldPosition.getXInt();
            yPoints[i] = rotatedY - worldPosition.getYInt();
        }

        // Mettre à jour les points du polygone de la hitbox avec les nouvelles coordonnées après rotation
        hitbox.getBounds().xpoints = xPoints;
        hitbox.getBounds().ypoints = yPoints;
    }*/
/*
    public void update() {
        super.update();

        if(direction==1) {
            if((angleShift+angleRotationPerFrame)%angleOffset < angleShift){
                direction *= -1;
            }
            angleShift+=angleRotationPerFrame;
        }else{

            if((angleShift+angleRotationPerFrame) < 0){
                direction *= -1;
            }
            angleShift-=angleRotationPerFrame;
        }

        angle = angle + direction*angleRotationPerFrame; // Avancer sur le cercle

        double radius = gamePanel.tileSize/2;
        int centerX = (int) worldPosition.x + (gamePanel.tileSize / 2);
        int centerY = (int) worldPosition.y + (gamePanel.tileSize / 2);
        // Nouveau point de base en mouvement circulaire
        double x1 = centerX + radius * Math.cos(angle);
        double y1 = centerY + radius * Math.sin(angle);

        // Points du triangle avant rotation
        double[] point2 = {gamePanel.tileSize * viewDistance, width / 2};
        double[] point3 = {gamePanel.tileSize * viewDistance, -width / 2};

        // Appliquer la rotation au triangle autour du premier point
        double[] rotated2 = rotatePoint(point2[0], point2[1], angle);
        double[] rotated3 = rotatePoint(point3[0], point3[1], angle);

        // Positions finales des points
        double x2 = x1 + rotated2[0];
        double y2 = y1 + rotated2[1];
        double x3 = x1 + rotated3[0];
        double y3 = y1 + rotated3[1];

        // Mettre à jour la hitbox

        Polygon triangle = new Polygon(new int[] { (int)x1 - worldPosition.getXInt(), (int)x2- worldPosition.getXInt(), (int)x3- worldPosition.getXInt() }, new int[] { (int)y1- worldPosition.getYInt(), (int)y2- worldPosition.getYInt(), (int)y3- worldPosition.getYInt() }, 3);

        // Mise à jour de la hitbox du triangle
        hitbox.setBounds(triangle);
    }

    // Fonction de rotation d'un point autour de (0,0)
    private double[] rotatePoint(double x, double y, double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new double[]{newX, newY};
    }*/

    public void update() {
        super.update();
        detectionZone.update(worldPosition.x,worldPosition.y);
    }


    public void onCollision(){
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

        Vec2 screenWorldPosition = new Vec2(
                gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt(),
                gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt()
        );
        Polygon detectionZonePolygonWorld = detectionZone.getTransformedPolygon(worldPosition);
        Polygon screenPoly = new Polygon();
        screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt());
        screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth,screenWorldPosition.getYInt());
        screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth, screenWorldPosition.getYInt() + gamePanel.screenHeight);
        screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt()+ gamePanel.screenHeight);

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

    @Override
    public void onPickUp(){
        // Pour l'instant sert a tchi
    }
}
