package GameFile.utils;

import GameFile.game.GameWorld;
import GameFile.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AssetManager {
    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private static final Map<String, Sound> sound = new HashMap<>();
    private static final Map<String, List<BufferedImage>> animation = new HashMap<>();

    private static BufferedImage loadImage(final String path) throws IOException {


        return ImageIO.read(
                Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path),
                        "Could not find %s".formatted(path)));

    }
    private static Sound loadClip(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioIn  = AudioSystem.getAudioInputStream(
                Objects.requireNonNull(
                        AssetManager.class.getClassLoader().getResource(path),
                        "Sound Resource %s was not found".formatted(path)
                ));
        Clip clip  = AudioSystem.getClip();
        clip .open(audioIn);

        return new Sound(clip);
    }
    private static void loadSprites(){
        try{
         AssetManager.sprites.put("bg", loadImage("maps/bg.png"));
        AssetManager.sprites.put("dog", loadImage("animations/Dog/stand/stand1.png"));
        AssetManager.sprites.put("cat", loadImage("animations/Cat/stand/cstand.png"));
        AssetManager.sprites.put("tank2", loadImage("tank/tank2.png"));
        AssetManager.sprites.put("menu", loadImage("menu/dogvscat.png"));
        AssetManager.sprites.put("dogWin", loadImage("menu/dogWin.png"));
        AssetManager.sprites.put("catWin", loadImage("menu/catWin.png"));
        AssetManager.sprites.put("bullet", loadImage("bullet/bullet.jpg"));

            //Floor
        AssetManager.sprites.put("floor", loadImage("floor/bg.bmp"));
      //  AssetManager.sprites.put("bg", loadImage("floor/floor2.png"));

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


        //Bullet type1
        AssetManager.sprites.put("bull1t1", loadImage("bullet/bull1t1.png"));
        AssetManager.sprites.put("bull2t1", loadImage("bullet/bull2t1.png"));
        AssetManager.sprites.put("bull3t1", loadImage("bullet/bull3t1.png"));
        AssetManager.sprites.put("bull4t1", loadImage("bullet/bull4t1.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadSound(){
        //Sound
        try {
            AssetManager.sound.put("bgSound", loadClip("sounds/s6.wav"));
            AssetManager.sound.put("shootSound", loadClip("sounds/shotfiring.wav"));

            AssetManager.sound.put("pk", loadClip("sounds/pickupItem.wav"));

//            AssetManager.sound.put("loseSound", loadClip("sounds/die.wav"));

        }catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }
    private static void loadAnimation(){
        try {
            // Load the stand
            List<BufferedImage> dogStand = List.of(
                    loadImage("animations/Dog/stand/stand1.png")

            );


            animation.put("dogStand", dogStand);
            // Load the c stand
            List<BufferedImage> catStand = List.of(
                    loadImage("animations/Cat/stand/cstand.png")

            );


            animation.put("catStand", catStand);
            // Load the  d walk
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
            // Load the c walk
            List<BufferedImage> catWalk = List.of(
                    loadImage("animations/Cat/walk/cwalk1.png"),
                    loadImage("animations/Cat/walk/cwalk2.png"),
                    loadImage("animations/Cat/walk/cwalk3.png"),
                    loadImage("animations/Cat/walk/cwalk4.png"),
                    loadImage("animations/Cat/walk/cwalk5.png"),
                    loadImage("animations/Cat/walk/cwalk6.png"),
                    loadImage("animations/Cat/walk/cwalk7.png"),
                    loadImage("animations/Cat/walk/cwalk8.png")

            );
            animation.put("catWalk", catWalk);

            //Load the shoot
            List<BufferedImage> dogShoot = List.of(
                    loadImage("animations/Dog/shoot/shoot1.png"),
                    loadImage("animations/Dog/shoot/shoot2.png"),
                    loadImage("animations/Dog/shoot/shoot3.png"),
                    loadImage("animations/Dog/shoot/shoot4.png"),
                    loadImage("animations/Dog/shoot/shoot5.png"),
                    loadImage("animations/Dog/shoot/shoot6.png"),
                    loadImage("animations/Dog/shoot/shoot7.png")


            );
            animation.put("dogShoot", dogShoot);
            //Load the c shoot
            List<BufferedImage> catShoot = List.of(
                    loadImage("animations/Cat/shoot/shoot1.png"),
                    loadImage("animations/Cat/shoot/shoot2.png"),
                    loadImage("animations/Cat/shoot/shoot3.png"),
                    loadImage("animations/Cat/shoot/shoot4.png"),
                    loadImage("animations/Cat/shoot/shoot5.png"),
                    loadImage("animations/Cat/shoot/shoot6.png"),
                    loadImage("animations/Cat/shoot/shoot7.png")

            );
            animation.put("catShoot", catShoot);

            List<BufferedImage> bullt1 = List.of(
                    loadImage("bullet/bull1t2.png"),
                    loadImage("bullet/bull2t2.png"),
                    loadImage("bullet/bull3t2.png"),
                    loadImage("bullet/bull4t2.png")

            );
            animation.put("bullt1", bullt1);

            List<BufferedImage> t1collision = List.of(
                    loadImage("animations/bullethit/t2c1.png"),
                    loadImage("animations/bullethit/t2c2.png"),
                    loadImage("animations/bullethit/t2c3.png")
            );
            animation.put("t1collision", t1collision);
        //
            List<BufferedImage> catDeath = List.of(
                    loadImage("animations/Cat/catDie/catd1.png"),
                    loadImage("animations/Cat/catDie/catd2.png"),
                    loadImage("animations/Cat/catDie/catd3.png"),
                    loadImage("animations/Cat/catDie/catd4.png"),
                    loadImage("animations/Cat/catDie/catd5.png")
            );
//
            animation.put("catDeath", catDeath);
            List<BufferedImage> dogDeath = List.of(
                    loadImage("animations/Dog/dogDie/dogd1.png"),
                    loadImage("animations/Dog/dogDie/dogd2.png"),
                    loadImage("animations/Dog/dogDie/dogd3.png"),
                    loadImage("animations/Dog/dogDie/dogd4.png"),
                    loadImage("animations/Dog/dogDie/dogd5.png")
            );

            animation.put("dogDeath", dogDeath);

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
    public static Sound getSound(String key){
        if(!sound.containsKey(key)){
            throw new RuntimeException("Key not found %s".formatted(key));
        }
        return sound.get(key);
    }

    public static void loadAssets(){
        loadSprites();
        loadSound();
        loadAnimation();

    }
}
