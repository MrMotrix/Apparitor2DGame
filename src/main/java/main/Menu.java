package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Menu {
    private GamePanel gamePanel;
    private int selectedOption = 0;

    public Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("MENU PAUSE", gamePanel.screenWidth / 2 - 80, gamePanel.screenHeight / 2 - 100);

        String[] options = {"Reprendre", "Quitter"}; //on peut rajouter ce qu'on veut comme btn
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g2.setColor(Color.WHITE);
            }

            else {
                g2.setColor(Color.WHITE);
            }
            g2.drawString(options[i], gamePanel.screenWidth /2-60, gamePanel.screenHeight / 2 + i * 40);
        }
    }
}