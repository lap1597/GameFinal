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
    private static final Map<String, List<BufferedImage>> snimation = new HashMap<>();

    private static BufferedImage loadImage(final String path) throws IOException {


        return ImageIO.read(
                Objects.requireNonNull(GameWorld.class.getClassLoader().getResource(path),
                        "Could not find %s".formatted(path)));

    }
    private static void loadSprites(){
        try{
        AssetManager.sprites.put("tank1", loadImage("tank/tank1.png"));
        AssetManager.sprites.put("tank2", loadImage("tank/tank2.png"));

        AssetManager.sprites.put("menu", loadImage("menu/title.png"));

        AssetManager.sprites.put("bullet", loadImage("bullet/bullet.jpg"));
        //Floor
        AssetManager.sprites.put("floor", loadImage("floor/bg.bmp"));
        AssetManager.sprites.put("bg", loadImage("floor/floor2.png"));
        AssetManager.sprites.put("g1", loadImage("floor/g1.png"));
        AssetManager.sprites.put("g2", loadImage("floor/g2.png"));
        AssetManager.sprites.put("g3", loadImage("floor/g3.png"));
        AssetManager.sprites.put("g4", loadImage("floor/g4.png"));
        AssetManager.sprites.put("g5", loadImage("floor/g5.png"));
        AssetManager.sprites.put("g6", loadImage("floor/g6.png"));
        AssetManager.sprites.put("g7", loadImage("floor/g7.png"));
        AssetManager.sprites.put("g8", loadImage("floor/g8.png"));
        AssetManager.sprites.put("g9", loadImage("floor/g9.png"));
        AssetManager.sprites.put("g10", loadImage("floor/g10.png"));
        AssetManager.sprites.put("grass", loadImage("floor/grass.png"));
        AssetManager.sprites.put("grass2", loadImage("floor/grass2.png"));

        AssetManager.sprites.put("health", loadImage("powerups/health.png"));

        AssetManager.sprites.put("speed", loadImage("powerups/speed.png"));

        AssetManager.sprites.put("shield", loadImage("powerups/shield.png"));

        AssetManager.sprites.put("unbreak", loadImage("wall/wall1.png"));
        AssetManager.sprites.put("break", loadImage("wall/wall2.png"));
        AssetManager.sprites.put("break1", loadImage("wall/tile000.png"));

        AssetManager.sprites.put("break3", loadImage("wall/tile003.png"));
        AssetManager.sprites.put("break4", loadImage("wall/tile004.png"));
        AssetManager.sprites.put("break5", loadImage("wall/tile005.png"));
        AssetManager.sprites.put("break6", loadImage("wall/tile006.png"));
        AssetManager.sprites.put("break7", loadImage("wall/tile007.png"));
        AssetManager.sprites.put("break8", loadImage("wall/tile008.png"));
        AssetManager.sprites.put("break9", loadImage("wall/tile009.png"));
        AssetManager.sprites.put("break10", loadImage("wall/tile010.png"));
        AssetManager.sprites.put("break11", loadImage("wall/tile011.png"));
        AssetManager.sprites.put("break12", loadImage("wall/tile012.png"));
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
