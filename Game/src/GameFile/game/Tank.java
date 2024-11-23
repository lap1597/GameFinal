package GameFile.game;

import GameFile.GameConstants;
import GameFile.utils.AssetManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {
    private float screenX, screenY;
    private float x;
    private float y;

    private boolean isFacingLeft = false;
    private boolean isShooting = false;
    private float R = 1;


    private BufferedImage img;
    private List<BufferedImage> currentAnimation;
    private List<BufferedImage> walkingAnimation = AssetManager.getAnimation("dogWalk");
    private List<BufferedImage> shootAnimation = AssetManager.getAnimation(("dogShoot"));
    private int currentFrame = 0; // Current frame index in the animation
    private int frameCounter = 0; // Counter to control frame delay
    private int frameDelay = 10; // Number of game ticks between frame changes
    //For shooting
    private int shootTimer = 0; // Timer for shooting animation
    private int shootDuration = 100; // Duration (in game ticks) for the shooting animation
    private int shootFrameDelay = 10; // Control frame speed for shooting animation

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;


    Tank(float x, float y, BufferedImage standing) {
        super((int) x, (int) y, standing);

        this.x = x;
        this.y = y;
        //default
        img = standing;
        currentAnimation = List.of(standing);

    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }


    void update() {
        boolean isMoving = false;

        if (isShooting) {
            if (currentAnimation != shootAnimation) {
                currentAnimation = shootAnimation;
                currentFrame = 0; // Reset frame index
            }
        } else {
            if (UpPressed) {
                moveUp();
                isMoving = true;
            }
            if (DownPressed) {
                moveDown();
                isMoving = true;
            }
            if (LeftPressed) {
                moveLeft();
                isMoving = true;
            }
            if (RightPressed) {
                moveRight();
                isMoving = true;
            }

            if (isMoving) {
                if (currentAnimation != walkingAnimation) {
                    currentAnimation = walkingAnimation;
                    currentFrame = 0; // Reset frame index
                }
            } else {
                if (currentAnimation != null && currentAnimation != List.of(img)) {
                    currentAnimation = List.of(img); // Default to standing image
                    currentFrame = 0; // Reset frame index
                }
            }
        }

        centerScreen();
        updateAnimationFrame();
    }

    private void updateAnimationFrame() {
        if (currentAnimation != null && !currentAnimation.isEmpty()) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrame = (currentFrame + 1) % currentAnimation.size();

                if (isShooting && currentFrame == 0) {
                    isShooting = false; // End shooting animation
                }
            }
        }
    }

//    void update() {
//        boolean isMoving = false;
//        if (isShooting) {
//            currentAnimation = shootAnimation;
////            isMoving = false; // Prioritize shooting animation over movement
//        } else {
//            if (this.UpPressed) {
//                this.moveUp();
//                isMoving = true;
//            }
//            if (this.DownPressed) {
//                this.moveDown();
//                isMoving = true;
//            }
//            if (this.LeftPressed) {
//                this.moveLeft();
//                isMoving = true;
//            }
//            if (this.RightPressed) {
//                this.moveRight();
//                isMoving = true;
//            }
//
//
//            if (isShooting) {
//                currentAnimation = shootAnimation;
//            } else if (isMoving) {
//                currentAnimation = walkingAnimation;
//            }else {
//                currentAnimation = null; // No animation, use the standing image
//            }
//        }
//
//
//
//
//        centerScreen();
//
//        // Update animation frame
//
//        if (currentAnimation != null) {
//            frameCounter++;
//            if (frameCounter >= frameDelay) {
//                frameCounter = 0;
//                currentFrame = (currentFrame + 1) % currentAnimation.size();
//
//                // Reset shooting animation
//                if (isShooting && currentFrame == 0) {
//                    isShooting = false; // End shooting animation
//                }
//            }
//        }
//    }

//    void update() {
//        boolean isMoving = false;
//
//        // Movement logic
//        if (UpPressed) {
//            moveUp();
//            isMoving = true;
//        }
//        if (DownPressed) {
//            moveDown();
//            isMoving = true;
//        }
//        if (LeftPressed) {
//            moveLeft();
//            isMoving = true;
//        }
//        if (RightPressed) {
//            moveRight();
//            isMoving = true;
//        }
//
//        // Handle animation based on state
//        if (isShooting) {
//            shootTimer--;
//
//            // Play shooting animation
//            frameCounter++;
//            if (frameCounter >= shootFrameDelay) {
//                frameCounter = 0;
//                currentFrame = (currentFrame + 1) % shootAnimation.size();
//            }
//
//            if (shootTimer <= 0) {
//                isShooting = false; // End shooting state after the timer
//            }
//
//            // Ensure shooting animation overrides others
//            currentAnimation = shootAnimation;
//        } else if (isMoving) {
//            // If moving and not shooting, play walking animation
//            frameCounter++;
//            if (frameCounter >= frameDelay) {
//                frameCounter = 0;
//                currentFrame = (currentFrame + 1) % walkingAnimation.size();
//            }
//            currentAnimation = walkingAnimation;
//        } else {
//            // If not moving or shooting, play standing animation
//            frameCounter++;
//            if (frameCounter >= frameDelay) {
//                frameCounter = 0;
//                currentFrame = (currentFrame + 1) % standingAnimation.size();
//            }
//            currentAnimation = standingAnimation;
//        }
//
//        centerScreen(); // Keep the screen centered on the tank
//    }


    private void centerScreen() {
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT / 2f;

        // lower bound check
        if (this.screenX < 0) screenX = 0;
        if (this.screenY < 0) screenY = 0;

        //upper bound check
        if (this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f) { // going abobe max, set back to max
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }
        if (this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    public float getScreenX() {
        return this.screenX;
    }

    public float getScreenY() {
        return this.screenY;
    }

    private void moveLeft() {
        isFacingLeft = true;
        x -= R;
        checkBorder();
    }

    private void moveRight() {
        isFacingLeft = false;
        x += R;
        checkBorder();
    }

    private void moveDown() {
        y += R;
        checkBorder();
    }

    private void moveUp() {
        y -= R;
        checkBorder();
    }

    public void toggleShootPress() {
        isShooting = true;
        shootTimer = shootDuration; // Start the timer

        currentAnimation = shootAnimation;
        currentFrame = 0; // Start shooting animation from the beginning

    }

    public void unToggleShootPressed() {
//        isShooting = false;
//        currentAnimation = standingAnimation;
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 80) {
            x = GameConstants.WORLD_WIDTH - 80;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 88) {
            y = GameConstants.WORLD_HEIGHT - 88;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    //    void drawImage(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        if (currentAnimation != null && !currentAnimation.isEmpty()) {
//            // Draw the current frame of the animation
//            BufferedImage currentImage = currentAnimation.get(currentFrame);
//            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
//
//            if (isFacingLeft) {
//                rotation.scale(-1, 1);
//                rotation.translate(-currentImage.getWidth(), 0);
//            }
//
//            g2d.drawImage(currentImage, rotation, null);
//        } else {
//            // Draw the standing image when no animation is active
//            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
//
//            if (isFacingLeft) {
//                rotation.scale(-1, 1);
//                rotation.translate(-img.getWidth(), 0);
//            }
//
//            g2d.drawImage(img, rotation, null);
//        }
//    }
    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double scaleFactor = 1.5;
        BufferedImage currentImage = (currentAnimation != null && !currentAnimation.isEmpty())
                ? currentAnimation.get(currentFrame)
                : img;
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        if (isFacingLeft) {
            rotation.scale(-scaleFactor , scaleFactor );
            rotation.translate(-currentImage.getWidth(), 0);
        }else{
            rotation.scale(scaleFactor, scaleFactor);
        }
        System.out.println("x = " + x + ", y = " + y);
        g2d.drawImage(currentImage, rotation, null);
    }


    @Override
    public void handleCollision(GameObject o) {

        if (o instanceof Wall) {
            // Example collision resolution: stop movement
            System.out.println("Tank collided with a wall!");
            // Reset position or stop the tank based on collision resolution
            if (this.UpPressed) this.y += R; // Undo movement up
            if (this.DownPressed) this.y -= R; // Undo movement down
            if (this.LeftPressed) this.x += R; // Undo movement left
            if (this.RightPressed) this.x -= R; // Undo movement right
//            this.x -=R;
//            this.y -= R;
        }
    }


    @Override
    public Rectangle getHitbox() {
        // Assuming `currentImage` is the image representing the current animation frame
        BufferedImage currentImage = (currentAnimation != null && !currentAnimation.isEmpty()) ? currentAnimation.get(currentFrame) : img;

        // Return the hitbox considering the width and height of the image
        return new Rectangle((int) x, (int) y, (int) (currentImage.getWidth()), (int) (currentImage.getHeight()));
    }
}