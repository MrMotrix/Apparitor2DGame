package main;
import entity.Character;
import temp.HitboxState;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.Polygon;
import java.awt.geom.Area;
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Character c) {

        Rectangle bounds = new Rectangle(
                c.hitbox.defaultBoundsX,
                c.hitbox.defaultBoundsY,
                c.hitbox.width,
                c.hitbox.height
        );

        int cLeftWorldX = c.worldPosition.getXInt() + bounds.x;
        int cRightWorldX = c.worldPosition.getXInt() + bounds.x + bounds.width;
        int cTopWorldY = c.worldPosition.getYInt() + bounds.y;
        int cBottomWorldY = c.worldPosition.getYInt() + bounds.y + bounds.height;

        int cLeftCol = cLeftWorldX / gp.tileSize;
        int cRightCol = cRightWorldX / gp.tileSize;
        int cTopRow= cTopWorldY / gp.tileSize;
        int cBottomRow = cBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (c.direction){
            case UP:
                cTopRow = (cTopWorldY - c.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cTopRow];
                tileNum2 = gp.tileManager.mapTileNum[cRightCol][cTopRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case DOWN:
                cBottomRow = (cBottomWorldY + c.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[cRightCol][cBottomRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case LEFT:
                cLeftCol = (cLeftWorldX - c.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cTopRow];
                tileNum2 = gp.tileManager.mapTileNum[cLeftCol][cBottomRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case RIGHT:
                cRightCol = (cRightWorldX + c.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[cRightCol][cTopRow];
                tileNum2 = gp.tileManager.mapTileNum[cRightCol][cBottomRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
        }
    }
    /*public int checkObejct(Character c, boolean player) {
        int index =999;

        for(int i =0; i< gp.obj.length;i++){

            if(gp.obj[i] == null) continue;

            c.hitbox.getBounds().x +=  c.worldPosition.getXInt();
            c.hitbox.getBounds().y += c.worldPosition.getYInt();

            gp.obj[i].hitbox.getBounds().x += gp.obj[i].worldPosition.getXInt();
            gp.obj[i].hitbox.getBounds().y += gp.obj[i].worldPosition.getYInt();

            switch(c.direction){
                case UP:
                    c.hitbox.getBounds().y -= c.speed;
                    if(c.hitbox.checkCollision(gp.obj[i].hitbox)) {
                        if (gp.obj[i].hitbox.getState() == HitboxState.ACTIVE)
                            c.hitbox.setState(HitboxState.ACTIVE);
                        if (player == true)
                            index = i;
                        System.out.println("object collision up");
                    }
                    break;
                case DOWN:
                    c.hitbox.getBounds().y += c.speed;
                    if(c.hitbox.checkCollision(gp.obj[i].hitbox)) {
                        if (gp.obj[i].hitbox.getState() == HitboxState.ACTIVE)
                            c.hitbox.setState(HitboxState.ACTIVE);
                        if (player == true)
                            index = i;
                        System.out.println("object collision down");
                    }
                    break;
                case LEFT:
                    c.hitbox.getBounds().x -= c.speed;
                    if(c.hitbox.checkCollision(gp.obj[i].hitbox)) {
                        if (gp.obj[i].hitbox.getState() == HitboxState.ACTIVE)
                            c.hitbox.setState(HitboxState.ACTIVE);
                        if (player == true)
                            index = i;
                        System.out.println("object collision left");
                    }
                    break;
                case RIGHT:
                    c.hitbox.getBounds().x += c.speed;
                    if(c.hitbox.checkCollision(gp.obj[i].hitbox)){
                        if(gp.obj[i].hitbox.getState() == HitboxState.ACTIVE)
                            c.hitbox.setState(HitboxState.ACTIVE);
                        if(player == true)
                            index = i;
                        System.out.println("object collision right");
                    }
                    break;
            }

            c.hitbox.resetToDefaultBounds();
            gp.obj[i].hitbox.resetToDefaultBounds();
        }

        return index;
    }*/

    public int checkObject(Character c, boolean player) {
        int index = 999; // Default value for no collision

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) continue;

            // Get polygons instead of bounds
            Polygon charPoly = c.hitbox.getPolygonAt(c.worldPosition);
            Polygon objPoly = gp.obj[i].hitbox.getPolygonAt(gp.obj[i].worldPosition);

            // Convert to Area for collision check
            Area charArea = new Area(charPoly);
            Area objArea = new Area(objPoly);

            // Check intersection
            charArea.intersect(objArea);
            if (!charArea.isEmpty()) { // Collision detected
                if (gp.obj[i].hitbox.getState() == HitboxState.ACTIVE) {
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                if (player) index = i;

                System.out.println("Object collision detected");
            }
        }
        return index;
    }
}
