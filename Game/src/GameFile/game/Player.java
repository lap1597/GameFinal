package GameFile.game;

import GameFile.GameConstants;
import GameFile.utils.AssetManager;
import GameFile.game.Life;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject implements Tracking {
    private float screenX, screenY;
    private float x, y;
    private float resPawnX, resPawnY;
    private GameWorld gw;
    private boolean isFacingLeft = false;
    private boolean isShooting = false;
    private float R = 0.5f;
    private float angle = 0; // Angle in degrees

    private BufferedImage img;
    private List<BufferedImage> currentAnimation;
    private List<BufferedImage> standingAnimation;
    private List<BufferedImage> walkingAnimation;
    private List<BufferedImage> shootAnimation;
    private List<BufferedImage> dieAnimation;

    private int currentFrame = 0;
    private int frameCounter = 0;
    private int frameDelay = 15;


    // Shooting control
    private int shootTimer = 15;
    private int shootDuration = 10;
    private int shootFrameDelay = 15;


    private int playerId;
    private int health = 100;
    private float speed;
    private Bullet bullet;
    private int damage;
    private Life life;

    private boolean UpPressed, DownPressed, RightPressed, LeftPressed;

    Player(int type,float x, float y, List<BufferedImage> standing) {
        super((int) x, (int) y, standing);
        this.x = x;
        this.y = y;
        this.resPawnX = x;
        this.resPawnY = y;
        standingAnimation = standing;
        checkType();

        playerId = type;
        checkType();

        this.speed = 0.5f;
        this.damage =5;
        this.life = new Life(this, health,50,10);


    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth( int health){
        this.health = health;
    }
    public void resetHealth(){
        this.health = 100;
    }
    public void upgradeHealth(){
        this.health += 25;
    }
    public float getSpeed(){
        return this.speed;
    }
    public void setSpeed( float speed){
        this.speed = speed;
    }
    public void resetSpeed(){
        this.speed = 0.5f;
    }
    public void upgradeSpeed(){
        this.speed +=0.5f;
    }
    public void upgradeDamage(){
        this.damage +=10;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }

    public Life getLife(){
        return this.life;
    }
    private void checkType(){
        if( playerId == 1){
            walkingAnimation = AssetManager.getAnimation("dogWalk");
            shootAnimation = AssetManager.getAnimation("dogShoot");
            dieAnimation = AssetManager.getAnimation("dogDeath");

        }else{
            walkingAnimation = AssetManager.getAnimation("catWalk");
            shootAnimation = AssetManager.getAnimation("catShoot");
            dieAnimation = AssetManager.getAnimation("catDeath");

        }
    }
    private int safeShootX() {
        return (int) (x + this.currentAnimation.get(0).getWidth() );
    }
    private int safeShootY() {
        return (int) (y + this.currentAnimation.get(0).getWidth());
    }
    private void shootBullet(GameWorld gw) {
        int bX = safeShootX();  // Set initial X position of bullet to tank's X
        int bY = safeShootY();

        // Determine shooting angle
        if (UpPressed && LeftPressed) {
            angle = 225;  // 45 degrees up-left
        } else if (UpPressed && RightPressed) {
            angle = 315;  // 45 degrees up-right
        } else if (DownPressed && LeftPressed) {
            angle = 135;  // 45 degrees down-left
        } else if (DownPressed && RightPressed) {
            angle = 45;   // 45 degrees down-right
        }

        // Adjust bullet's starting position
        if (angle == 0 || angle == 45 || angle == 315) {
            bX += 5;

        }else if( angle == 270){
            bX = (int)(x+20);
            bY = (int)(y -20);
        }
        else {
            bX -= 35;
        }
        Sound s = AssetManager.getSound("shootSound");
        s.setVolume(0.3f);
        s.play();
        Bullet newBullet = new Bullet(bX, bY, AssetManager.getAnimation("bullt1"));
        newBullet.shoot(angle);
        newBullet.setOwner(playerId);
        newBullet.setDamage(this.damage);
        gw.addGameObject(newBullet);  // Add bullet to the GameWorld

    }
    void setX() {
        this.x = this.resPawnX;
    }
    void setY() {
        this.y = this.resPawnY;
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
    void unToggleUpPressed() {this.UpPressed = false;}
    void unToggleDownPressed() {
        this.DownPressed = false;
    }
    void unToggleRightPressed() {
        this.RightPressed = false;
    }
    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    void toggleShootPress() {

        isShooting = true;


    }
    void unToggleShootPressed(){
        isShooting = false;
        currentAnimation = standingAnimation;
    }

    public void update(GameWorld gw) {
        this.gw = gw;
        boolean isMoving = false;
        if (health <= 0) {

            if (currentAnimation != dieAnimation) {
                currentAnimation = dieAnimation;
                currentFrame = 0;
            }
            // Ensure the player remains on the last frame of the dieAnimation
            if (currentAnimation == dieAnimation && currentFrame < dieAnimation.size() - 1) {
                updateAnimationFrame();
            }
            return; // Skip the rest of the update logic if the player is dead
        }

        // Movement logic
        if (isShooting) {
            if (currentAnimation != shootAnimation) {
                currentAnimation = shootAnimation;
                currentFrame = 0;
                shootTimer = shootDuration; // Reset timer for shooting animation
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

            if (isMoving && currentAnimation != walkingAnimation) {
                currentAnimation = walkingAnimation;
                currentFrame = 0;
            } else if (!isMoving && currentAnimation != standingAnimation) {
                currentAnimation = standingAnimation;
                currentFrame = 0;
            }
        }

        // Handle shooting
        if (isShooting) {
            if (shootTimer > 0) {
                shootTimer--; // Decrement shooting timer
            }
            // Fire bullet only at the last frame of the shoot animation
            if (shootTimer == 0 && currentAnimation == shootAnimation && currentFrame == shootAnimation.size() - 1) {
                shootBullet(gw);
                shootTimer = shootDuration + shootFrameDelay; // Reset shoot timer
            }
        }

        centerScreen();
        updateAnimationFrame();

    }
    private void updateAnimationFrame() {
        if (currentAnimation != null && !currentAnimation.isEmpty()) {
            frameCounter++;
            int effectiveFrameDelay = (currentAnimation == shootAnimation) ? shootFrameDelay : frameDelay;
            if (frameCounter >= effectiveFrameDelay) {
                frameCounter = 0;
                currentFrame = (currentFrame + 1) % currentAnimation.size();



            }
        }
    }

    public float getScreenX() {
        return this.screenX;
    }

    public float getScreenY() {
        return this.screenY;
    }
    private void centerScreen() {
        this.screenX = this.x - GameConstants.GAME_SCREEN_WIDTH / 4f;
        this.screenY = this.y - GameConstants.GAME_SCREEN_HEIGHT / 4f;
        if (this.screenX < 0) screenX = 0;
        if (this.screenY < 0) screenY = 0;
        if (this.screenX > GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f) {
            this.screenX = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2f;
        }
        if (this.screenY > GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screenY = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    private void checkBorder() {
        if (x < 30) x = 30;
        if (x >= GameConstants.WORLD_WIDTH - 80) x = GameConstants.WORLD_WIDTH - 80;
        if (y < 40) y = 40;
        if (y >= GameConstants.WORLD_HEIGHT - 88) y = GameConstants.WORLD_HEIGHT - 88;
    }

    private void moveLeft() {
        angle = 180;
        isFacingLeft = true;
        x -= this.speed;
        checkBorder();
    }

    private void moveRight() {
        angle = 0;
        isFacingLeft = false;
        x += this.speed;
        checkBorder();
    }

    private void moveDown() {
        angle = 90;
        y += this.speed;
        checkBorder();
    }

    private void moveUp() {
        angle = 270;
        y -= this.speed;
        checkBorder();
    }



void drawImage(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    double scaleFactor = 1.5;
    BufferedImage currentImage;

    if (currentAnimation != null && !currentAnimation.isEmpty()) {
        currentFrame = Math.min(currentFrame, currentAnimation.size() - 1);
        currentImage = currentAnimation.get(currentFrame);
    } else if (!standingAnimation.isEmpty()) {
        currentFrame = 0;
        currentImage = standingAnimation.get(currentFrame);
    } else {
        throw new IllegalStateException("No animation frames available.");
    }

    System.out.println(" Dog: X "+x + "Y "+y);
    AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
    if (isFacingLeft) {
        rotation.scale(-scaleFactor, scaleFactor);
        rotation.translate(-currentImage.getWidth(), 0);
    } else {
        rotation.scale(scaleFactor, scaleFactor);
    }
    g2d.drawImage(currentImage, rotation, null);
}


    @Override
    public void handleCollision(GameObject o) {
        System.out.println("Player HEALTH "+ this.health);
        Sound pu = AssetManager.getSound("pk");
        if (o instanceof Wall || o instanceof Breakable || o instanceof  Player) {
            Rectangle wallHitbox = o.getHitbox();
            Rectangle playerHitbox = this.getHitbox();

            // Check where the collision occurred and adjust position
            if (playerHitbox.intersects(wallHitbox)) {
                Rectangle intersection = playerHitbox.intersection(wallHitbox);

                // Determine collision side
                if (intersection.width < intersection.height) {
                    // Horizontal collision
                    if (playerHitbox.x < wallHitbox.x) {
                        // Collision on the left
                        x -= intersection.width;
                    } else {
                        // Collision on the right
                        x += intersection.width;
                    }
                } else {
                    // Vertical collision
                    if (playerHitbox.y < wallHitbox.y) {
                        // Collision on the top
                        y -= intersection.height;
                    } else {
                        // Collision on the bottom
                        y += intersection.height;
                    }
                }

                // Update hitbox location after position adjustment
                this.hitbox.setLocation((int) x, (int) y);

            }
        }else if (o instanceof Bullet) {
            Bullet bullet = (Bullet) o;
            // If the bullet is owned by the player, ignore the collision
            if (bullet.getOwner() != this.playerId) {
                this.health = this.health - bullet.getDamage();
            }
        }else if(o instanceof Speed){
            pu.setVolume(0.2f);
            pu.play();
            upgradeSpeed();
            System.out.println("PLayer speed: "+this.speed);
            o.setHasCollided();
        }else if(o instanceof Health){
            pu.setVolume(0.2f);
            pu.play();
          upgradeHealth();
          if(this.health>100){
              this.health=100;
          }
            System.out.println("PLayer health: "+this.health);
            o.setHasCollided();
        }else if(o instanceof Damage){
            pu.setVolume(0.2f);
            pu.play();
            upgradeDamage();
            System.out.println("PLayer"+ playerId +"damage:"+ this.damage);
             o.setHasCollided();
        }
    }
    @Override
    public Rectangle getHitbox() {
        BufferedImage currentImage = (currentAnimation != null && !currentAnimation.isEmpty())
                ? currentAnimation.get(Math.min(currentFrame, currentAnimation.size() - 1))
                : img;
        return new Rectangle((int) x, (int) y + 10, (int) currentImage.getWidth(), (int) currentImage.getHeight());
    }
    public int getType(){
        return playerId;
    }

}
