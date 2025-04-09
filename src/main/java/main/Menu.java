package main;

import ui.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Menu extends Screen {
    public Menu(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("MENU PAUSE", gamePanel.screenWidth / 2 - 80, gamePanel.screenHeight / 2 - 100);

        drawMenuOption(g2, "Reprendre", 0);
        drawMenuOption(g2, "Options", 40);
        drawMenuOption(g2, "Quitter", 80);

        g2.setColor(Color.RED);
        drawMenuBox(g2, 0);
        drawMenuBox(g2, optionHeight);
        drawMenuBox(g2, optionHeight * 2);
    }

    @Override
    public void firstOption() {
        if (gamePanel.gameState == gamePanel.pauseState) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    @Override
    public void secondOption() {
        if (gamePanel.gameState == gamePanel.pauseState) {
            System.out.println("Options");
        }
    }

    @Override
    public void thirdOption() {
        if (gamePanel.gameState == gamePanel.pauseState) {
            System.exit(0);
        }
    }
}