package main;
import entity.Character;
import math.Vec2;
import temp.Direction;
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
                cTopRow = ((cTopWorldY - c.speed)/gp.tileSize);
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cTopRow];
                tileNum2 = gp.tileManager.mapTileNum[cRightCol][cTopRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case DOWN:
                cBottomRow = ((cBottomWorldY + c.speed)/gp.tileSize);
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[cRightCol][cBottomRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case LEFT:
                cLeftCol = ((cLeftWorldX - c.speed)/gp.tileSize);
                tileNum1 = gp.tileManager.mapTileNum[cLeftCol][cTopRow];
                tileNum2 = gp.tileManager.mapTileNum[cLeftCol][cBottomRow];

                if(gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true){
                    c.hitbox.setState(HitboxState.ACTIVE);
                }
                break;
            case RIGHT:
                cRightCol = ((cRightWorldX + c.speed)/gp.tileSize);
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

        Vec2 cNextWorldPosition = new Vec2(c.worldPosition);
        if(c.hitbox.getState() == HitboxState.DISABLED) {

            switch (c.direction) {
                case UP:
                    cNextWorldPosition.y -= c.speed;
                    break;
                case DOWN:
                    cNextWorldPosition.y += c.speed;
                    break;
                case LEFT:
                    cNextWorldPosition.x -= c.speed;
                    break;
                case RIGHT:
                    cNextWorldPosition.x += c.speed;
                    break;

            }
        }

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) continue;

            // Get polygons instead of bounds
            Polygon charPoly = c.hitbox.getPolygonAt(cNextWorldPosition);
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

    public boolean checkApparitorsCollision(Character c, boolean player) {
        boolean collision = false;
        Vec2 cNextWorldPosition = new Vec2(c.worldPosition);
        if(c.hitbox.getState() == HitboxState.DISABLED) {
            switch (c.direction) {
                case UP:
                    cNextWorldPosition.y -= c.speed;
                    break;
                case DOWN:
                    cNextWorldPosition.y += c.speed;
                    break;
                case LEFT:
                    cNextWorldPosition.x -= c.speed;
                    break;
                case RIGHT:
                    cNextWorldPosition.x += c.speed;
                    break;

            }
        }

        for (int i = 0; i < gp.apparitors.length; i++) {
            if (gp.apparitors[i] == null) continue;
            // Get polygons instead of bounds
            Polygon charPoly = c.hitbox.getPolygonAt(cNextWorldPosition);
            Polygon apparitorPoly = gp.apparitors[i].hitbox.getPolygonAt(gp.apparitors[i].worldPosition);
            Polygon apparitorDetectionZonePoly = gp.apparitors[i].detectionZone.getPolygonAt(gp.apparitors[i].worldPosition);

            // Convert to Area for collision check
            Area charArea = new Area(charPoly);
            Area apparitorArea = new Area(apparitorPoly);
            Area apparitorDetectionZoneArea = new Area(apparitorDetectionZonePoly);


            // Check intersection
            apparitorArea.intersect(charArea);
            apparitorDetectionZoneArea.intersect(charArea);

            if (!apparitorArea.isEmpty()) { // Collision detected
                c.hitbox.setState(HitboxState.ACTIVE);
                gp.apparitors[i].onCollision();
                collision = true;
            }

            if(!apparitorDetectionZoneArea.isEmpty()){
                gp.apparitors[i].onCollision();
                collision = true;
            }
        }
        return collision;
    }

    public boolean checkCamerasCollision(Character c, boolean player) {
        boolean collision = false;
        Vec2 cNextWorldPosition = new Vec2(c.worldPosition);
        if(c.hitbox.getState() == HitboxState.DISABLED) {
            switch (c.direction) {
                case UP:
                    cNextWorldPosition.y -= c.speed;
                    break;
                case DOWN:
                    cNextWorldPosition.y += c.speed;
                    break;
                case LEFT:
                    cNextWorldPosition.x -= c.speed;
                    break;
                case RIGHT:
                    cNextWorldPosition.x += c.speed;
                    break;

            }
        }

        for (int i = 0; i < gp.cameras.length; i++) {
            if (gp.cameras[i] == null) continue;
            // Get polygons instead of bounds
            Polygon charPoly = c.hitbox.getPolygonAt(cNextWorldPosition);
            Polygon cameraPoly = gp.cameras[i].hitbox.getPolygonAt(gp.cameras[i].worldPosition);
            Polygon cameraDetectionZonePoly = gp.cameras[i].detectionZone.getPolygonAt(gp.cameras[i].worldPosition);

            // Convert to Area for collision check
            Area charArea = new Area(charPoly);
            Area cameraArea = new Area(cameraPoly);
            Area cameraDetectionZoneArea = new Area(cameraDetectionZonePoly);


            // Check intersection
            cameraArea.intersect(charArea);
            cameraDetectionZoneArea.intersect(charArea);

            if (!cameraArea.isEmpty()) { // Collision detected
                c.hitbox.setState(HitboxState.ACTIVE);
                gp.cameras[i].onCollision();
                collision = true;
            }

            if(!cameraDetectionZoneArea.isEmpty()){
                gp.cameras[i].onCollision();
                collision = true;
            }
        }
        return collision;
    }

    public boolean checkPlayerCollision(Character c){
        boolean collision = false;
        Vec2 cNextWorldPosition = new Vec2(c.worldPosition);
        if(c.hitbox.getState() == HitboxState.DISABLED) {
            switch (c.direction) {
                case UP:
                    cNextWorldPosition.y -= c.speed;
                    break;
                case DOWN:
                    cNextWorldPosition.y += c.speed;
                    break;
                case LEFT:
                    cNextWorldPosition.x -= c.speed;
                    break;
                case RIGHT:
                    cNextWorldPosition.x += c.speed;
                    break;

            }
        }

        // Get polygons instead of bounds
        Polygon charPoly = c.hitbox.getPolygonAt(cNextWorldPosition);
        Polygon playerPoly = gp.player.hitbox.getPolygonAt(gp.player.worldPosition);

        // Convert to Area for collision check
        Area charArea = new Area(charPoly);
        Area playerArea = new Area(playerPoly);


        // Check intersection
        playerArea.intersect(charArea);

        if (!playerArea.isEmpty()) { // Collision detected
            c.hitbox.setState(HitboxState.ACTIVE);
            collision = true;
        }

        return collision;
    }
}
