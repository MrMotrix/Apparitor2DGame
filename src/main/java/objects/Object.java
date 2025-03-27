package objects;
import main.Vec2;
import entity.Entity;
import main.GamePanel;


public class Object extends Entity {
    int posX;
    int posY;
    boolean collectable = false;
    public Object(int posX, int posY, boolean collectable) {
        super(new Vec2(posX, posY));
        this.collectable = collectable;
    }

    public void update(){

    }

    public void collect() {
        if (collectable) {
            System.out.println("Collected");
        }
    }
}
