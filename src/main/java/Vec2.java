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

    public double getMagnitude() {
        return Math.sqrt((Math.pow(this.x, 2) + Math.pow(this.y, 2)));
    }

    public double getAngleRad() {
        return Math.atan2(y, x);
    }

    public void scale(double scale) {
        this.x *= scale;
        this.y *= scale;
    }

    public void rotate(double angleRad) {
        int x1 = this.x;
        int y1 = this.y;

        x = (int)(Math.cos(angleRad*x1) - Math.sin(angleRad*y1));
        y = (int)(Math.sin(angleRad*x1) + Math.cos(angleRad*y1));
    }

    public void normalize(){
        double magnitude = getMagnitude();

        this.x /= magnitude;
        this.y /= magnitude;
    }

    public void shiftByAngleAndMagnitude(double angleRad, double distance) {
        this.rotate(angleRad);
        x = (int)(x + distance * Math.cos(angleRad));
        y = (int)(y + distance * Math.sin(angleRad));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
