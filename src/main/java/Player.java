import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.file.*;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;
    private BufferedImage up1, up2;
    private BufferedImage down1, down2;
    private BufferedImage imoFace, imoBack;
    private Vec2 velocity;


    public Player(GamePanel gp, KeyHandler keyH) {
        super();
        this.gp = gp;
        this.keyH = keyH;
        this.velocity = new Vec2(0, 0);
        this.setDefaultValues();
        this.getPlayerImage();
    }

    public void setDefaultValues() {
        this.position.set(100, 100);
        this.speed = 6;
        this.direction = "up";
    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if (this.keyH.upPressed) {
                setDirection("up");
                velocity.set(0, -1);
            }
            else if (this.keyH.downPressed) {
                setDirection("down");
                velocity.set(0, 1);
            }
            else if (this.keyH.leftPressed) {
                setDirection("left");
                velocity.set(-1, 0);
            }
            else if (this.keyH.rightPressed) {
                setDirection("right");
                velocity.set(1, 0);
            }
            move();
            
            spriteCounter++;
            if (spriteCounter > 14) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        } else {

            switch (direction) {
                case "up":
                    direction = "imoBack";
                    break;
                case "down":
                    direction = "imoFace";
            }

            velocity.set(0, 0);
        }
    }

    private void move() {
        velocity.scale(speed);
        position.add(velocity);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (this.spriteNum == 1) {
                    image = up1;
                }
                if (this.spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (this.spriteNum == 1) {
                    image = down1;
                }
                if (this.spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                image = this.imoFace;
                break;
            case "left":
                image = this.imoFace;
                break;
            case "imoFace":
                image = this.imoFace;
                break;
            case "imoBack":
                image = this.imoBack;
        }
        g2.drawImage(image, position.x, position.y, gp.tileSize, gp.tileSize, null);
    }

    public void getPlayerImage() {
        try {
            String basePath = Paths.get("src/main/perso").toAbsolutePath().toString()+"/";
            System.out.println(basePath);
            this.up1 = ImageIO.read(new java.io.File(basePath + "pixil-frame-4.png"));
            this.up2 = ImageIO.read(new java.io.File(basePath + "pixil-frame-6.png"));
            this.imoFace = ImageIO.read(new java.io.File(basePath + "pixil-frame-0.png"));
            this.down1 = ImageIO.read(new java.io.File(basePath + "pixil-frame-1.png"));
            this.down2 = ImageIO.read(new java.io.File(basePath + "pixil-frame-2.png"));
            this.imoBack = ImageIO.read(new java.io.File(basePath + "pixil-frame-3.png"));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des images : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
