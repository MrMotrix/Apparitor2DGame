package ui;

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

    public String getItemsNames() {
            if (!items.isEmpty()) {
                return items.getLast().getName();
            }
            return null;
        }

    public void removeItem(SuperObject item) {
        items.remove(item);
    }
    public void removeLastItem() {
        if (!items.isEmpty()) {
            items.remove(items.size() - 1);
        }
    }

    public List<SuperObject> getItems() {
        return items;
    }

    public boolean isFull() {
        return items.size() >= maxSize;
    }

}