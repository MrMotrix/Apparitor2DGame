package sprite;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    @Test
    void testStaticSprite() {
        // On crée une image dummy
        BufferedImage dummyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Création du sprite statique
        Sprite sprite = new Sprite(dummyImage);

        // Vérification : la seule image dans les frames doit être celle que l'on a créée
        assertEquals(dummyImage, sprite.getSprite());
    }

    @Test
    void testAnimatedSprite() {
        // Créer une liste d'images dummy pour l'animation
        BufferedImage dummyImage1 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        BufferedImage dummyImage2 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        List<BufferedImage> frames = Arrays.asList(dummyImage1, dummyImage2);

        // Création du sprite animé
        Sprite sprite = new Sprite(frames, 1);

        // Vérification que le sprite est animé
        assertTrue(sprite.getSprite() == dummyImage1 || sprite.getSprite() == dummyImage2);

        // Mise à jour de l'animation
        sprite.update();

        // Après une mise à jour, on vérifie que l'animation change de frame
        assertEquals(dummyImage2, sprite.getSprite());
    }

    @Test
    void testAnimationLoop() {
        // Créer une liste d'images dummy
        BufferedImage dummyImage1 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        BufferedImage dummyImage2 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        List<BufferedImage> frames = Arrays.asList(dummyImage1, dummyImage2);

        // Création du sprite animé
        Sprite sprite = new Sprite(frames, 1);  // 1 frame per update

        // Tester plusieurs mises à jour de l'animation
        sprite.update();
        assertEquals(dummyImage2, sprite.getSprite());

        sprite.update();  // Deuxième update -> doit revenir à la première frame
        assertEquals(dummyImage1, sprite.getSprite());
    }
}
