public class Vec2 {
    public double x, y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double newX, double newY) {
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
        double x1 = this.x;
        double y1 = this.y;

        x = (Math.cos(angleRad*x1) - Math.sin(angleRad*y1));
        y = (Math.sin(angleRad*x1) + Math.cos(angleRad*y1));
    }

    public void normalize(){
        double magnitude = getMagnitude();
        if(magnitude == 0) return;
        this.x /= magnitude;
        this.y /= magnitude;
    }

    public void shiftByAngleAndMagnitude(double angleRad, double distance) {
        this.rotate(angleRad);
        x = (x + distance * Math.cos(angleRad));
        y = (y + distance * Math.sin(angleRad));
    }

    public int getXInt(){
        return (int)x;
    }

    public int getYInt(){
        return (int)y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
