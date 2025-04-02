package temp;

import math.Vec2;

import java.awt.*;
import java.awt.geom.Area;
import java.util.List;

public class Hitbox {
    private HitboxState state;
    private HitboxType type;
    //private Rectangle bounds;
    private Polygon bounds;
    public Color insideHitBoxColor;
    public Color outsideHitBoxColor;
    public int width;
    public int height;
    public  int defaultBoundsX;
    public  int defaultBoundsY;

    public Hitbox(int x, int y, int width, int height, HitboxState state,HitboxType type, Color insideHitBoxColor, Color outsideHitBoxColor) {
        this.state = state;
        this.type = type;
        //this.bounds = new Rectangle(x, y, width, height);
        this.bounds = new Polygon();
        /*this.bounds.addPoint(x, y);
        this.bounds.addPoint(x + width, y);
        this.bounds.addPoint(x + width, y + height);
        this.bounds.addPoint(x, y + height);*/
        this.defaultBoundsX = x;
        this.defaultBoundsY = y;
        this.width = width;
        this.height = height;
        this.insideHitBoxColor = insideHitBoxColor;
        this.outsideHitBoxColor = outsideHitBoxColor;
    }

    /*public void updatePosition(int x, int y) {
        bounds.setLocation(x, y);
    }*/

    public void resetToDefaultBounds() {
        //bounds.setLocation(defaultBoundsX, defaultBoundsY);
        bounds = new Polygon();
        this.bounds.addPoint(defaultBoundsX, defaultBoundsY);
        this.bounds.addPoint(defaultBoundsX + width, defaultBoundsY);
        this.bounds.addPoint(defaultBoundsX + width, defaultBoundsY + height);
        this.bounds.addPoint(defaultBoundsX, defaultBoundsY + height);
    }

    public Polygon getPolygonAt(Vec2 worldPosition) {
        int[] transformedX = new int[bounds.npoints];
        int[] transformedY = new int[bounds.npoints];

        for (int i = 0; i < bounds.npoints; i++) {
            transformedX[i] = bounds.xpoints[i] + (int) worldPosition.x;
            transformedY[i] = bounds.ypoints[i] + (int) worldPosition.y;
        }

        return new Polygon(transformedX, transformedY, bounds.npoints);
    }

    public Polygon getTransformedPolygon(Vec2 worldPosition) {
        int[] transformedX = new int[this.bounds.npoints];
        int[] transformedY = new int[this.bounds.npoints];

        for (int i = 0; i < this.bounds.npoints; i++) {
            transformedX[i] = this.bounds.xpoints[i] + worldPosition.getXInt();
            transformedY[i] = this.bounds.ypoints[i] + worldPosition.getYInt();
        }

        return new Polygon(transformedX, transformedY, this.bounds.npoints);
    }


    public boolean checkCollision(Hitbox other) {

        Area area1 = new Area(this.bounds);
        Area area2 = new Area(other.bounds);

        area1.intersect(area2);

        return !area1.isEmpty();

        //if(this.state == HitboxState.ACTIVE && other.state == HitboxState.ACTIVE){
           //return this.bounds.intersects(other.bounds);
        //}
        //return false;
    }

    public boolean bothSolid(Hitbox other) {
       return this.isSolid() && other.isSolid();
    }

    public boolean isSolid() {
        return this.state == HitboxState.ACTIVE && this.type == HitboxType.SOLID ;
    }

    public HitboxState getState() {
        return state;
    }

    public void setState(HitboxState state) {
        this.state = state;
    }

    public HitboxType getType() {
         return type;
    }

    public void setType(HitboxType type) {
        this.type = type;
    }

    /*public Rectangle getBounds() {
        return bounds;
    }*/

    public Polygon getBounds() {
        return bounds;
    }
    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }
}
