package ui;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Screen {
    public GamePanel gamePanel;
    public int menuX;
    public int menuWidth;
    public int menuY;
    public int optionHeight;

    public Screen(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.menuWidth = 160;
        this.optionHeight = 40;
        this.menuX = gamePanel.screenWidth / 2 - 65;
        this.menuY = gamePanel.screenHeight / 2 - 35;
    }

    public abstract void draw(Graphics2D g2);

    protected void drawMenuOption(Graphics2D g2, String text, int yOffset) {
        g2.drawString(text, gamePanel.screenWidth / 2 - 60, gamePanel.screenHeight / 2 + yOffset);
    }

    protected void drawMenuOption(Graphics2D g2, String text, int menuX, int menuY) {
        g2.drawString(text, menuX , menuY );
    }


    protected void drawMenuBox(Graphics2D g2, int yOffset) {
        g2.drawRect(menuX, menuY + yOffset, menuWidth, optionHeight);
    }

    protected void drawMenuBox(Graphics2D g2, int yOffset, int menuX, int menuY) {
        g2.drawRect(menuX, menuY + yOffset, menuWidth, optionHeight);
    }

    public void checkClick(Point click) {
        if (click == null) return;

        if (click.x >= menuX && click.x <= menuX + menuWidth) {
            if (click.y >= menuY && click.y <= menuY + optionHeight) {
                firstOption();
            }
            else if (click.y >= menuY + optionHeight && click.y <= menuY + optionHeight * 2) {
                secondOption();
            }
            else if (click.y >= menuY + optionHeight * 2 && click.y <= menuY + optionHeight * 3) {
                thirdOption();
            }
        }
    }

    public void firstOption(){};
    public void secondOption(){};
    public void thirdOption(){};
}