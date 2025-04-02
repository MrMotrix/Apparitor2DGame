package main;

import entity.Inventory;
import objects.SuperObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

public class InventoryScreen extends Screen {
    private Inventory inventory;
    private int selectedIndex = 0;
    private static final int SPRITE_SIZE = 32;
    private static final int PADDING = 10;



    public int MenuX = 10;
    public int MenuY = 10;

    public InventoryScreen(GamePanel gamePanel, Inventory inventory) {
        super(gamePanel);
        this.inventory = inventory;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRect( menuX - 320, menuY - 300, menuWidth + 20, optionHeight * (inventory.getItems().size() + 1));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Inventaire", menuX - 270, menuY - 300);

        List<SuperObject> items = inventory.getItems();
        for (int i = 0; i < items.size(); i++) {
            SuperObject item = items.get(i);

            int itemY = menuY + (i * optionHeight);

            if (i == selectedIndex) {
                g2.setColor(Color.YELLOW);
            }
            else {
                g2.setColor(Color.WHITE);
            }
            drawMenuBox(g2, i * optionHeight, menuY -300, menuX -250);

            if (item.getSprite() != null) {
                g2.drawImage(
                        item.getSprite().getSprite(),
                        menuX + PADDING +20,
                        itemY + (optionHeight - SPRITE_SIZE) / 2,
                        SPRITE_SIZE,
                        SPRITE_SIZE,
                        null
                );
            }

            drawMenuOption(g2, item.getName(), menuY -220, menuX -300);
        }

    }

}