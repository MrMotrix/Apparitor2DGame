public class Vec2 {
    public int x, y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void add(Vec2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
