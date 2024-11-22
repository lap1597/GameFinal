package GameFile.game;


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
                this.t2.update();
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



        this.gObj = MapLoader.loadMapObjects("level1");
      //  floor = AssetManager.getSprite("floor");
        System.out.println(gObj);
        t1 = new Tank(300, 300, 0, 0, (short) 0, AssetManager.getSprite("tank1"));
        t2 = new Tank ( 500, 500,0,0,(short) 0,  AssetManager.getSprite("tank2"));

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
//        this.gObj.add(t1);
//        this.gObj.add(t2);


    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);

        drawFloor(buffer);
        for(GameObject gObj : this.gObj){
            gObj.draw(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world, 0, 0, null);
        buildMiniMap(g2);
        buildSplitScreen(g2);

    }

    private void drawFloor(Graphics buffer){
        BufferedImage floor = AssetManager.getSprite("floor");
        for(int i=0; i<GameConstants.WORLD_WIDTH; i+=320){
            for(int j=0; j<GameConstants.WORLD_HEIGHT; j+=240){
                buffer.drawImage(floor, i, j, null);
            }
        }
    }
    private void buildMiniMap(Graphics2D window){
        BufferedImage mini = this.world.getSubimage(0,0,
                GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT);
        AffineTransform miniTransform = AffineTransform.getTranslateInstance(
                GameConstants.GAME_SCREEN_WIDTH/2f - (GameConstants.GAME_SCREEN_WIDTH*0.2)/2,
                GameConstants.GAME_SCREEN_HEIGHT - (GameConstants.GAME_SCREEN_HEIGHT*0.2));
        window.scale(0.2,0.2);

        window.drawImage(mini, miniTransform, null);

    }
    private void buildSplitScreen(Graphics window){
        BufferedImage lh = this.world.getSubimage((int)t1.getScreenX(), (int)t2.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.WORLD_HEIGHT);
        window.drawImage(lh, 0,0, null);

        BufferedImage rh = this.world.getSubimage((int)t2.getScreenX(),(int)t2.getScreenY(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.WORLD_HEIGHT);
        window.drawImage(rh,GameConstants.GAME_SCREEN_WIDTH/2,0, null);

    }

}
