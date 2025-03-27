package entity;
import java.awt.*;
import main.Vec2;
import main.GamePanel;
import sprite.Sprite;
import sprite.Drawable;

import java.util.HashMap;
import java.util.Map;

public class VisualEntity extends Entity implements Drawable {

    public Map<String, Sprite> sprites;
    public String currentSpriteKey = null;
    private GamePanel gamePanel;

    VisualEntity(Vec2 position, GamePanel gamePanel) {
        super(position);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
    }

    VisualEntity(Vec2 position, GamePanel gamePanel, String currentSpriteKey) {
        super(position);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
        this.currentSpriteKey = currentSpriteKey;
    }
    VisualEntity(Vec2 position, Map<String, Sprite> sprites, GamePanel gamePanel) {
        super(position);
        this.sprites = sprites;
        this.gamePanel = gamePanel;
    }

    VisualEntity(Vec2 position, Map<String, Sprite> sprites, String currentSpriteKey, GamePanel gamePanel) {
        super(position);
        this.sprites = sprites;
        setCurrentSpriteKey(currentSpriteKey);
        this.gamePanel = gamePanel;
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
            System.out.println("No such a key, cannot set the current sprite key");
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
    }


    @Override
    public void draw(Graphics2D g2) {
        if(currentSpriteKey != null) {
            g2.drawImage(
                    sprites.get(currentSpriteKey).getSprite(),
                    (int)super.position.x,
                    (int)super.position.y,
                    gamePanel.tileSize,
                    gamePanel.tileSize,
                    null
            );
        }
    }
}
