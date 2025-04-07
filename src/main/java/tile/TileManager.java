package tile;
import main.GamePanel;
import tools.SpriteLibrary;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];
    private final String basePath = Paths.get("src/main/resources").toAbsolutePath().toString()+"/";


    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.tiles = new Tile[54]; // A remplacer plus tard par une list
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("map");
    }

    public void getTileImage() {
        for(int i = 0; i < tiles.length; i++) {
            if (i ==0 || i == 52 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10  || i == 11 || i == 12 || i == 14 || i == 19  || i == 20 || i == 21 ){ // || i == 22 || i == 26 || i == 27 || i == 28 || i == 29 || i == 31 || i == 39 || i == 40 || i == 50) {
                tiles[i] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world", String.valueOf(i)), true);
            }
            else tiles[i] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world", String.valueOf(i)));
        }
    }

    public void loadMap(String mapName) {
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("maps/mapFinal.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;


            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();


                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int wordRow = 0;

        while(worldCol < gp.maxWorldCol && wordRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][wordRow];

            int worldX = worldCol *gp.tileSize;
            int worldY = wordRow *gp.tileSize;
            int screenX = worldX - gp.player.worldPosition.getXInt() + gp.player.screenPosition.getXInt();
            int screenY = worldY - gp.player.worldPosition.getYInt() + gp.player.screenPosition.getYInt();

            if(worldX + gp.tileSize > gp.player.worldPosition.getXInt() - gp.player.screenPosition.getXInt() &&
                    worldX - gp.tileSize < gp.player.worldPosition.getXInt() + gp.player.screenPosition.getXInt() &&
                    worldY + gp.tileSize > gp.player.worldPosition.getYInt() - gp.player.screenPosition.getYInt() &&
                    worldY - gp.tileSize < gp.player.worldPosition.getYInt() + gp.player.screenPosition.getYInt()) {

                g2.drawImage(tiles[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                wordRow++;
            }
        }
    }
}
