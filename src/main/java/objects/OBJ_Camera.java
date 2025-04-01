package objects;

import main.GamePanel;
import math.Vec2;
import sprite.Sprite;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import tools.SpriteLibrary;

import java.awt.*;

public class OBJ_Camera extends SuperObject {

    private double angle;
    private double viewDistance;
    private double angleOffset;
    private double angleRotationPerFrame;
    private double width;
    private double angleShift=0;
    public int detectCounter = 0;
    private int direction = 1;

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
                        0,
                        0,
                        0,
                        0,
                        HitboxState.DISABLED,
                        HitboxType.SOLID
                ),
                gamePanel,
                "camera",
                true,
                false,
                "camera"
        );

        this.viewDistance = viewDistance;
        this.angle = Math.toRadians(initialAngleDegree);
        this.angleOffset = Math.toRadians(angleRangeDegree);
        this.angleRotationPerFrame = Math.toRadians(angleDegreeRotationPerFrame);
        this.width = width;
        super.hitbox.getBounds().addPoint(0,0);
        super.hitbox.getBounds().addPoint((int)(gamePanel.tileSize*viewDistance), (int)(width/2));
        super.hitbox.getBounds().addPoint((int)(gamePanel.tileSize*viewDistance), (int)(-width/2));


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
    }


    @Override
    public void onPickUp(){
        if(gamePanel.player.hitbox.getType() == HitboxType.NONE || detectCounter == 180) {
            gamePanel.player.hitbox.setType(HitboxType.HURTBOX);
            gamePanel.player.healthPoints--;
            detectCounter = 0;
        }
        detectCounter++;
        System.out.println(gamePanel.player.healthPoints);
    }
}
