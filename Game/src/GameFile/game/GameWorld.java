package GameFile.game;

import GameFile.GameConstants;
import GameFile.Launcher;
import GameFile.utils.AssetManager;
import GameFile.utils.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private BufferedImage ground;
    private Player t1;
    private Player t2;
    private final Launcher lf;
    private long tick = 0L;
    private List<GameObject> gObj = new ArrayList<>();

    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        this.resetGame();

        try {
            while (true) {
                if (this.t1 == null || this.t2 == null) {
                    System.out.println("Game objects not initialized, skipping frame.");
                    Thread.sleep(5L);
                    continue;
                }

                this.tick++;
                this.t1.update(this);
                this.t2.update(this);

                for (int i = this.gObj.size() - 1; i >= 0; i--) {
                    GameObject obj = this.gObj.get(i);
                    if (obj instanceof Tracking tracking) {
                        tracking.update(this);
                    }
                }

                this.checkCollision();
                this.gObj.removeIf(GameObject::getHasCollided);
                this.repaint();

                if (this.t1.getLife().getCounter() == 0) {
                    System.out.println("Cat wins!");
                    this.lf.updateEndGamePanel(AssetManager.getSprite("catWin"));
                    this.lf.setFrame("end");
                    break;
                }

                if (this.t2.getLife().getCounter() == 0) {
                    System.out.println("Dog wins!");
                    this.lf.updateEndGamePanel(AssetManager.getSprite("dogWin"));
                    this.lf.setFrame("end");
                    break;
                }

                Thread.sleep(5L);
            }
        } catch (InterruptedException e) {
            System.err.println("Game loop interrupted: " + e.getMessage());
        }
    }

    public void resetGame() {
        System.out.println("Resetting game...");
        this.tick = 0L;
        this.gObj.clear();

        // Reset player properties
        if (this.t1 != null) {
            this.t1.getLife().setCounter(3);
            this.t1.setX();
            this.t1.setY();
            this.t1.setHealth(100);
            this.t1.setSpeed(0.5F);
            this.t1.setDamage(5);
        }

        if (this.t2 != null) {
            this.t2.getLife().setCounter(3);
            this.t2.setX();
            this.t2.setY();
            this.t2.setHealth(100);
            this.t2.setSpeed(0.5F);
            this.t2.setDamage(5);
        }

        // Load the selected map
        String selectedMap = this.lf.getSelectedMap();
        if (selectedMap == null || selectedMap.isEmpty()) {
            System.out.println("No map selected, defaulting to level1.");
            selectedMap = "level1";
        } else {
            System.out.println("Selected map: " + selectedMap);
        }

        this.gObj = MapLoader.loadMapObjects(selectedMap);
        this.gObj.add(this.t1);
        this.gObj.add(this.t2);
        this.repaint();
    }

    public void InitializeGame() {
        System.out.println("Initializing game...");
        this.world = new BufferedImage(2048, 1536, BufferedImage.TYPE_INT_ARGB);
        this.ground = AssetManager.getSprite("bg");

        // Load the selected map
        String selectedMap = this.lf.getSelectedMap();
        if (selectedMap == null || selectedMap.isEmpty()) {
            selectedMap = "level1";
        }

        this.gObj = MapLoader.loadMapObjects(selectedMap);
        System.out.println("Map loaded: " + selectedMap);

        // Initialize players
        this.t1 = new Player(1, 100.0F, 100.0F, AssetManager.getAnimation("dogStand"));
        this.t2 = new Player(2, 1850.0F, 1280.0F, AssetManager.getAnimation("catStand"));

        // Set up player controls
        PlayerControl tc1 = new PlayerControl(this.t1, 87, 83, 65, 68, 32); // WASD + Spacebar
        PlayerControl tc2 = new PlayerControl(this.t2, 38, 40, 37, 39, 16); // Arrow keys + Shift
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

        this.gObj.add(this.t1);
        this.gObj.add(this.t2);

        // Start background sound
        try {
            Sound bgSound = AssetManager.getSound("bgSound");
            bgSound.loopContinuously();
            bgSound.setVolume(0.5F);
            System.out.println("Background sound started.");
        } catch (Exception e) {
            System.err.println("Failed to play background sound: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = this.world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, 1280, 960);
        buffer.drawImage(this.ground, 0, 0, 2048, 1536, this);

        for (GameObject obj : this.gObj) {
            obj.draw(buffer);
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);


        this.displayHealthBar(buffer);
        this.displayLiveCount(buffer);
        this.buildSplitScreen(g2);
        this.buildMiniMap(g2);
    }

    private void checkCollision() {
        for (int i = 0; i < this.gObj.size(); ++i) {
            GameObject obj1 = this.gObj.get(i);

            for (int j = 0; j < this.gObj.size(); ++j) {
                if (i != j) {
                    GameObject obj2 = this.gObj.get(j);
                    if (obj1.getHitbox().intersects(obj2.getHitbox())) {
                        obj1.handleCollision(obj2);
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
        BufferedImage mini = new BufferedImage((int) (2048 * scale), (int) (1536 * scale), BufferedImage.TYPE_INT_ARGB);
        Graphics2D miniGraphics = mini.createGraphics();
        miniGraphics.scale(scale, scale);
        miniGraphics.drawImage(this.world, 0, 0, null);
        miniGraphics.dispose();

        int miniMapWidth = mini.getWidth();
        int miniMapHeight = mini.getHeight();
        int xPosition = (GameConstants.GAME_SCREEN_WIDTH - miniMapWidth) / 2;
        int yPosition = GameConstants.GAME_SCREEN_HEIGHT - miniMapHeight - 20;
        window.drawImage(mini, xPosition, yPosition, null);
    }

    private void buildSplitScreen(Graphics window) {
        BufferedImage left = this.world.getSubimage((int) this.t1.getScreenX(), (int) this.t1.getScreenY(), 640, 960);
        BufferedImage right = this.world.getSubimage((int) this.t2.getScreenX(), (int) this.t2.getScreenY(), 640, 960);

        window.drawImage(left, 0, 0, null);
        window.drawImage(right, 640, 0, null);
    }

    private void displayHealthBar(Graphics2D onScreenPanel) {
        this.t1.getLife().draw(onScreenPanel, (int) this.t1.getX() - 5, (int) this.t1.getY() - 10);
        this.t2.getLife().draw(onScreenPanel, (int) this.t2.getX() - 5, (int) this.t2.getY() - 10);
    }

    private void displayLiveCount(Graphics2D onScreenPanel) {
        int live1 = this.t1.getLife().getCounter();
        int live2 = this.t2.getLife().getCounter();
        // Display live counts as needed
    }
}
