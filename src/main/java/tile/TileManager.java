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
        this.tiles = new Tile[16]; // A remplacer plus tard par une list
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("map");
    }

    public void getTileImage() {
        tiles[0] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","0"),true);
        tiles[1] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","1"));
        tiles[2] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","2"));
        tiles[3] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","3"));
        tiles[4] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","4"));
        tiles[5] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","5"));
        tiles[6] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","6"));
        tiles[7] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","7"));
        tiles[8] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","8"));
        tiles[9] = new Tile(SpriteLibrary.getInstance(basePath).getSprite("world","9"));
    }

    public void loadMap(String mapName) {
        try{
            InputStream is = getClass().getClassLoader().getResourceAsStream("maps/map.txt");
            System.out.println(basePath+"maps/"+mapName+".txt");
            System.out.println(is.toString());
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

            g2.drawImage(tiles[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                wordRow++;
            }
        }
    }
}
