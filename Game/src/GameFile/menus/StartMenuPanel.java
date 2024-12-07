package GameFile.menus;

import GameFile.Launcher;
import GameFile.utils.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;

        // Load the menu background image
        menuBackground = AssetManager.getSprite("menu");

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // Map selection dropdown
        String[] maps = {"level1", "level2", "level3"};
        JComboBox<String> mapSelector = new JComboBox<>(maps);
        mapSelector.setFont(new Font("Courier New", Font.PLAIN, 18));
        mapSelector.setBounds(150, 150, 150, 40);
        mapSelector.addActionListener(e -> {
            String selectedMap = (String) mapSelector.getSelectedItem();
            System.out.println("Selected Map: " + selectedMap);
            lf.setSelectedMap(selectedMap); // Set the selected map in Launcher
        });

        // Start button
        JButton start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 200, 150, 50);
        start.addActionListener(actionEvent -> {
            // Ensure a map is selected before starting the game
            String selectedMap = lf.getSelectedMap();
            if (selectedMap == null || selectedMap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a map before starting!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                System.out.println("Starting game with map: " + selectedMap);
                lf.setFrame("game"); // Switch to the game frame
            }
        });

        // Exit button
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 270, 150, 50);
        exit.addActionListener(actionEvent -> lf.closeGame());

        // Volume slider
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

        // Volume button
        JButton volumeButton = new JButton("Volume");
        volumeButton.setFont(new Font("Courier New", Font.BOLD, 18));
        volumeButton.setBounds(150, 350, 150, 50);
        volumeButton.addActionListener(e -> volumeSlider.setVisible(!volumeSlider.isVisible()));

        // Help button
        JButton helpButton = new JButton("Help");
        helpButton.setFont(new Font("Courier New", Font.BOLD, 14));
        helpButton.setBounds(350, 10, 100, 30); // Adjust position for top-right corner
        helpButton.addActionListener(e -> {
            // Display instructions on how to play
            String instructions = """
            Welcome to the Game!
            For Dog player:
            - Use the W, S, A, D keys to move your character Up, Down, Left, Right.
            - Press Spacebar to shoot. The direction will be based on your movement.
            For Cat player:
            - Use the arrow keys Up, Down, Left, Right to move your character.
            - Press Shift to shoot. The direction will be based on your movement.
            All:
            - Collect bags to increase health, speed, and damage.

            Good luck and have fun!
            """;
            JOptionPane.showMessageDialog(this, instructions,
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add components to the panel
        this.add(mapSelector);
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
