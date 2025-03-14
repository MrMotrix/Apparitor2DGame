import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Sprite {
    private List<BufferedImage> frames;
    private int currentFrame;
    private boolean animated;
    private int frameDelay; // Time between two frame
    private int frameTimer;

    // Constructor for static Sprite
    public Sprite(BufferedImage image) {
        this.frames = new ArrayList<>();
        this.frames.add(image);
        this.animated = false;
        this.currentFrame = 0;
    }

    // Constructor for animated Sprite
    public Sprite(List<BufferedImage> frames, int frameDelay) {
        this.frames = frames;
        this.animated = frames.size() > 1;
        this.currentFrame = 0;
        this.frameDelay = frameDelay;
        this.frameTimer = 0;
    }

    // Update animation
    public void update() {
        if (animated) {
            frameTimer++;
            if (frameTimer >= frameDelay) {
                frameTimer = 0;
                currentFrame = (currentFrame + 1) % frames.size();
            }
        }
    }

    // Get actual Sprite
    public BufferedImage getSprite() {
        return frames.get(currentFrame);
    }
}
