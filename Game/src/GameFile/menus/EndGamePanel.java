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
        String[] maps = {"level1", "level2", "level3"};

        // Map selection dropdown
        JComboBox<String> mapSelector = new JComboBox<>(maps);
        mapSelector.setFont(new Font("Courier New", Font.PLAIN, 18));
        mapSelector.setBounds(150, 150, 200, 40);
        mapSelector.addActionListener(e -> {
            String selectedMap = (String) mapSelector.getSelectedItem();
            System.out.println("Selected Map: " + selectedMap);
            lf.setSelectedMap(selectedMap);

        });

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 250, 200, 50);
        start.addActionListener((actionEvent -> {
                lf.getSelectedMap();
                this.lf.setFrame("game");
        }));


        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 350, 200, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(mapSelector);
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
