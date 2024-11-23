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
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;
    List<GameObject> gObj = new ArrayList<>();
    List<GameObject> bg = new ArrayList<>();
    private BufferedImage floor;

    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
             //   this.t2.update();
                checkCollision();
                this.repaint();   // redraw game
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
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(500);
        this.t2.setY(500);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        this.bg = MapLoader.loadMapObjects("bground");
        this.gObj = MapLoader.loadMapObjects("level1");

        System.out.println(gObj);
        t1 = new Tank(300, 300, AssetManager.getSprite("tank1"));
     //   t2 = new Tank ( 500, 500,0,0,(short) 0,  AssetManager.getSprite("tank2"));

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
//        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc1);
     //   this.lf.getJf().addKeyListener(tc2);
//        this.gObj.add(t1);
//        this.gObj.add(t2);


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);

       // drawFloor(buffer);
//        for(GameObject obj : this.bg) {
//            obj.draw(buffer);
//        }
        for(GameObject gObj : this.gObj){
            gObj.draw(buffer);
        }

        this.t1.drawImage(buffer);
    //    this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);
        buildMiniMap(g2);
        buildSplitScreen(g2);

    }

    private void drawFloor(Graphics buffer){
        BufferedImage floor = AssetManager.getSprite("g2");
        for(int i=0; i<GameConstants.WORLD_WIDTH; i+=32){
            for(int j=0; j<GameConstants.WORLD_HEIGHT; j+=32){
                buffer.drawImage(floor, i, j, null);
            }
        }
    }
    private void checkCollision(){
        for (GameObject obj : this.gObj) {
            if (obj instanceof Wall) { // Check only for Wall collision
                if (t1.getHitbox().intersects(obj.getHitbox())) {
                    t1.handleCollision(obj); // Call the collision handler in the Tank class
                }
            }
        }
    }
    private void buildMiniMap(Graphics2D window){
        double scale= 0.1;
        BufferedImage mini = this.world.getSubimage(0,0,
                GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT);
        AffineTransform miniTransform = AffineTransform.getTranslateInstance(
                (GameConstants.GAME_SCREEN_WIDTH/2f - GameConstants.WORLD_WIDTH*scale/2f),
                GameConstants.GAME_SCREEN_HEIGHT/2f);
        window.scale(scale,scale);

        window.drawImage(mini, 0,0, null);
      //  window.scale(1 / scale, 1 / scale);

    }
    private void buildSplitScreen(Graphics window){
        BufferedImage lh = this.world.getSubimage((int)t1.getScreenX(), (int)t1.getScreenY(), (int)GameConstants.GAME_SCREEN_WIDTH/2, (int)GameConstants.GAME_SCREEN_HEIGHT);
        window.drawImage(lh, 0,0, null);

//        BufferedImage rh = this.world.getSubimage((int)t2.getScreenX(),(int)t2.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.WORLD_HEIGHT);
//        window.drawImage(rh,GameConstants.GAME_SCREEN_WIDTH/2,0, null);

    }

}
