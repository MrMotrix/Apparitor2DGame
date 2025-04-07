package temp;

import main.GamePanel;
import math.Vec2;

import java.awt.*;

public class VisionCone extends Hitbox{

    public double angle;
    private double viewDistance;
    public double angleOffset;
    private double angleRotationPerFrame;
    private double width;
    private Vec2 worldPosition;
    private GamePanel gamePanel;
    private double angleShift=0;
    private int direction = 1;

    public VisionCone(int x, int y, int width, int height, HitboxState state, HitboxType type, Color insideHitBoxColor, Color outsideHitBoxColor, double initialAngleDegree, double viewDistance, double angleDegreeRange, double angleDegreeRotationPerFrame, GamePanel gamePanel,Vec2 wordlPosition) {

        super(x, y, width, height, state, type, insideHitBoxColor, outsideHitBoxColor);
        this.angle = Math.toRadians(initialAngleDegree);
        this.viewDistance = viewDistance;
        this.angleOffset = Math.toRadians(angleDegreeRange);
        this.angleRotationPerFrame = Math.toRadians(angleDegreeRotationPerFrame);
        this.width = width;
        this.worldPosition = wordlPosition;
        this.gamePanel = gamePanel;
        super.getBounds().addPoint(0,0);
        super.getBounds().addPoint((int)(gamePanel.tileSize*viewDistance), (int)(width/2));
        super.getBounds().addPoint((int)(gamePanel.tileSize*viewDistance), (int)(-width/2));
    }

    public void setWorldPosition(double x, double y) {
        worldPosition.x = x;
        worldPosition.y = y;
    }

    public void update(double x, double y) {

        setWorldPosition(x,y);

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
        setBounds(triangle);
    }

    // Fonction de rotation d'un point autour de (0,0)
    private double[] rotatePoint(double x, double y, double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new double[]{newX, newY};
    }
}
