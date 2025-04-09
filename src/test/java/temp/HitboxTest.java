package temp;

import math.Vec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.Polygon;

import static org.junit.jupiter.api.Assertions.*;

class HitboxTest {

    private Hitbox hitbox;
    private Hitbox other;

    @BeforeEach
    void setUp() {
        hitbox = new Hitbox(0, 0, 10, 10, HitboxState.ACTIVE, HitboxType.SOLID, Color.RED, Color.BLUE);
        hitbox.resetToDefaultBounds();
        other = new Hitbox(5, 5, 10, 10, HitboxState.ACTIVE, HitboxType.SOLID, Color.GREEN, Color.YELLOW);
        other.resetToDefaultBounds();
    }

    @Test
    void testCollisionDetection() {
        assertTrue(hitbox.checkCollision(other));
    }

    @Test
    void testNoCollisionWhenSeparated() {
        Hitbox farHitbox = new Hitbox(100, 100, 10, 10, HitboxState.ACTIVE, HitboxType.SOLID, Color.BLACK, Color.WHITE);
        farHitbox.resetToDefaultBounds();
        assertFalse(hitbox.checkCollision(farHitbox));
    }

    @Test
    void testIsSolid() {
        assertTrue(hitbox.isSolid());
    }

    @Test
    void testBothSolid() {
        assertTrue(hitbox.bothSolid(other));
    }

    @Test
    void testGetTransformedPolygon() {
        Vec2 position = new Vec2(5, 5);
        Polygon poly = hitbox.getTransformedPolygon(position);
        assertEquals(4, poly.npoints);
    }

    @Test
    void testGetPolygonAt() {
        Vec2 position = new Vec2(10, 10);
        Polygon poly = hitbox.getPolygonAt(position);
        assertEquals(4, poly.npoints);
    }
}
