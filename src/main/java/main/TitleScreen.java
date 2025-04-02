package main;
import java.awt.*;

public class TitleScreen extends Screen {
    public TitleScreen(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(6, 21, 43));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("MENU PRINCIPAL", gamePanel.screenWidth / 2 - 100, gamePanel.screenHeight / 2 - 90);

        drawMenuOption(g2, "Load Game", 0);
        drawMenuOption(g2, "Options", 40);
        drawMenuOption(g2, "Quitter", 80);
    }

    @Override
    public void firstOption() {
        if (gamePanel.gameState == gamePanel.titleState) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    @Override
    public void secondOption() {
        if (gamePanel.gameState == gamePanel.titleState) {
            System.out.println("Options");
        }
    }

    @Override
    public void thirdOption() {
        if (gamePanel.gameState == gamePanel.titleState) {
            System.exit(0);
        }
    }
}
