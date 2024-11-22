package GameFile.game;

import GameFile.utils.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x, y; // because its protected, u dont have to declare this in all subclasses of GameObject or use setters and getters
    protected BufferedImage img;
    public GameObject(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }
    public static GameObject newInstance(String type, int x, int y) {


        return switch (type){
            case "9"->new Wall(x,y, AssetManager.getSprite("unbreak"));
            case "2"->new Wall(x,y, AssetManager.getSprite("unbreak"));
            case "3" -> new Wall (x,y ,AssetManager.getSprite("break12"));
            case "1" -> new Ground(x,y ,AssetManager.getSprite("g8"));
            case "0" -> new Ground (x,y ,AssetManager.getSprite("floor"));


            default ->  throw new IllegalArgumentException("Invalid type %s".formatted(type));

        };
    }


    public void draw(Graphics g) {
        g.drawImage(this.img, (int)x,(int)y, null);
    }
}


