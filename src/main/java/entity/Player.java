package entity;

import entity.Character;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;
import main.KeyHandler;
import main.GamePanel;
import main.Vec2;
import sprite.Sprite;
import sprite.SpriteLibrary;

public class Player extends Character {
    public KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(new Vec2(100,100),gamePanel,"idle-up",6,false);
        this.keyHandler = keyHandler;
        super.direction = Direction.UP;
        loadSprites();
        System.out.println(sprites.keySet());
    }

    @Override
    public void update() {
        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.rightPressed || keyHandler.leftPressed) {
            if (this.keyHandler.upPressed) {
                super.setDirection(Direction.UP);
                super.setCurrentSpriteKey("up");
                velocity.set(0, -1);
            }
            else if (this.keyHandler.downPressed) {
                super.setDirection(Direction.DOWN);
                super.setCurrentSpriteKey("down");

                velocity.set(0, 1);
            }
            else if (this.keyHandler.leftPressed) {
                super.setDirection(Direction.LEFT);
                super.setCurrentSpriteKey("left");
                velocity.set(-1, 0);
            }
            else if (this.keyHandler.rightPressed) {
                super.setDirection(Direction.RIGHT);
                super.setCurrentSpriteKey("right");
                velocity.set(1, 0);
            }
        } else {
            switch (direction){
                case UP:
                    super.setCurrentSpriteKey("idle-up");
                    break;
                case DOWN:
                    super.setCurrentSpriteKey("idle-down");
                    break;
            }
            super.setDirection(Direction.IDLE);
            velocity.set(0, 0);
        }
        super.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    private void loadSprites(){
        String basePath = Paths.get("src/main/perso").toAbsolutePath().toString()+"/";
        Sprite frontSprite = new Sprite(
                List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-2"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-3"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-4"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-5")
                ),
                12
        );
        addSprite("up", frontSprite);
        addSprite("idle-up", new Sprite(List.of(
                SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-0")),14));
    }
}