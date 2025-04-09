package ui;
import main.GamePanel;
import math.Vec2;
import objects.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import temp.Hitbox;
import temp.HitboxState;
import temp.HitboxType;
import ui.Inventory;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private SuperObject item1;
    private SuperObject item2;

    // Fausse implémentation de SuperObject pour le test
    static class TestItem extends SuperObject {
        private final String name;

        public TestItem(String name) {
            super(
                    new Vec2(0,0),
                    new Vec2(0,0),
                    new Hitbox(0,0,0,0, HitboxState.DISABLED, HitboxType.SOLID, Color.RED,Color.RED),
                    new GamePanel(),
                    "test",
                    false,
                    false,
                    "test"
            );
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void onPickUp() {

        }
    }

    @BeforeEach
    void setUp() {
        inventory = new Inventory(2); // taille max = 2
        item1 = new TestItem("Torch");
        item2 = new TestItem("Key");
    }

    @Test
    void testAddItem_successfully() {
        assertTrue(inventory.addItem(item1));
        assertTrue(inventory.getItems().contains(item1));
    }

    @Test
    void testAddItem_whenFull_shouldReturnFalse() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        SuperObject item3 = new TestItem("Extra");
        assertFalse(inventory.addItem(item3));
        assertEquals(2, inventory.getItems().size());
    }

    @Test
    void testRemoveItem() {
        inventory.addItem(item1);
        inventory.removeItem(item1);
        assertFalse(inventory.getItems().contains(item1));
    }

    @Test
    void testIsFull() {
        assertFalse(inventory.isFull());
        inventory.addItem(item1);
        inventory.addItem(item2);
        assertTrue(inventory.isFull());
    }

    @Test
    void testGetItems_returnsCorrectList() {
        inventory.addItem(item1);
        inventory.addItem(item2);
        List<SuperObject> items = inventory.getItems();
        assertEquals(2, items.size());
        assertEquals(item1, items.get(0));
        assertEquals(item2, items.get(1));
    }
}
