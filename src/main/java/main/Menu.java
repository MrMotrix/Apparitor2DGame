package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

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
        g2.drawString("Options", gamePanel.screenWidth / 2 - 60, gamePanel.screenHeight / 2 + 40);
        g2.drawString("Quitter", gamePanel.screenWidth / 2 - 60, gamePanel.screenHeight / 2 + 80);

        int menuX = gamePanel.screenWidth / 2 - 65;
        int menuWidth = 160;
        int startY = gamePanel.screenHeight / 2 - 35;
        int optionHeight = 40;

        g2.setColor(Color.RED);
        g2.drawRect(menuX, startY, menuWidth, optionHeight);
        g2.drawRect(menuX, startY + optionHeight, menuWidth, optionHeight);
        g2.drawRect(menuX, startY + optionHeight * 2, menuWidth, optionHeight);

    }

    public void checkClick(Point click) {
        if (click == null) return;

        int menuX = gamePanel.screenWidth / 2 - 65;
        int menuWidth = 160;
        int menuY = gamePanel.screenHeight / 2 - 35;
        int optionHeight = 40;
        System.out.println(click);


        if (click.x >= menuX && click.x <= menuX + menuWidth && click.y >= menuY && click.y <= menuY + optionHeight) {
            gamePanel.pauseState = false;
        }
        else if (click.x >= menuX && click.x <= menuX + menuWidth && click.y >= menuY + optionHeight && click.y <= menuY + optionHeight * 2) {
            System.out.println("Options");
        }
        else if (click.x >= menuX && click.x <= menuX + menuWidth && click.y >= menuY + optionHeight * 2 && click.y <= menuY + optionHeight * 3) {
            System.exit(0);
        }
    }
}