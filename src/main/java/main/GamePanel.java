package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Player;
import objects.OBJ_Key;
import objects.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize*scale;
    public final int max_screenCol = 16;
    public final int max_screenRow = 12;
    public final int screenWidth = tileSize * max_screenCol;
    public final int screenHeight = tileSize * max_screenRow;

    public final int maxWorldCol = 27;
    public final int maxWorldRow = 24;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private final int FPS = 60;

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

    //game state
    public int gameState = 2;
    public int pauseState = 0; //le pause state gere aussi l'affichage du menu
    public int playState = 1;
    public int titleState = 2;



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
    }

    public void setupGame(){
        aSetter.setObjects();
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void run() {
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

    }

    public void update() {

        if(gameState == playState) {
            player.update();
        }
        else if(gameState == pauseState) {
           menu.checkClick(mouseH.getLastClick());
           mouseH.resetLastClick();
        }
        else if(gameState == titleState) {
            titleScreen.checkClick(mouseH.getLastClick());
            mouseH.resetLastClick();
        }
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null)
                obj[i].update();
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null)
                obj[i].draw(g2);
        }
        player.draw(g2);
        if (gameState == pauseState) {
            menu.draw(g2);
        }
        else if(gameState == titleState) {
            titleScreen.draw(g2);
        }

        g2.dispose();
    }
}
