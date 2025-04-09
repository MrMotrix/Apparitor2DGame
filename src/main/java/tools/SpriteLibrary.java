package tools;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private static SpriteLibrary instance;  // The only instance
    private final Map<String, Map<String, BufferedImage>> sprites;

    private SpriteLibrary(String basePath) {  // Private constructor
        this.sprites = new HashMap<>();
        sprites.put("world", new HashMap<>());
        sprites.put("characters", new HashMap<>());
        sprites.put("ui", new HashMap<>());
        sprites.put("effects", new HashMap<>());

        try {
            addSprites(
                    "characters",
                    SpriteJSONExtractor.getSprites(
                            basePath + "perso/player-moov.json",
                            basePath + "perso/PlayerMoov-sheet.png"
                    )
            );
            addSprites(
                    "characters",
                    SpriteJSONExtractor.getSprites(
                            basePath + "perso/player-moov.json",
                            basePath + "perso/PlayerMoov-sheet.png"
                    )
            );
            addSprites(
                    "world",
                    SpriteJSONExtractor.getSprites(
                            basePath+ "tiles/test.json",
                            basePath+ "tiles/tilesetFinal.png"
                    )
            );

            addSprites(
                    "world",
                    SpriteJSONExtractor.getSprites(
                            basePath+"objects/key.json",
                            basePath+"objects/key.png"
                    )
            );

            addSprites(
                    "world",
                    SpriteJSONExtractor.getSprites(
                            basePath+"objects/camera.json",
                            basePath+"objects/camera.png"
                    )
            );

            addSprites(
                    "ui",
                    SpriteJSONExtractor.getSprites(
                            basePath+"ui/hearth-sprite.json",
                            basePath+"ui/hearth-sprite-sheet.png"
                    )
            );

            addSprites(
                    "world",
                    SpriteJSONExtractor.getSprites(
                            basePath+"objects/Doormats.json",
                            basePath+"objects/Doormats.png"
                    )
            );

        } catch (IOException e) {
            System.err.println("Error loading sprites: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Static method to get the Singleton instance
    public static SpriteLibrary getInstance(String basePath) {
        if (instance == null) {
            instance = new SpriteLibrary(basePath);
        }
        return instance;
    }

    public boolean addSprites(String key, Map<String, BufferedImage> parsedSprites) {
        if (sprites.containsKey(key)) {
            sprites.get(key).putAll(parsedSprites);
            return true;
        } else {
            System.err.println("Key '" + key + "' not found.");
            return false;
        }
    }

    public BufferedImage getSprite(String category, String name) {
        if (sprites.containsKey(category)) {
            if (sprites.get(category).containsKey(name)) {
                return sprites.get(category).get(name);
            } else {
                System.err.println("sprite.Sprite '" + name + "' not found in '" + category + "'.");
            }
        } else {
            System.err.println("Category '" + category + "' does not exist.");
        }
        return null;
    }
}
