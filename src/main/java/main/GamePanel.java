package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import entity.Player;
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

    public final int maxWorldCol = 53;
    public final int maxWorldRow = 31;
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
    public SuperObject obj[] = new SuperObject[10];


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
        // Get graphics context for the fog image
        Graphics2D fogG = fogImage.createGraphics();

        // Reset fog (fully black)
        fogG.setComposite(AlphaComposite.Src);
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        float radius = Math.min(screenWidth, screenHeight) / 3.0f; // Rayon du spot lumineux
        int TILE_SIZE = 8;
        int MAX_OPACITY = 255;

        // Dessiner la grille de carrés noirs avec opacité variable
        for (int x = 0; x < screenWidth; x += TILE_SIZE) {
            for (int y = 0; y < screenHeight; y += TILE_SIZE) {
                // Calculer la distance du centre à ce carré
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

                // Opacité 0% au centre, 100% au-delà du rayon
                float opacity = (float) Math.min(1, distance / radius);
                int alpha = (int) (opacity * MAX_OPACITY);

                // Dessiner le carré noir avec une opacité variable
                fogG.setColor(new Color(0, 0, 0, alpha));
                fogG.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }
        // Cut out a polygon shape where we want to see through (Test Pour Camera)
        //faudra itérer sur les hitbox visible dans le noir

        for(int i = 3; i <= 5 ; i++) {
            if (obj[i] != null && obj[i].hitbox != null) {
                fogG.setComposite(AlphaComposite.DstOut); // This makes the polygon transparent
                fogG.fill(obj[i].hitbox.getPolygonAt(obj[i].screenPosition));
            }
        }



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


        player.draw(g2);


        drawFog(g2);
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
