package entity;

import main.GamePanel;
import sprite.Sprite;
import temp.Drawable;
import math.Vec2;
import temp.Hitbox;

import java.awt.*;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Map;

public class VisualEntity extends Entity implements Drawable {

    public Map<String, Sprite> sprites;
    public String currentSpriteKey = null;
    public Vec2 screenPosition;
    public boolean drawHitbox;
    //public Map<String,List<temp.Hitbox>> hitboxes;
    public GamePanel gamePanel;

    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    public VisualEntity(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox) {
        super(worldPosition);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
        this.currentSpriteKey = currentSpriteKey;
        this.screenPosition = screenPosition;
        this.hitbox = hitbox;
        this.drawHitbox = drawHitbox;
    }
    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, Map<String, Sprite> sprites, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = sprites;
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, Map<String, Sprite> sprites, String currentSpriteKey, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = sprites;
        setCurrentSpriteKey(currentSpriteKey);
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    public void addSprite(String key, Sprite sprite) {
        sprites.put(key,sprite);
    }

    public Sprite getSprite(String key) {
        return sprites.getOrDefault(key,null);
    }

    public void setCurrentSpriteKey(String key) {
        if(sprites.containsKey(key)) {
            this.currentSpriteKey = key;
        }else{
            System.out.println("No such key("+key+"), cannot set the current sprite key");
        }
    }

    public Sprite getCurrentSprite() {
        return sprites.get(currentSpriteKey);
    }

    @Override
    public void update(){
        if(currentSpriteKey != null) {
            sprites.get(currentSpriteKey).update();
        }
        screenPosition.set(
                this.worldPosition.getXInt() - gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt(),
                this.worldPosition.getYInt() - gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
        );
    }

    @Override
    public void draw(Graphics2D g2) {
        if(     this.worldPosition.getXInt() + gamePanel.tileSize > gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt() &&
                this.worldPosition.getXInt() - gamePanel.tileSize < gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt() &&
                this.worldPosition.getYInt() + gamePanel.tileSize > gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt() &&
                this.worldPosition.getYInt() - gamePanel.tileSize < gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
        ) {
            if (currentSpriteKey != null) {
                g2.drawImage(
                        sprites.get(currentSpriteKey).getSprite(),
                        screenPosition.getXInt(),
                        screenPosition.getYInt(),
                        gamePanel.tileSize,
                        gamePanel.tileSize,
                        null
                );
            }
        }

        /*boolean shouldDraw = false;
        if(drawHitbox) {
            for(int i =0;i<this.hitbox.getBounds().xpoints.length && !shouldDraw; i++){
                if(this.worldPosition.getXInt() + this.hitbox.getBounds().xpoints[i] > gamePanel.player.worldPosition.getXInt() - gamePanel.player.screenPosition.getXInt() &&
                        this.worldPosition.getXInt() + this.hitbox.getBounds().xpoints[i]- gamePanel.tileSize < gamePanel.player.worldPosition.getXInt() + gamePanel.player.screenPosition.getXInt() &&
                        this.worldPosition.getYInt() + this.hitbox.getBounds().ypoints[i]+ gamePanel.tileSize > gamePanel.player.worldPosition.getYInt() - gamePanel.player.screenPosition.getYInt() &&
                        this.worldPosition.getYInt() + this.hitbox.getBounds().ypoints[i]- gamePanel.tileSize < gamePanel.player.worldPosition.getYInt() + gamePanel.player.screenPosition.getYInt()
                ){
                    shouldDraw = true;
                }
            }
        }

        if(shouldDraw) {
            drawHitbox(g2);
        }*/
        if(drawHitbox) {

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
    }

    public void drawHitbox(Graphics2D g2) {
        if (drawHitbox) {
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
}
