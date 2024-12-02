package GameFile.game;

import GameFile.utils.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    private float x, y;
    private List<BufferedImage> frames;
    private long delay = 30; // Delay between frames in milliseconds
    private long lastFrameTime = 0;
    private int currentFrame = 0;
    private boolean running = true;

    public Animation(float x, float y, List<BufferedImage> frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.running = true;
        this.currentFrame = 0;
    }


    // Update the animation frames
    public void update() {
        if (!running || frames == null || frames.isEmpty()) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= delay) {
            currentFrame++;

            // Stop the animation when it reaches the end
            if (currentFrame >= frames.size()) {
                currentFrame = frames.size() - 1; // Clamp to the last frame
                running = false;
            }

            lastFrameTime = currentTime;
        }
    }

    // Draw the current frame
    public void draw(Graphics g) {
        if (frames != null && !frames.isEmpty() && running) {
            BufferedImage currentFrameImage = frames.get(Math.min(currentFrame, frames.size() - 1));
            g.drawImage(currentFrameImage, (int) x, (int) y, null);
        } else if (frames == null || frames.isEmpty()) {
            System.err.println("No frames available to draw.");
        }
    }

    // Set a new animation
    public void setAnimation(List<BufferedImage> newFrames) {
        if (newFrames == null || newFrames.isEmpty()) {
            throw new IllegalArgumentException("Animation frames cannot be null or empty.");
        }
        this.frames = newFrames;
        this.currentFrame = 0;
        this.running = true;
        this.lastFrameTime = System.currentTimeMillis();
    }

    // Check if the animation is running
    public boolean isRunning() {
        return running;
    }

    // Reset the animation
    public void reset() {
        this.currentFrame = 0;
        this.running = true;
        this.lastFrameTime = System.currentTimeMillis();
    }

    // Set the position of the animation
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Set the frame delay
    public void setDelay(long delay) {
        this.delay = delay;
    }
}
