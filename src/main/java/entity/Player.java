package entity;

import main.GamePanel;
import main.KeyHandler;
import math.Vec2;
import tools.SpriteLibrary;
import temp.*;
import sprite.Sprite;

import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.nio.file.Paths;
import java.util.List;

public class Player extends Character{
    private KeyHandler keyHandler;
    //public final int screenX;
    //public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(
                new Vec2(100,100),
                new Vec2(gamePanel.screenWidth/2 - (gamePanel.tileSize/2),gamePanel.screenHeight/2 - (gamePanel.tileSize/2)),
                new Hitbox(
                        8,
                        16,
                        32,
                        32,
                        HitboxState.DISABLED,
                        HitboxType.SOLID
                ),
                gamePanel,
                "idle-down",
                6,
                false
        );

        this.keyHandler = keyHandler;
        super.direction = Direction.DOWN;
        loadSprites();
        System.out.println(sprites.keySet());
    }

    private boolean handleDiagonalMovement() {
        if (keyHandler.upPressed && keyHandler.leftPressed) {
            setMovementInfo(Direction.UP, "up", -1, -1);
            return true;
        }
        if (keyHandler.upPressed && keyHandler.rightPressed) {
            setMovementInfo(Direction.UP, "up", 1, -1);
            return true;
        }
        if (keyHandler.downPressed && keyHandler.leftPressed) {
            setMovementInfo(Direction.DOWN, "down", -1, 1);
            return true;
        }
        if (keyHandler.downPressed && keyHandler.rightPressed) {
            setMovementInfo(Direction.DOWN, "down", 1, 1);
            return true;
        }
        return false;
    }

    private void setMovementInfo(Direction direction, String spriteKey, int vx, int vy) {
        super.setDirection(direction);
        super.setCurrentSpriteKey(spriteKey);
        velocity.set(vx, vy);
    }

    private void handleIdleState() {
        switch (direction) {
            case Direction.UP -> super.setCurrentSpriteKey("idle-up");
            case Direction.DOWN -> super.setCurrentSpriteKey("idle-down");
            default -> super.setCurrentSpriteKey("idle");
        }
        super.setDirection(Direction.IDLE);
        velocity.set(0, 0);
    }

    @Override
    public void update() {

        if ((keyHandler.upPressed && keyHandler.downPressed) ||
                (keyHandler.leftPressed && keyHandler.rightPressed)){
            handleIdleState();
            return;
        }

        if(handleDiagonalMovement()){}
        else if (keyHandler.upPressed) setMovementInfo(Direction.UP, "up", 0, -1);
        else if (keyHandler.downPressed) setMovementInfo(Direction.DOWN, "down", 0, 1);
        else if (keyHandler.leftPressed) setMovementInfo(Direction.LEFT, "left", -1, 0);
        else if (keyHandler.rightPressed) setMovementInfo(Direction.RIGHT, "right", 1, 0);
        else handleIdleState();
        this.hitbox.setState(HitboxState.DISABLED);
        gamePanel.cChecker.checkTile(this);
        if(this.hitbox.getState() == HitboxState.ACTIVE) velocity.set(0, 0);
        super.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    private void loadSprites(){
        String basePath = Paths.get("src/main/resources").toAbsolutePath().toString()+"/";
        Sprite frontSprite = new Sprite(
                List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-front-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-front-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-front-frame-2")
                ),
                14
        );

        Sprite backSprite = new Sprite(
                List.of(
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-back-frame-0"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-back-frame-1"),
                        SpriteLibrary.getInstance(basePath).getSprite("characters","player-back-frame-2")
                ),
                14
        );

        addSprite("down", frontSprite);
        addSprite("up", backSprite);
        addSprite("idle-up", new Sprite(
                List.of(
                    SpriteLibrary.getInstance(basePath).getSprite("characters","player-back-frame-0")),
                14
                )
        );

        addSprite("idle-down", new Sprite(
                        List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-front-frame-0")),
                        14
                )
        );

        addSprite("left", new Sprite(
                        List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-back-frame-0")),
                        14
                )
        );

        addSprite("right", new Sprite(
                        List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-front-frame-0")),
                        14
                )
        );
    }
}