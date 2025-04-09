package tile;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testTileWithoutCollision() {
        BufferedImage dummyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Tile tile = new Tile(dummyImage);

        assertEquals(dummyImage, tile.image);
        assertFalse(tile.collision);
    }

    @Test
    void testTileWithCollision() {
        BufferedImage dummyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Tile tile = new Tile(dummyImage, true);

        assertEquals(dummyImage, tile.image);
        assertTrue(tile.collision);
    }
}
