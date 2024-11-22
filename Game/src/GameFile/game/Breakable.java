package GameFile.game;

import java.awt.image.BufferedImage;

public class Breakable extends GameObject {
    private BufferedImage sprite;
    public Breakable(int x, int y, BufferedImage sprite) {
        super(x,y,sprite);

        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

}
