//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package GameFile.game;

import GameFile.Launcher;
import GameFile.utils.AssetManager;
import GameFile.utils.MapLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class GameWorld extends JPanel implements Runnable {
    private BufferedImage world;
    private BufferedImage ground;
    private Player t1;
    private Player t2;
    private final Launcher lf;
    private long tick = 0L;
    List<GameObject> gObj = new ArrayList();
    private BufferedImage heart1;
    private BufferedImage heart2;
    private BufferedImage heart3;
    private BufferedImage heart4;
    private BufferedImage heart5;
    private long t1RespawnTime = -1L;
    private long t2RespawnTime = -1L;
    private BufferedImage floor;

    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    public void run() {
        this.resetGame();

        try {
            while(true) {
                ++this.tick;
                this.t1.update(this);
                this.t2.update(this);

                for(int i = this.gObj.size() - 1; i >= 0; --i) {
                    Object var3 = this.gObj.get(i);
                    if (var3 instanceof Tracking u) {
                        u.update(this);
                    }
                }

                this.checkCollision();
                this.gObj.removeIf((g) -> {
                    return g.getHasCollided();
                });
                this.repaint();
                if (this.t1.getLife().getCounter() == 0) {
                    System.out.println("Cat win!");
                    this.lf.updateEndGamePanel(AssetManager.getSprite("catWin"));
                    this.lf.setFrame("end");
                    break;
                }

                if (this.t2.getLife().getCounter() == 0) {
                    System.out.println("Dog win!");
                    this.lf.updateEndGamePanel(AssetManager.getSprite("dogWin"));
                    this.lf.setFrame("end");
                    break;
                }

                Thread.sleep(5L);
            }
        } catch (InterruptedException var4) {
            InterruptedException ignored = var4;
            System.out.println(ignored);
        }

    }

    public void resetGame() {
        this.tick = 0L;
        this.gObj.clear();
        this.t1.getLife().setCounter(3);
        this.t2.getLife().setCounter(3);
        this.t1.setX();
        this.t1.setY();
        this.t1.setHealth(100);
        this.t1.setSpeed(0.5F);
        this.t1.setDamage(5);
        this.t2.setX();
        this.t2.setY();
        this.t2.setHealth(100);
        this.t2.setSpeed(0.5F);
        this.t1.setDamage(5);
        this.gObj = MapLoader.loadMapObjects("level1");
        this.gObj.add(this.t1);
        this.gObj.add(this.t2);
        this.repaint();
    }

    public void InitializeGame() {
        this.world = new BufferedImage(2048, 1536, 1);
        this.ground = AssetManager.getSprite("bg");
        this.gObj = MapLoader.loadMapObjects("level1");
        System.out.println(this.gObj);
        this.t1 = new Player(1, 100.0F, 100.0F, AssetManager.getAnimation("dogStand"));
        this.t2 = new Player(2, 1850.0F, 1280.0F, AssetManager.getAnimation("catStand"));
        PlayerControl tc1 = new PlayerControl(this.t1, 87, 83, 65, 68, 32);
        PlayerControl tc2 = new PlayerControl(this.t2, 38, 40, 37, 39, 16);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
        this.gObj.add(this.t1);
        this.gObj.add(this.t2);

        try {
            Sound bgSound = AssetManager.getSound("bgSound");
            bgSound.loopContinuously();
            bgSound.setVolume(0.5F);
            System.out.println("Background sound started.");
        } catch (Exception var4) {
            Exception e = var4;
            System.err.println("Failed to play background sound: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Graphics2D buffer = this.world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, 1280, 960);
        buffer.drawImage(this.ground, 0, 0, 2048, 1536, this);
        Iterator var4 = this.gObj.iterator();

        while(var4.hasNext()) {
            GameObject gObj = (GameObject)var4.next();
            gObj.draw(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        buffer.drawImage(this.world, 0, 0, (ImageObserver)null);
        this.displayHealthBar(buffer);
        this.displayLiveCount(buffer);
        this.buildSplitScreen(g2);
        this.buildMiniMap(g2);
    }

    private void checkCollision() {
        for(int i = 0; i < this.gObj.size(); ++i) {
            GameObject obj1 = (GameObject)this.gObj.get(i);

            for(int j = 0; j < this.gObj.size(); ++j) {
                if (i != j) {
                    GameObject obj2;
                    if (obj1 instanceof Player) {
                        obj2 = (GameObject)this.gObj.get(j);
                        if (obj1.getHitbox().intersects(obj2.getHitbox())) {
                            obj1.handleCollision(obj2);
                        }
                    }

                    if (obj1 instanceof Bullet) {
                        obj2 = (GameObject)this.gObj.get(j);
                        if (obj1.getHitbox().intersects(obj2.getHitbox())) {
                            obj1.handleCollision(obj2);
                        }
                    }
                }
            }
        }

    }

    public void addGameObject(GameObject g) {
        this.gObj.add(g);
    }

    private void buildMiniMap(Graphics2D window) {
        double scale = 0.1;
        BufferedImage mini = new BufferedImage((int)(2048.0 * scale), (int)(1536.0 * scale), 1);
        Graphics2D miniGraphics = mini.createGraphics();
        miniGraphics.scale(scale, scale);
        miniGraphics.drawImage(this.world, 0, 0, (ImageObserver)null);
        miniGraphics.dispose();
        int miniMapWidth = mini.getWidth();
        int miniMapHeight = mini.getHeight();
        int xPosition = (1280 - miniMapWidth) / 2;
        int yPosition = 960 - miniMapHeight - 20;
        window.drawImage(mini, xPosition, yPosition, (ImageObserver)null);
    }

    private void buildSplitScreen(Graphics window) {
        BufferedImage lh = this.world.getSubimage((int)this.t1.getScreenX(), (int)this.t1.getScreenY(), 640, 960);
        window.drawImage(lh, 0, 0, (ImageObserver)null);
        BufferedImage rh = this.world.getSubimage((int)this.t2.getScreenX(), (int)this.t2.getScreenY(), 640, 960);
        window.drawImage(rh, 640, 0, (ImageObserver)null);
    }

    private void displayHealthBar(Graphics2D onScreenPanel) {
        this.t1.getLife().draw(onScreenPanel, (int)this.t1.getX() - 5, (int)this.t1.getY() - 10);
        this.t2.getLife().draw(onScreenPanel, (int)this.t2.getX() - 5, (int)this.t2.getY() - 10);
    }

    private void displayLiveCount(Graphics2D onScreenPanel) {
        int live1 = this.t1.getLife().getCounter();
        int live2 = this.t2.getLife().getCounter();
    }
}
