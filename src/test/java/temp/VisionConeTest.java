package temp;

import main.GamePanel;
import math.Vec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class VisionConeTest {

    private VisionCone visionCone;
    private GamePanel gamePanel;
    private Vec2 worldPos;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        worldPos = new Vec2(100, 100);

        visionCone = new VisionCone(
                0, 0, 20, 20,
                HitboxState.ACTIVE,
                HitboxType.SOLID,
                Color.RED, Color.BLUE,
                45,    // initial angle
                3,     // view distance in tiles
                90,    // angle range
                5,     // rotation per frame
                gamePanel,
                worldPos
        );
    }

    @Test
    void testUpdateChangesBounds() {
        Polygon before = visionCone.getBounds();
        visionCone.update(120, 120);
        Polygon after = visionCone.getBounds();

        assertNotNull(after);
        assertEquals(3, after.npoints); // It's a triangle
        assertFalse(before.equals(after)); // Bounds should have changed
    }

    @Test
    void testSetWorldPosition() {
        visionCone.setWorldPosition(200, 300);
        assertEquals(200, worldPos.x);
        assertEquals(300, worldPos.y);
    }

    @Test
    void testRotationDoesNotCrash() {
        // Run several frames to simulate animation
        for (int i = 0; i < 50; i++) {
            visionCone.update(100 + i, 100 + i);
        }
        assertNotNull(visionCone.getBounds());
    }
}
