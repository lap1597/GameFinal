package GameFile.game;

import GameFile.utils.AssetManager;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x, y;
    protected BufferedImage img;
    protected List<BufferedImage> animation;
    protected boolean hasCollided = false;
    protected boolean wallCollision = false;
    protected Rectangle hitbox;
    public GameObject(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x, (int)y, img.getWidth()/2, img.getHeight()/2);
    }
    public GameObject(int x, int y,List<BufferedImage> img) {
        this.x = x;
        this.y = y;
        this.animation = img;
        this.hitbox = new Rectangle((int)x, (int)y, animation.get(0).getWidth(), animation.get(0).getHeight());
    }

    public static GameObject newInstance(String type, int x, int y) {


        return switch (type){
            // Wall -
            case "9"->new Wall(x,y, AssetManager.getSprite("unbreak"));
            case "20" -> new Wall(x,y ,AssetManager.getSprite("t7"));

            //--house
            case "4"->new Wall(x,y, AssetManager.getSprite("h1"));
            case "5"->new Wall(x,y, AssetManager.getSprite("h2"));
            case "6"->new Wall(x,y, AssetManager.getSprite("h3"));
            case "7"->new Wall(x,y, AssetManager.getSprite("h4"));
            case "8"->new Wall(x,y, AssetManager.getSprite("h5"));
            case "10"->new Wall(x,y, AssetManager.getSprite("h6"));
            case "11"->new Wall(x,y, AssetManager.getSprite("h7"));
            case "12"->new Wall(x,y, AssetManager.getSprite("h8"));
            case "13"->new Wall(x,y, AssetManager.getSprite("h9"));
            case "14"->new Wall(x,y, AssetManager.getSprite("h10"));
            case "15"->new Wall(x,y, AssetManager.getSprite("h11"));
            case "31" ->new Wall(x,y, AssetManager.getSprite("h12"));
            //--bigTree


            //Ground
            case "1" -> new Ground(x,y ,AssetManager.getSprite("g8"));
            case "30" -> new Ground(x,y ,AssetManager.getSprite("g4"));
            case "16" -> new Ground(x,y ,AssetManager.getSprite("g5"));
            case "17" -> new Ground(x,y ,AssetManager.getSprite("g6"));
            case "18" -> new Ground(x,y ,AssetManager.getSprite("g7"));
            case "0" -> new Ground (x,y ,AssetManager.getSprite("g1"));
            //G -Corn
            case "2" -> new Ground (x,y ,AssetManager.getSprite("corn"));
            //Breakable Wall - Tree

            case "3"->new Breakable(x,y, AssetManager.getSprite("break12"));
            case "19" -> new Breakable(x,y, AssetManager.getSprite("cow1"));
            case "21" -> new Breakable(x,y, AssetManager.getSprite("cow2"));
            case "22" -> new Breakable(x,y, AssetManager.getSprite("pig1"));
            case "23" -> new Breakable(x,y, AssetManager.getSprite("pig2"));
            case "24" -> new Breakable(x,y, AssetManager.getSprite("ck1"));
            case "25" -> new Breakable(x,y, AssetManager.getSprite("ck2"));

            //ITEMS
            case "27"->new Damage(x,y, AssetManager.getSprite("dmg"));
            case "28" -> new Health(x,y, AssetManager.getSprite("hlt"));
            case "29" -> new Speed(x,y, AssetManager.getSprite("speed"));



            default ->  throw new IllegalArgumentException("Invalid type %s".formatted(type));

        };
    }


    public void draw(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }

    public boolean getHasCollided() {
        return this.hasCollided;
    }
    public void setHasCollided() {
        this.hasCollided = true;
    }


    public void handleCollision(GameObject by) {
        // empty method for override in Tank.java and Bullet.java
        // reference obj GameObjects in GameWorld.java
    }
}



