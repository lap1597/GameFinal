package GameFile.game;

import java.awt.*;

public class Life {
    private Player player;
    private int maxHealth;
    private int width;
    private int height;
    private int counter = 4;
    private long deathTime = -1;
    public Life(Player p, int maxHealth, int width, int height) {
        this.player = p;
        this.maxHealth = maxHealth;
        this.width = width;
        this.height = height;

    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void draw(Graphics2D g, int x, int y) {
        int currentHealth = player.getHealth();
        float healthPercent = (float) currentHealth / maxHealth;
        int healthWidth = (int) (width * healthPercent);

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Handle when health is 0
        if (currentHealth <= 0) {
            if (deathTime == -1) {
                deathTime = System.currentTimeMillis(); // Start timer for death
            }

            if (System.currentTimeMillis() - deathTime >= 3000) {
                if (counter != 0) { // Player has lives left
                    counter--;
                    System.out.println("Lives left: " + counter);

                    player.setX(); // Reset player position
                    player.setY();
                    player.resetHealth();
                } else {
                    // Handle end-game logic
                    System.out.println("Game over!");
                }
                deathTime = -1; // Reset death timer after respawn
            } else {
                System.out.println("Waiting 3 seconds before respawning...");
                return; // Exit early to enforce delay
            }
        } else {
            deathTime = -1; // Reset death timer if health is above 0
        }

        // Draw health bar based on health
        if (currentHealth == 100) { // Health power-up, adds health past 100
            g.setColor(Color.CYAN);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth >= 80) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth >= 30) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, healthWidth, height);
        } else if (currentHealth < 30) {
            g.setColor(Color.RED);
            g.fillRect(x, y, healthWidth, height);
        }
    }

//    public void draw(Graphics2D g, int x, int y) {
//        int currentHealth = player.getHealth();
//        float healthPercent = (float) currentHealth / maxHealth;
//        int healthWidth = (int) (width * healthPercent);
//
//        g.setColor(Color.GRAY);
//        g.fillRect(x, y, width, height);
//
//        g.setColor(Color.BLACK);
//        g.drawRect(x, y, width, height);
//
//        if (currentHealth <= 0) {
//            if (deathTime == -1) {
//                deathTime = System.currentTimeMillis();
//            }
//            if (counter != 0) {
//                counter--;
//                System.out.println("lives left: "+ counter);
//                player.setX();
//                player.setY();
//                player.resetHealth();
//            } else {
//                // end Game
//            }
//
//        } else if (currentHealth == 100) { // health power up, adds health past 100
//            g.setColor(Color.CYAN);
//            g.fillRect(x, y, healthWidth, height);
//        } else if (currentHealth >= 80) {
//            g.setColor(Color.GREEN);
//            g.fillRect(x, y, healthWidth, height);
//        } else if (currentHealth >= 30) {
//            g.setColor(Color.YELLOW);
//            g.fillRect(x, y, healthWidth, height);
//        } else if (currentHealth < 29) {
//            g.setColor(Color.RED);
//            g.fillRect(x, y, healthWidth, height);
//        }
//
//
//    }
}