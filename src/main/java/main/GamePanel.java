package main;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import entity.Apparitor;
import entity.Player;
import objects.OBJ_Camera;
import objects.SuperObject;
import tile.TileManager;
import entity.Inventory;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int max_screenCol = 16;
    public final int max_screenRow = 12;
    public final int screenWidth = tileSize * max_screenCol;
    public final int screenHeight = tileSize * max_screenRow;

    public final int maxWorldCol = 64;
    public final int maxWorldRow = 30;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private final int FPS = 60;
    private BufferedImage fogImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);;

    private Thread gameThread;
    public KeyHandler keyH;
    public TileManager tileManager;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public Player player;
    public SuperObject obj[] = new SuperObject[100];
    public OBJ_Camera cameras[] = new OBJ_Camera[100];
    public Apparitor apparitors[] = new Apparitor[100];


    public Menu menu;
    public MouseHandler mouseH;

    public TitleScreen titleScreen;
    public InventoryScreen inventory;

    //game state
    public int gameState = 2;
    public int pauseState = 0; //pause state = menu state
    public int playState = 1;
    public int titleState = 2;
    public boolean inventoryState = false;


    public GamePanel() {
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
        this.inventory = new InventoryScreen(this,player.getInventory());
    }

    public void setupGame() {
        aSetter.setObjects();
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    /*public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }*/

    /*public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread !=null) {
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / drawInterval;
        lastTime = currentTime;

        if (delta >= 1) {
            update();
            repaint();
            delta--;
        }

        // Ajout d'une pause pour éviter de monopoliser le CPU
        try {
            Thread.sleep(1);  // Permet au CPU de respirer un peu
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}*/

    public void run() {
        final int FPS = 60;
        final double drawInterval = 1_000_000_000.0 / FPS; // Intervalle en nanosecondes
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
                frames++;  // Comptage des FPS réels
            }

            if (timer >= 1_000_000_000) { // Affiche le nombre de FPS toutes les secondes
                //System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }

            try {
                Thread.sleep(1);  // Laisser le CPU respirer
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void update() {

        if(gameState == playState) {
            player.update();
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null)
                    obj[i].update();
            }

            for(int i = 0; i < apparitors.length; i++) {
                if(apparitors[i] != null)
                    apparitors[i].update();
            }

            for(int i = 0; i < cameras.length; i++) {
                if(cameras[i] != null)
                    cameras[i].update();
            }
        }
        else if(gameState == pauseState) {
           menu.checkClick(mouseH.getLastClick());
           mouseH.resetLastClick();
        }
        else if(gameState == titleState) {
            titleScreen.checkClick(mouseH.getLastClick());
            mouseH.resetLastClick();
        }
    }

    public void drawFog(Graphics2D g2) {
        Graphics2D fogG = fogImage.createGraphics();
        fogG.setComposite(AlphaComposite.Src);

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        float radius = Math.min(screenWidth, screenHeight) / 4.0f; // Rayon du spot lumineux

        // Dégradé radial pour simuler l’effet du brouillard
        Point2D center = new Point2D.Float(centerX, centerY);
        float[] dist = {0.0f, 1.0f}; // Début à 0% d'opacité, Fin à 100% d'opacité
        Color[] colors = {new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)}; // Transparent → Noir

        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
        fogG.setPaint(gradient);
        fogG.fillRect(0, 0, screenWidth, screenHeight); // Appliquer le gradient sur toute la surface

        // Supprimer les zones visibles
        Area visibleArea = new Area();

        /*for (int i = 3; i < 100; i++) {
            if (obj[i] != null && obj[i].hitbox != null) {
                visibleArea.add(new Area(obj[i].hitbox.getPolygonAt(obj[i].screenPosition)));
            }
        }*/

       for(int i = 0; i < cameras.length; i++) {
            if(cameras[i] != null) {
                visibleArea.add(new Area(cameras[i].detectionZone.getPolygonAt(cameras[i].screenPosition)));
            }
        }

        for(int i = 0; i < apparitors.length; i++) {
            if(apparitors[i] != null)
                visibleArea.add(new Area(apparitors[i].detectionZone.getPolygonAt(apparitors[i].screenPosition)));
        }

        // Rendre ces zones transparentes
        fogG.setComposite(AlphaComposite.DstOut);
        fogG.fill(visibleArea);
        fogG.dispose();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        /*// Enabling anti-aliasing for smoother edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        */

        tileManager.draw(g2);

        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null)
                obj[i].draw(g2);
        }

        for(int i = 0; i < cameras.length; i++) {
            if(cameras[i] != null)
                cameras[i].draw(g2);
        }

        for(int i = 0; i < apparitors.length; i++) {
            if(apparitors[i] != null)
                apparitors[i].draw(g2);
        }

        player.draw(g2);


        //drawFog(g2);
        g2.drawImage(fogImage, 0, 0, null);

        if (inventoryState == true && gameState == playState) {
            inventory.draw(g2);
        }


        if (gameState == pauseState) {
            menu.draw(g2);
        }

        else if(gameState == titleState) {
            titleScreen.draw(g2);
        }


        g2.dispose();
        Toolkit.getDefaultToolkit().sync();
    }
}
