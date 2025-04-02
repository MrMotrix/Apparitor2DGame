package entity;

import objects.SuperObject;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<SuperObject> items;
    private int maxSize;

    public Inventory(int maxSize) {
        this.items = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public boolean addItem(SuperObject item) {
        if (items.size() < maxSize) {
            items.add(item);
            System.out.println("Item added to inventory: " + item.getName());
            return true;
        }
        return false;
    }

    public void removeItem(SuperObject item) {
        items.remove(item);
    }

    public List<SuperObject> getItems() {
        return items;
    }

    public boolean isFull() {
        return items.size() >= maxSize;
    }

}