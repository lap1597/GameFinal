package GameFile.utils;

import GameFile.game.GameWorld;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AssetManager {
    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Clip> sound = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animation = new HashMap<>();

    private static BufferedImage loadImage(final String path) throws IOException {


        return ImageIO.read(
                Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path),
                        "Could not find %s".formatted(path)));

    }
    private static void loadSprites(){
        try{
        AssetManager.sprites.put("tank1", loadImage("animations/Dog/stand/stand1.png"));
        AssetManager.sprites.put("tank2", loadImage("tank/tank2.png"));

        AssetManager.sprites.put("menu", loadImage("menu/title.png"));

        AssetManager.sprites.put("bullet", loadImage("bullet/bullet.jpg"));
        //Floor
        AssetManager.sprites.put("floor", loadImage("floor/bg.bmp"));
        AssetManager.sprites.put("bg", loadImage("floor/floor2.png"));

        AssetManager.sprites.put("g1", loadImage("floor/grass1.png"));
        AssetManager.sprites.put("g2", loadImage("floor/grass2.png"));
        AssetManager.sprites.put("g3", loadImage("floor/grass3.png"));
        AssetManager.sprites.put("g4", loadImage("floor/grass4.png"));
        AssetManager.sprites.put("g5", loadImage("floor/grass5.png"));
        AssetManager.sprites.put("g6", loadImage("floor/grass6.png"));
        AssetManager.sprites.put("g7", loadImage("floor/grass7.png"));
        AssetManager.sprites.put("g8", loadImage("floor/grass8.png"));

        //house
        AssetManager.sprites.put("h1", loadImage("floor/roof1.png"));
        AssetManager.sprites.put("h2", loadImage("floor/roof2.png"));
        AssetManager.sprites.put("h3", loadImage("floor/roof3.png"));
        AssetManager.sprites.put("h4", loadImage("floor/roof4.png"));
        AssetManager.sprites.put("h5", loadImage("floor/roof5.png"));
        AssetManager.sprites.put("h6", loadImage("floor/roof6.png"));
        AssetManager.sprites.put("h7", loadImage("floor/house1.png"));
        AssetManager.sprites.put("h8", loadImage("floor/house2.png"));
        AssetManager.sprites.put("h9", loadImage("floor/house3.png"));
        AssetManager.sprites.put("h10", loadImage("floor/bothouse1.png"));
        AssetManager.sprites.put("h11", loadImage("floor/bothouse2.png"));
        AssetManager.sprites.put("h12", loadImage("floor/bothouse3.png"));
        //bigTree
        AssetManager.sprites.put("t1", loadImage("floor/topTree.png"));
        AssetManager.sprites.put("t2", loadImage("floor/topTree2.png"));
        AssetManager.sprites.put("t3", loadImage("floor/midTree1.png"));
        AssetManager.sprites.put("t4", loadImage("floor/midTree2.png"));
        AssetManager.sprites.put("t5", loadImage("floor/botTree1.png"));
        AssetManager.sprites.put("t6", loadImage("floor/botTree2.png"));
        //small Tree
        AssetManager.sprites.put("t7", loadImage("floor/tree.png"));
        //corn
        AssetManager.sprites.put("corn", loadImage("floor/corn.png"));
        //Damage

        AssetManager.sprites.put("dmg", loadImage("powerups/damageUp.png"));
    //SPeed
        AssetManager.sprites.put("speed", loadImage("powerups/speedUp.png"));
//Health
        AssetManager.sprites.put("hlt", loadImage("powerups/healthUp.png"));
//Wall
        AssetManager.sprites.put("unbreak", loadImage("wall/wall1.png"));


        AssetManager.sprites.put("break3", loadImage("wall/tile003.png"));
        AssetManager.sprites.put("break4", loadImage("wall/tile004.png"));
        AssetManager.sprites.put("break5", loadImage("wall/tile005.png"));
        AssetManager.sprites.put("break6", loadImage("wall/tile006.png"));
        AssetManager.sprites.put("break7", loadImage("wall/tile007.png"));
        AssetManager.sprites.put("break8", loadImage("wall/tile008.png"));
        AssetManager.sprites.put("break9", loadImage("wall/tile009.png"));
        AssetManager.sprites.put("break10", loadImage("wall/tile010.png"));
        AssetManager.sprites.put("break11", loadImage("wall/tile011.png"));

        //BreakWall
        AssetManager.sprites.put("break12", loadImage("wall/tile012.png"));
        AssetManager.sprites.put("cow1",loadImage("wall/cow1.png"));
        AssetManager.sprites.put("cow2",loadImage("wall/cow2.png"));
        AssetManager.sprites.put("pig1",loadImage("wall/pig1.png"));
        AssetManager.sprites.put("pig2",loadImage("wall/pig2.png"));


        AssetManager.sprites.put("break13", loadImage("wall/tile013.png"));
        AssetManager.sprites.put("break14", loadImage("wall/tile014.png"));
        AssetManager.sprites.put("break15", loadImage("wall/tile015.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadSound(){

    }
    private static void loadAnimation(){
        try {
            // Load the stand
            List<BufferedImage> dogStand = List.of(
                    loadImage("animations/Dog/stand/stand1.png"),
                    loadImage("animations/Dog/stand/stand2.png"),
                    loadImage("animations/Dog/stand/stand3.png"),
                    loadImage("animations/Dog/stand/stand4.png")
            );

            animation.put("dogStand", dogStand);
            // Load the walk
            List<BufferedImage> dogWalk = List.of(
                    loadImage("animations/Dog/walk/walk1.png"),
                    loadImage("animations/Dog/walk/walk2.png"),
                    loadImage("animations/Dog/walk/walk3.png"),
                    loadImage("animations/Dog/walk/walk4.png"),
                    loadImage("animations/Dog/walk/walk5.png"),
                    loadImage("animations/Dog/walk/walk6.png"),
                    loadImage("animations/Dog/walk/walk7.png"),
                    loadImage("animations/Dog/walk/walk8.png")

            );
            animation.put("dogWalk", dogWalk);
            //Load the shoot
            List<BufferedImage> dogShoot = List.of(
                    loadImage("animations/Dog/shoot/shoot.png"),
                    loadImage("animations/Dog/shoot/shoot1.png"),
                    loadImage("animations/Dog/shoot/shoot2.png"),
                    loadImage("animations/Dog/shoot/shoot3.png"),
                    loadImage("animations/Dog/shoot/shoot4.png"),
                    loadImage("animations/Dog/shoot/shoot5.png"),
                    loadImage("animations/Dog/shoot/shoot6.png"),
                    loadImage("animations/Dog/shoot/shoot7.png")

            );
            animation.put("dogShoot", dogShoot);





        } catch (IOException e) {
            throw new RuntimeException("Failed to load animations", e);
        }



    }
    public static List<BufferedImage> getAnimation(String key){
        if(!animation.containsKey(key)){
            throw new RuntimeException("Key not found %s".formatted(key));
        }
        return animation.get(key);
    }

    public static BufferedImage getSprite(String key) {
        if(!sprites.containsKey(key)){
            throw new RuntimeException("Key not found %s".formatted(key));
        }
        return sprites.get(key);
    }

    public static void loadAssets(){
        loadSprites();
        loadSound();
        loadAnimation();

    }
}
