package math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Vec2Test {

    @Test
    void testConstructor() {
        Vec2 v = new Vec2(3, 4);
        assertEquals(3, v.x);
        assertEquals(4, v.y);
    }

    @Test
    void testSet() {
        Vec2 v = new Vec2(1, 2);
        v.set(5, 6);
        assertEquals(5, v.x);
        assertEquals(6, v.y);
    }

    @Test
    void testAdd() {
        Vec2 v1 = new Vec2(1, 2);
        Vec2 v2 = new Vec2(3, 4);
        v1.add(v2);
        assertEquals(4, v1.x);
        assertEquals(6, v1.y);
    }

    @Test
    void testGetMagnitude() {
        Vec2 v = new Vec2(3, 4);
        assertEquals(5, v.getMagnitude(), 0.0001);
    }

    @Test
    void testGetAngleRad() {
        Vec2 v = new Vec2(1, 1);
        double angle = v.getAngleRad();
        assertEquals(Math.PI / 4, angle, 0.0001);  // 45 degrees, which is pi/4 radians
    }

    @Test
    void testScale() {
        Vec2 v = new Vec2(2, 3);
        v.scale(2);
        assertEquals(4, v.x);
        assertEquals(6, v.y);
    }

    @Test
    void testNormalize() {
        Vec2 v = new Vec2(3, 4);
        v.normalize();
        assertEquals(1, v.getMagnitude(), 0.0001);  // Should be a unit vector after normalize
    }

    @Test
    void testShiftByAngleAndMagnitude() {
        Vec2 v = new Vec2(1, 1);
        v.shiftByAngleAndMagnitude(Math.PI / 4, 5);  // Rotate by 45 degrees and shift by 5 units

        double expectedX = 1 + 5 * Math.cos(Math.PI / 4);  // Expected X component after the shift
        double expectedY = 1 + 5 * Math.sin(Math.PI / 4);  // Expected Y component after the shift

        // Use an appropriate tolerance to compare the values
        assertEquals(expectedX, v.x);
        assertEquals(expectedY, v.y);
    }

    @Test
    void testRotate() {
        Vec2 v = new Vec2(1, 0);
        v.rotate(Math.PI / 2);  // Rotate 90 degrees
        assertEquals(0, v.x, 0.0001);
        assertEquals(1, v.y, 0.0001);
    }

    @Test
    void testToString() {
        Vec2 v = new Vec2(3, 4);
        assertEquals("(3.0, 4.0)", v.toString());
    }
}
