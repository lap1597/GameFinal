package GameFile.menus;

import GameFile.Launcher;
import GameFile.utils.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;

        menuBackground = AssetManager.getSprite("menu");

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 250, 50);
        start.addActionListener((actionEvent -> this.lf.setFrame("game")));


        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 250, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(start);
        this.add(exit);

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure the panel's children are rendered
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (menuBackground != null) {
            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            g2.drawImage(menuBackground, 0, 0, panelWidth, panelHeight, null);
        }
    }
    public void winner(BufferedImage img){
        menuBackground = img;
    }

}
