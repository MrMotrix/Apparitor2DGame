package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Menu {
    private GamePanel gamePanel;

    public Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("MENU PAUSE", gamePanel.screenWidth / 2 - 80, gamePanel.screenHeight / 2 - 100);

        g2.drawString("Reprendre", gamePanel.screenWidth / 2 - 60, gamePanel.screenHeight / 2);
        g2.drawString("Quitter", gamePanel.screenWidth / 2 - 60, gamePanel.screenHeight / 2 + 40);
    }
}