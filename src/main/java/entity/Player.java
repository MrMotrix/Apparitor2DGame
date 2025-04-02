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
    public int nbKey = 0;
    public int healthPoints = 3;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(
                new Vec2(100,100),
                new Vec2(gamePanel.screenWidth/2 - (gamePanel.tileSize/2),gamePanel.screenHeight/2 - (gamePanel.tileSize/2)),
                new Hitbox(
                        13,
                        21,
                        32,
                        32,
                        HitboxState.DISABLED,
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

        this.keyHandler = keyHandler;
        super.direction = Direction.DOWN;
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX + hitbox.width, hitbox.defaultBoundsY + hitbox.height);
        super.hitbox.getBounds().addPoint(hitbox.defaultBoundsX, hitbox.defaultBoundsY + hitbox.height);
        loadSprites();
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
            case Direction.LEFT -> super.setCurrentSpriteKey("idle-left");
            case Direction.RIGHT -> super.setCurrentSpriteKey("idle-right");
            //default -> super.setCurrentSpriteKey("idle");
        }
        super.setDirection(Direction.IDLE);
        velocity.set(0, 0);
    }

    public void pickUpObject(int index){
        if(index != 999) {
            gamePanel.obj[index].onPickUp();
            if(gamePanel.obj[index].isPeakable())
                gamePanel.obj[index] = null;
        }else{
            hitbox.setType(HitboxType.NONE);
        }
    }

    @Override
    public void update() {

        if ((keyHandler.upPressed && keyHandler.downPressed) ||
                (keyHandler.leftPressed && keyHandler.rightPressed)){
            handleIdleState();
            return;
        }

        if (keyHandler.upPressed) setMovementInfo(Direction.UP, "up", 0, -1);
        else if (keyHandler.downPressed) setMovementInfo(Direction.DOWN, "down", 0, 1);
        else if (keyHandler.leftPressed) setMovementInfo(Direction.LEFT, "left", -1, 0);
        else if (keyHandler.rightPressed) setMovementInfo(Direction.RIGHT, "right", 1, 0);
        else handleIdleState();

        /*
        //DIAGONAL MOVEMENT IF ACTIVE CHECK HITBOX ISSUE
        if(handleDiagonalMovement()){}
        else if (keyHandler.upPressed) setMovementInfo(Direction.UP, "up", 0, -1);
        else if (keyHandler.downPressed) setMovementInfo(Direction.DOWN, "down", 0, 1);
        else if (keyHandler.leftPressed) setMovementInfo(Direction.LEFT, "left", -1, 0);
        else if (keyHandler.rightPressed) setMovementInfo(Direction.RIGHT, "right", 1, 0);
        else handleIdleState();*/
        this.hitbox.setState(HitboxState.DISABLED);
        gamePanel.cChecker.checkTile(this);
        int objIndex = gamePanel.cChecker.checkObject(this,true);
        pickUpObject(objIndex);
        if(this.hitbox.getState() == HitboxState.ACTIVE) velocity.set(0, 0);

        if(keyHandler.sprintPressed) super.sprint = true;
        else super.sprint = false;

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
                List.of(
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
                List.of(
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
                List.of(
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
                List.of(
                    SpriteLibrary.getInstance(basePath).getSprite("characters","player-up-frame-0")),
                10
                )
        );

        addSprite("idle-down", new Sprite(
                        List.of(
                                SpriteLibrary.getInstance(basePath).getSprite("characters","player-down-frame-0")),
                        10
                )
        );

        addSprite("idle-left", new Sprite(
                        List.of(
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