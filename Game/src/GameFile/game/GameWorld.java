package GameFile.game;


import GameFile.GameConstants;
import GameFile.Launcher;
import GameFile.utils.AssetManager;
import GameFile.utils.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private BufferedImage ground;
    private Player t1;
    private Player t2;
    private final Launcher lf;
    private long tick = 0;
    List<GameObject> gObj = new ArrayList<>();
    private BufferedImage heart1, heart2, heart3, heart4, heart5;
   private  long t1RespawnTime = -1; // Tracks when to respawn t1 (-1 means no respawn needed)
    private long t2RespawnTime = -1;
    private BufferedImage floor;

    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        resetGame();
        try {
            while (true) {

                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this);
                for (int i = this.gObj.size()-1; i >= 0; i--) {
                    if(this.gObj.get(i) instanceof Tracking u) {
                        u.update(this);
                    }
                }
                this.checkCollision();
                this.gObj.removeIf(g ->g.getHasCollided());
                this.repaint();   // redraw game

                if(t1.getLife().getCounter() == 0){
                    System.out.println("Cat win!");

                    this.lf.updateEndGamePanel(AssetManager.getSprite("catWin"));
                    lf.setFrame("end");
                    break;
                }else if(t2.getLife().getCounter() == 0){
                    System.out.println("Dog win!");

                    this.lf.updateEndGamePanel(AssetManager.getSprite("dogWin"));
                    lf.setFrame("end");

                    break;
                }

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                */
                Thread.sleep(1000 / 200);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        // Reset tick counter
        this.tick = 0;

        // Clear all game objects
        this.gObj.clear();

        // Reset player lives
        t1.getLife().setCounter(3); // Reset to initial lives (example: 3 lives)
        t2.getLife().setCounter(3);

        // Reset player states
        t1.setX(); // Reset to initial X position
        t1.setY(); // Reset to initial Y position
        t1.setHealth(100); // Reset health
        t1.setSpeed(0.5f); // Reset speed
        t1.setDamage(5);

        t2.setX(); // Reset to initial X position
        t2.setY(); // Reset to initial Y position
        t2.setHealth(100); // Reset health
        t2.setSpeed(0.5f); // Reset speed
        t1.setDamage(5);
        // Reload game objects (map, collectibles, obstacles)
        this.gObj = MapLoader.loadMapObjects("level1");

        // Re-add players to the game object list
        this.gObj.add(t1);
        this.gObj.add(t2);

        // Reset the game view, if applicable
        this.repaint();




    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.ground = AssetManager.getSprite("bg");
        this.gObj = MapLoader.loadMapObjects("level1");


        System.out.println(gObj);
        t1 = new Player(1,100, 100, AssetManager.getAnimation("dogStand"));
        t2 = new Player (2, 500, 500, AssetManager.getAnimation("catStand"));

        PlayerControl tc1 = new PlayerControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        PlayerControl tc2 = new PlayerControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
        this.gObj.add(t1);
        this.gObj.add(t2);
        try {
            Sound bgSound = AssetManager.getSound("bgSound");
            bgSound.loopContinuously(); // Loop the sound continuously
            bgSound.setVolume(0.5f);    // Optional: Set volume to 50%
            System.out.println("Background sound started.");
        } catch (Exception e) {
            System.err.println("Failed to play background sound: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void paintComponent(Graphics g) {
;
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        g2.drawImage(ground, 0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT,this);


        for(GameObject gObj : this.gObj){

            gObj.draw(g2);
        }

        this.t1.drawImage(g2);
        this.t2.drawImage(g2);
     //   buffer.drawImage(world, 0, 0, null);
        displayHealthBar(g2);
        displayLiveCount(g2);
        //buildSplitScreen(buffer);

    }

    private void checkCollision(){

        for (int i = 0; i < this.gObj.size(); i++) {
            GameObject obj1 = this.gObj.get(i);
            for (int j = 0; j < this.gObj.size(); j++) {
                if (i == j) continue; // avoid colliding with yourself

                if (obj1 instanceof Player) {
                    GameObject obj2 = this.gObj.get(j);
                    if (obj1.getHitbox().intersects(obj2.getHitbox())) {

                        obj1.handleCollision(obj2);

                    }
                }
                if(obj1 instanceof Bullet) { // to stop bullets from going past walls
                    GameObject obj2 = this.gObj.get(j);

                    if(obj1.getHitbox().intersects(obj2.getHitbox())) {

                        obj1.handleCollision(obj2);
                    }
                }

            }
        }
    }
    public void addGameObject(GameObject g) {
        this.gObj.add(g);
    }
    private void buildMiniMap(Graphics2D window){
        double scale= 0.1;
        BufferedImage mini = this.world.getSubimage(0,0,
                GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT);
        AffineTransform miniTransform = AffineTransform.getTranslateInstance(
                (GameConstants.GAME_SCREEN_WIDTH/4f - GameConstants.WORLD_WIDTH*scale/2f),
                GameConstants.GAME_SCREEN_HEIGHT/2f);
        window.scale(scale,scale);

        window.drawImage(mini,miniTransform, null);

    }

    private void buildSplitScreen(Graphics window){
        BufferedImage lh = this.world.getSubimage((int)t1.getScreenX(), (int)t1.getScreenY(),
                (int)GameConstants.GAME_SCREEN_WIDTH/2,
                (int)GameConstants.GAME_SCREEN_HEIGHT);
        window.drawImage(lh, 0,0, null);

        BufferedImage rh = this.world.getSubimage((int)t2.getScreenX(),(int)t2.getScreenY(),
                (int)GameConstants.GAME_SCREEN_WIDTH/2,
                (int)GameConstants.GAME_SCREEN_HEIGHT);
        window.drawImage(rh,GameConstants.GAME_SCREEN_WIDTH/2,0, null);

    }

    private void displayHealthBar(Graphics2D onScreenPanel) {
        t1.getLife().draw(onScreenPanel, (int)t1.getX()-5, (int)t1.getY() -10);
        t2.getLife().draw(onScreenPanel, (int)t2.getX()-5, (int)t2.getY() -10);
    }
    private void displayLiveCount(Graphics2D onScreenPanel) {
        int live1 = t1.getLife().getCounter();
        int live2 = t2.getLife().getCounter();
    }

}
