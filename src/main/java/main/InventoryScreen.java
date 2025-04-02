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



    public int MenuX = 30;
    public int MenuY = 30;

    public InventoryScreen(GamePanel gamePanel, Inventory inventory) {
        super(gamePanel);
        this.inventory = inventory;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 150));
        g2.fillRect( MenuX, MenuY, menuWidth + 20, optionHeight * (inventory.getItems().size() + 1));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Inventaire", MenuX, MenuY);

        List<SuperObject> items = inventory.getItems();
        for (int i = 0; i < items.size(); i++) {
            SuperObject item = items.get(i);

            int itemY = MenuY + (i * optionHeight);

            if (i == selectedIndex) {
                g2.setColor(Color.YELLOW);
            }
            else {
                g2.setColor(Color.WHITE);
            }
            drawMenuBox(g2, i * optionHeight, MenuY, MenuX);

            if (item.getSprite() != null) {
                g2.drawImage(
                        item.getSprite().getSprite(),
                        MenuX + PADDING +20,
                        itemY + (optionHeight - SPRITE_SIZE) / 2,
                        SPRITE_SIZE,
                        SPRITE_SIZE,
                        null
                );
            }

            drawMenuOption(g2, item.getName(), MenuX, itemY + (optionHeight ) - SPRITE_SIZE/2 );
        }

    }

}