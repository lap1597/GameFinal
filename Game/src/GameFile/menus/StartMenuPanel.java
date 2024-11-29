package GameFile.menus;

import GameFile.Launcher;
import GameFile.utils.AssetManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;

        // Load the menu background image
        menuBackground = AssetManager.getSprite("menu");


        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // Start button
        JButton start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 200, 150, 50);
        start.addActionListener(actionEvent -> this.lf.setFrame("game"));

        // Exit button
        JButton exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 270, 150, 50);
        exit.addActionListener(actionEvent -> this.lf.closeGame());

//        // Volume slider
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setBounds(150, 400, 150, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setVisible(false);
        volumeSlider.addChangeListener(e -> {
            int volume = volumeSlider.getValue();

            adjustVolume(volume);
        });

        JButton volumeButton = new JButton("Volume");
        volumeButton.setFont(new Font("Courier New", Font.BOLD, 18));
        volumeButton.setBounds(150, 350, 150, 50);
        volumeButton.addActionListener(e -> {
            volumeSlider.setVisible(!volumeSlider.isVisible());
        });
        // Help button
        JButton helpButton = new JButton("Help");
        helpButton.setFont(new Font("Courier New", Font.BOLD, 14));
        helpButton.setBounds(350, 10, 100, 30); // Adjust position for top-right corner
        helpButton.addActionListener(e -> {
            // Display instructions on how to play
            String instructions = """
        Welcome to the Game!
        For Dog player:
        - Use the W,S,A,D keys to move your character Up, Down, Left, Right.
        - Press  Spacebar to shoot and the direction will be based on your movement.
        For Cat player:
        - Use the arrows Up, Down, Left,Right to move your character Up, Down, Left, Right.
        - Press Shift to shoot, and the direction will be based on your movement..
        All: 
        - Collect bags will increase your health, speed, and damage.
       

        Good luck and have fun!
        """;
            JOptionPane.showMessageDialog(this, instructions,
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add components to the panel
        this.add(start);
        this.add(exit);
        this.add(volumeButton);
        this.add(volumeSlider);
        this.add(helpButton);
    }

    private void adjustVolume(int volume) {
        float scaledVolume = volume / 100.0f;
        try {
            // Adjust the volume of the background sound
            AssetManager.getSound("bgSound").setVolume(scaledVolume);
            System.out.println("Volume adjusted to: " + volume + "%");
        } catch (Exception e) {
            System.err.println("Error adjusting volume: " + e.getMessage());
        }
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
}
