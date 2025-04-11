package main;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import entity.Apparitor;
import entity.InvisibleWall;
import entity.Player;
import objects.OBJ_Camera;
import objects.OBJ_Doormats;
import objects.SuperObject;
import tile.TileManager;
import ui.InventoryScreen;
import ui.TitleScreen;
import ui.Ui;

public class GamePanel extends JPanel implements Runnable {
    // Base tile sizes (before scaling)
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;

    // Screen settings (based on a reference resolution)
    public final int max_screenCol = 16;
    public final int max_screenRow = 12;
    public final int referenceScreenWidth = tileSize * max_screenCol; // 1024
    public final int referenceScreenHeight = tileSize * max_screenRow; // 768

    // Actual screen dimensions (will be set to fullscreen resolution)
    public int screenWidth;
    public int screenHeight;
    private double scaleX = 1.0; // Scaling factor for X-axis
    private double scaleY = 1.0; // Scaling factor for Y-axis

    // World settings
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private final int FPS = 60;
    private BufferedImage fogImage;

    private Thread gameThread;
    public KeyHandler keyH;
    public TileManager tileManager;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public Player player;
    public SuperObject obj[] = new SuperObject[7];
    public OBJ_Camera cameras[] = new OBJ_Camera[16];
    public Apparitor apparitors[] = new Apparitor[7];
    public OBJ_Doormats doormats[] = new OBJ_Doormats[12];
    public InvisibleWall invisibleWall[] = new InvisibleWall[1];

    public Menu menu;
    public Ui ui;
    public MouseHandler mouseH;

    public TitleScreen titleScreen;
    public InventoryScreen inventory;

    // Game state
    public int gameState = 2;
    public int pauseState = 0; // Pause state = menu state
    public int playState = 1;
    public int titleState = 2;
    public int gameOverState = 6;
    public int endState = 7;
    public boolean inventoryState = false;

    private boolean isGameOverTriggered = false;
    private long gameOverTimer = 0;
    private final int gameOverDelay = 2000; // 2 seconds

    public GamePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        scaleX = (double) screenWidth / referenceScreenWidth;
        scaleY = (double) screenHeight / referenceScreenHeight;

        fogImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);

        this.keyH = new KeyHandler(this);
        this.player = new Player(this, this.keyH);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.tileManager = new TileManager(this);
        this.cChecker = new CollisionChecker(this);
        this.menu = new Menu(this);
        this.mouseH = new MouseHandler(this);
        this.addMouseListener(mouseH);
        this.titleScreen = new TitleScreen(this);
        this.aSetter = new AssetSetter(this);
        this.inventory = new InventoryScreen(this, player.getInventory());
        this.ui = new Ui(this);
    }

    public void setupGame() {
        aSetter.setObjects();
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void restart() {
        player.toDefaultPosition();
        player.resetLife();
        player.getInventory().clearItems();
        setupGame();
        gameState = playState;
        isGameOverTriggered = false;
    }

    public void run() {
        final double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }

            if (timer >= 1_000_000_000) {
                frames = 0;
                timer = 0;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) obj[i].update();
            }
            for (int i = 0; i < apparitors.length; i++) {
                if (apparitors[i] != null) apparitors[i].update();
            }
            for (int i = 0; i < cameras.length; i++) {
                if (cameras[i] != null) cameras[i].update();
            }
            for (int i = 0; i < doormats.length; i++) {
                if (doormats[i] != null) doormats[i].update();
            }
        } else if (gameState == pauseState) {
            menu.checkClick(mouseH.getLastClick());
            mouseH.resetLastClick();
        } else if (gameState == titleState) {
            titleScreen.checkClick(mouseH.getLastClick());
            mouseH.resetLastClick();
        } else if (gameState == gameOverState) {
            if (!isGameOverTriggered) {
                isGameOverTriggered = true;
                gameOverTimer = System.currentTimeMillis();
            } else {
                long elapsed = System.currentTimeMillis() - gameOverTimer;
                if (elapsed >= gameOverDelay) {
                    restart();
                }
            }
        }
    }

    public void drawFog(Graphics2D g2) {
        Graphics2D fogG = fogImage.createGraphics();

        // Fill the entire screen with black (fog)
        fogG.setComposite(AlphaComposite.Src);
        fogG.setColor(new Color(0, 0, 0, 255));
        fogG.fillRect(0, 0, screenWidth, screenHeight);

        // Remove visible areas (polygons)
        Area visibleArea = new Area();
        for (int i = 0; i < apparitors.length; i++) {
            if (apparitors[i] != null) {
                // Scale the detection zone polygon to match fullscreen
                Polygon scaledPolygon = scalePolygon(apparitors[i].detectionZone.getPolygonAt(apparitors[i].screenPosition));
                visibleArea.add(new Area(scaledPolygon));
            }
        }
        fogG.setComposite(AlphaComposite.DstOut);
        fogG.setPaint(Color.BLACK);
        fogG.fill(visibleArea);

        // Create holes around light sources (gradients)
        fogG.setComposite(AlphaComposite.DstOut);
        float playerRadius = 150f * (float) Math.min(scaleX, scaleY); // Scale radius
        float apparitorRadius = 50f * (float) Math.min(scaleX, scaleY);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {
                new Color(0, 0, 0, 255), // Center: opaque
                new Color(0, 0, 0, 0)    // Edge: transparent
        };

        // Player light source
        Point2D playerCenter = new Point2D.Float(
                (player.screenPosition.getXInt() + tileSize / 2) * (float) scaleX,
                (player.screenPosition.getYInt() + tileSize / 2) * (float) scaleY
        );
        RadialGradientPaint playerGradient = new RadialGradientPaint(playerCenter, playerRadius, dist, colors);
        fogG.setPaint(playerGradient);
        fogG.fillRect(
                (int) (playerCenter.getX() - playerRadius),
                (int) (playerCenter.getY() - playerRadius),
                (int) (2 * playerRadius),
                (int) (2 * playerRadius)
        );

        // Apparitor light sources
        for (Apparitor apparitor : apparitors) {
            if (apparitor != null) {
                Point2D apparitorCenter = new Point2D.Float(
                        (apparitor.screenPosition.getXInt() + tileSize / 2) * (float) scaleX,
                        (apparitor.screenPosition.getYInt() + tileSize / 2) * (float) scaleY
                );
                RadialGradientPaint apparitorGradient = new RadialGradientPaint(apparitorCenter, apparitorRadius, dist, colors);
                fogG.setPaint(apparitorGradient);
                fogG.fillRect(
                        (int) (apparitorCenter.getX() - apparitorRadius),
                        (int) (apparitorCenter.getY() - apparitorRadius),
                        (int) (2 * apparitorRadius),
                        (int) (2 * apparitorRadius)
                );
            }
        }

        fogG.dispose();
    }

    private Polygon scalePolygon(Polygon original) {
        Polygon scaled = new Polygon();
        for (int i = 0; i < original.npoints; i++) {
            scaled.addPoint(
                    (int) (original.xpoints[i] * scaleX),
                    (int) (original.ypoints[i] * scaleY)
            );
        }
        return scaled;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.scale(scaleX, scaleY);

        tileManager.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) obj[i].draw(g2);
        }
        for (int i = 0; i < cameras.length; i++) {
            if (cameras[i] != null) cameras[i].draw(g2);
        }
        for (int i = 0; i < doormats.length; i++) {
            if (doormats[i] != null) doormats[i].draw(g2);
        }
        for (int i = 0; i < invisibleWall.length; i++) {
            if (invisibleWall[i] != null) invisibleWall[i].draw(g2);
        }
        for (int i = 0; i < apparitors.length; i++) {
            if (apparitors[i] != null) apparitors[i].draw(g2);
        }

        player.draw(g2);

        g2.scale(1.0 / scaleX, 1.0 / scaleY);
        drawFog(g2);
        g2.drawImage(fogImage, 0, 0, null);

        ui.drawPlayerLife(g2);

        if (inventoryState && gameState == playState) {
            inventory.draw(g2);
        }

        if (gameState == pauseState) {
            menu.draw(g2);
        } else if (gameState == titleState) {
            titleScreen.draw(g2);
        }

        if (gameState == gameOverState) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 64));
            String msg = "GAME OVER";
            FontMetrics fm = g2.getFontMetrics();
            int x = (screenWidth - fm.stringWidth(msg)) / 2;
            int y = screenHeight / 2;
            g2.drawString(msg, x, y);
        }

        if (gameState == endState) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 64));
            String msg = "BRAVO !";
            FontMetrics fm = g2.getFontMetrics();
            int x = (screenWidth - fm.stringWidth(msg)) / 2;
            int y = screenHeight / 2;
            g2.drawString(msg, x, y);
        }

        g2.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }
}