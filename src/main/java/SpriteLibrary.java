import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    public Map<String, Map<String, BufferedImage>> sprites;

    SpriteLibrary() {
        sprites = new HashMap<String, Map<String,BufferedImage>>();
        sprites.put("world",new HashMap<String,BufferedImage>());
        sprites.put("characters",new HashMap<String,BufferedImage>());
        sprites.put("ui",new HashMap<String,BufferedImage>());
        sprites.put("effects",new HashMap<String,BufferedImage>());

        //ICI CHARGER LES SPRITES A PARTIR DE FICHIERS JSON AVEC LA METHODE STATIC "getSprites" DE SpriteJSONExtractor.
    }

    public void addSprites(String key, Map<String,BufferedImage>parsedSprites){
        if(parsedSprites.containsKey(key)){
            sprites.get(key).putAll(parsedSprites);
        }else{
            System.out.println("Key " + key + " not found");
        }
    }

    public BufferedImage getSprite(String key, String name){
        if(sprites.containsKey(key)){
            if(sprites.get(key).containsKey(name)){
                return sprites.get(key).get(name);
            }else{
                System.out.println("name " + name + " not found");
            }
        }else{
            System.out.println("Key " + key + " not found");
        }
        return null;
    }
}
