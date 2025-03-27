package main;
import entity.Character;
import temp.HitboxState;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Character c) {
        int cLeftWorldX = c.worldPosition.getXInt() + c.hitbox.getBounds().x;
        int cRightWorldX = c.worldPosition.getXInt() + c.hitbox.getBounds().x + c.hitbox.getBounds().width;
        int cTopWorldY = c.worldPosition.getYInt() + c.hitbox.getBounds().y;
        int cBottomWorldY = c.worldPosition.getYInt() + c.hitbox.getBounds().y + c.hitbox.getBounds().height;

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
}
