package entity;

import main.GamePanel;
import math.Vec2;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;

import java.awt.*;
import java.awt.geom.Area;

public class InvisibleWall extends Entity {
    GamePanel gamePanel;
    boolean drawHitbox;

    public InvisibleWall(GamePanel gamePanel, Vec2 worldPosition, int width, int height,boolean drawHitbox) {
        super(
                worldPosition,
                new Hitbox(
                        0,
                        0,
                        width,
                        height,
                        HitboxState.ACTIVE,
                        HitboxType.NONE,
                        Color.red,
                        Color.black
                )
        );

        this.gamePanel = gamePanel;
        this.drawHitbox = drawHitbox;

        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY + hitbox.height);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY + hitbox.height);
    }

    public void draw(Graphics2D g2) {
        Vec2 screenWorldPosition = new Vec2(
                gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt(),
                gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt()
        );

        Polygon screenPoly = new Polygon();
                screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt());
                screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth,screenWorldPosition.getYInt());
                screenPoly.addPoint(screenWorldPosition.getXInt() + gamePanel.screenWidth, screenWorldPosition.getYInt() + gamePanel.screenHeight);
                screenPoly.addPoint(screenWorldPosition.getXInt(), screenWorldPosition.getYInt()+ gamePanel.screenHeight);

        Polygon visualEntityPoly = this.hitbox.getPolygonAt(worldPosition);
        // Convert to Area for collision check
        Area screenArea = new Area(screenPoly);
        Area visualEntityArea = new Area(visualEntityPoly);

        // Check intersection
        screenArea.intersect(visualEntityArea);
        if (!screenArea.isEmpty()) {// Collision detected
            drawHitbox(g2);
        }
    }

    public void drawHitbox(Graphics2D g2) {
        if (drawHitbox) {

            Vec2 screenPosition = new Vec2(0,0);
            screenPosition.set(
                    this.worldPosition.getXInt() - gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt(),
                    this.worldPosition.getYInt() - gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
            );

            // Get the transformed polygon for drawing
            Polygon hitboxPolygon = hitbox.getTransformedPolygon(screenPosition);

            // Set the opacity and color for filling
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% opacity
            g2.setColor(hitbox.insideHitBoxColor);
            g2.fill(hitboxPolygon);  // Draw filled polygon

            // Reset opacity and set the color for the outline
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // 100% opacity
            g2.setColor(hitbox.outsideHitBoxColor);
            g2.draw(hitboxPolygon);  // Draw polygon outline
        }
    }

    @Override
    public void update() {

    }
}
