package objects;
import main.Vec2;
import entity.Entity;


public class Object extends Entity {
    int posX;
    int posY;
    boolean collectable = false;
    public Object(int posX, int posY){
        super(new Vec2(posX,posY));

    }

    public void update(){

    }
}
