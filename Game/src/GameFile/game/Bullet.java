package GameFile.game;

import GameFile.GameConstants;
import GameFile.utils.AssetManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.List;

public class Bullet extends GameObject implements Tracking{
    private float x;
    private float y;
    private float angle;
    private float speed = 2;
    private int ownerID;
    private boolean stopped= false;
    private boolean exploding = false;

    private List<BufferedImage> animationFrames;


   // private List<BufferedImage> animationFrames; // List of images for animation
    private Animation explosionAnimation;
    private int currentFrameIndex = 0;
    private int animationSpeed = 10; // Frames to wait before switching to the next image
    private int frameCounter = 0;
    public Bullet(int x, int y, List<BufferedImage> animationFrames) {
        super(x, y,  animationFrames);
        this.x = x;
        this.y = y;
        this.animationFrames = animationFrames;
    }

    // Update the position of the bullet based on its angle
    public void update(GameWorld gameWorld) {
        if(!exploding) {
            x += speed * Math.cos(Math.toRadians(angle));
            y += speed * Math.sin(Math.toRadians(angle));
        }else{
            if (explosionAnimation != null) {
                explosionAnimation.update();

            }
        }


        updateAnimation();

    }
    private void updateAnimation() {
        if (animationFrames.size() > 1) {
            frameCounter++;
            if (frameCounter >= animationSpeed) {
                frameCounter = 0; // Reset counter
                currentFrameIndex = (currentFrameIndex + 1) % animationFrames.size(); // Loop through frames
            }
        }
    }



    public void setOwner(int id) {
        this.ownerID = id;
    }

    public int getOwner() {
        return this.ownerID;
    }

    // Set the initial shooting position and angle
    public void shoot( float angle) {

        this.angle = angle;

    }

    @Override
    public void handleCollision(GameObject o) {
        if (!exploding && (o instanceof Wall || o instanceof Breakable || o instanceof Player)) {
            this.stopped = true;
            this.exploding = true;
            this.speed = 0;

            // Initialize explosion animation
            explosionAnimation = new Animation(x, y);

            if (o instanceof Breakable) {
                o.setHasCollided(); // Handle breakable object-specific collision
            }
        }
    }
    @Override
    public Rectangle getHitbox() {
        if (!exploding) {
            BufferedImage currentImage = animationFrames.get(0); // Use the first frame for hitbox
            return new Rectangle((int) x, (int) y, currentImage.getWidth() / 2, currentImage.getHeight());
        }
        return new Rectangle(); // No hitbox during explosi
    }

    public void draw(Graphics g) {
        if (!exploding) {
            // Draw the bullet animation
            if (animationFrames != null && !animationFrames.isEmpty()) {
                Graphics2D g2d = (Graphics2D) g;
                BufferedImage currentFrame = animationFrames.get(currentFrameIndex); // Use the current frame index
                AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
                transform.rotate(Math.toRadians(angle), currentFrame.getWidth() / 2.0, currentFrame.getHeight() / 2.0);
                g2d.drawImage(currentFrame, transform, null);
            } else {
                System.err.println("No animation frames available for the bullet.");
            }
        } else if (explosionAnimation != null) {
            // Draw the explosion animation
            explosionAnimation.draw(g);
        }
    }



}
