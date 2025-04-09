package ui;

import main.GamePanel;
import tools.SpriteLibrary;

import java.awt.*;

public class Ui {
    GamePanel gp;

    public Ui(GamePanel gamePanel) {
        this.gp = gamePanel;
    }

    public void drawPlayerLife(Graphics2D g2)
    {
        //gp.player.life = 5;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        int iconSize = 32;

        //DRAW MAX LIFE (BLANK)
        while(i < gp.player.maxHealthPoints/2)
        {
            g2.drawImage(SpriteLibrary.getInstance("").getSprite("ui","empty-hearth"),x,y,iconSize, iconSize, null);
            i++;
            x += iconSize;
        }
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        //DRAW CURRENT HEART // ITS LIKE COLORING THE BLANK HEARTS
        while(i < gp.player.healthPoints)
        {
            g2.drawImage(SpriteLibrary.getInstance("").getSprite("ui","half-hearth"),x,y,iconSize, iconSize, null);
            i++;
            if(i < gp.player.healthPoints)
            {
                g2.drawImage(SpriteLibrary.getInstance("").getSprite("ui","full-hearth"),x,y,iconSize, iconSize, null);
            }
            i++;
            x += iconSize;
        }
    }
}
