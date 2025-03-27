package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;

public class SpriteJSONExtractor {

    public static Map<String, BufferedImage> getSprites(String jsonFilePath, String spriteSheetFilePath) throws IOException {

        Map<String, Map<String, Object>> jsonMap = null;
        BufferedImage spriteSheet = null;
        File JSON_SOURCE = new File(jsonFilePath);

        try {
            jsonMap = new ObjectMapper().readValue(JSON_SOURCE, HashMap.class);
            spriteSheet = ImageIO.read(new File(spriteSheetFilePath));
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

        Map<String, BufferedImage> sprites = new HashMap<>();

        for (String key : jsonMap.keySet()) {
            Map<String, Object> spriteInfo = (Map<String, Object>) jsonMap.get(key).get("sprite");

            int x = (int) spriteInfo.get("x");
            int y = (int) spriteInfo.get("y");
            int w = (int) spriteInfo.get("w");
            int h = (int) spriteInfo.get("h");

            BufferedImage sprite = spriteSheet.getSubimage(x, y, w, h);
            sprites.put(key, sprite);
        }

        return sprites;
    }
}
