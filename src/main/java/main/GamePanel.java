package main;

import entity.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {
    private BufferedImage sprite;
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize*scale;
    final int max_screenCol = 19;
    final int max_screenRow = 16;
    final int screen_width = 1216;
    final int screen_height = 920;
    private Thread gameThread;
    private KeyHandler keyH;
    private Player player;
    private final int FPS = 60;

    public GamePanel() {
        this.keyH = new KeyHandler();
        this.player = new Player(this, this.keyH);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();
    }
}
