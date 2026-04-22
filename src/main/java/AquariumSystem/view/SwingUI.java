package AquariumSystem.view;


import AquariumSystem.controller.AquariumController;
import AquariumSystem.exception.FeedingException;
import AquariumSystem.exception.ValidationException;
import AquariumSystem.interfaces.Fish;
import AquariumSystem.model.WaterQuality;
import AquariumSystem.simpleaquarium.SimpleFish;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingUI {

    private AquariumController controller;

    private JFrame frame;
    private JTextArea outputArea;
    private JComboBox<Fish> fishDropdown;
    private FishPanel fishPanel;

    public SwingUI(AquariumController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Dianas Akvarium");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tegnepanel
        fishPanel = new FishPanel();
        frame.add(fishPanel, BorderLayout.CENTER);

        // Top panel (dropdown + info)
        JPanel topPanel = new JPanel();

        fishDropdown = new JComboBox<>();
        refreshFishDropdown();

        topPanel.add(new JLabel("Fisk:"));
        topPanel.add(fishDropdown);

        frame.add(topPanel, BorderLayout.NORTH);

        // Output
        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Knapper
        JPanel buttonPanel = new JPanel();

        JButton feedBtn = new JButton("Fodr");
        JButton waterBtn = new JButton("Vandskift");
        JButton healthBtn = new JButton("Health");
        JButton addBtn = new JButton("Tilføj fisk");
        JButton removeBtn = new JButton("Slet fisk");
        JButton showWaterBtn = new JButton("Sidste vandskift");

        buttonPanel.add(feedBtn);
        buttonPanel.add(waterBtn);
        buttonPanel.add(healthBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(showWaterBtn);

        frame.add(buttonPanel, BorderLayout.PAGE_END);

        // Events
        feedBtn.addActionListener(e -> feedFish());
        waterBtn.addActionListener(e -> changeWater());
        healthBtn.addActionListener(e -> healthCheck());
        addBtn.addActionListener(e -> addFish());
        removeBtn.addActionListener(e -> removeFish());
        showWaterBtn.addActionListener(e -> showLastWater());

        frame.setVisible(true);
    }

    // Opdater dropdown + tegning
    private void refreshFishDropdown() {
        fishDropdown.removeAllItems();
        for (Fish f : controller.getFishList()) {
            fishDropdown.addItem(f);
        }
        fishPanel.repaint();
    }

    private void feedFish() {
        try {
            controller.feedFish();
            print("Fiskene fodret");
        } catch (FeedingException e) {
            showError(e.getMessage());
        }
    }

    private void changeWater() {
        try {
            String note = JOptionPane.showInputDialog(frame, "Note:");

            if (note == null) return;

            // Dropdown til valg af vandkvalitet
            WaterQuality quality = (WaterQuality) JOptionPane.showInputDialog(
                    frame,
                    "Vælg vandkvalitet:",
                    "Vandkvalitet",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    WaterQuality.values(), // enum bruges direkte
                    WaterQuality.GOOD      // default
            );

            if (quality != null) {
                controller.changeWater(note, quality);
                print("Vand skiftet (" + quality + ")");
            }

        } catch (ValidationException e) {
            showError(e.getMessage());
        }
    }



    private void healthCheck() {
        try {
            Fish fish = (Fish) fishDropdown.getSelectedItem();
            String note = JOptionPane.showInputDialog("Sundhedsnote:");

            if (fish != null && note != null) {
                controller.registerFishHealth(fish, note);
                print("Health check på " + fish.getName());
            }
        } catch (ValidationException e) {
            showError(e.getMessage());
        }
    }

    private void addFish() {
        String name = JOptionPane.showInputDialog("Navn på fisk:");

        if (name != null && !name.isEmpty()) {
            controller.addFish(new SimpleFish(name));
            refreshFishDropdown();
            print("Fisk tilføjet: " + name);
        }
    }

    private void removeFish() {
        Fish fish = (Fish) fishDropdown.getSelectedItem();

        if (fish != null) {
            int index = fishDropdown.getSelectedIndex();
            controller.removeFish(index);
            refreshFishDropdown();
            print("Fisk slettet");
        }
    }

    private void showLastWater() {
        var date = controller.getLastWaterChange();

        if (date == null) {
            print("Ingen vandskift endnu");
        } else {
            print("Sidste vandskift: " + date);
        }
    }

    private void print(String msg) {
        outputArea.append(msg + "\n");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Fejl", JOptionPane.ERROR_MESSAGE);
    }

    // Custom panel til tegning
    class FishPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            List<Fish> fishList = controller.getFishList();

            int x = 50;
            int y = 100;

            for (Fish fish : fishList) {

                // Krop
                g.setColor(Color.ORANGE);
                g.fillOval(x, y, 60, 30);

                // Hale
                int[] xPoints = {x + 60, x + 80, x + 60};
                int[] yPoints = {y, y + 15, y + 30};
                g.fillPolygon(xPoints, yPoints, 3);

                // Øje
                g.setColor(Color.BLACK);
                g.fillOval(x + 10, y + 10, 5, 5);

                // Navn
                g.drawString(fish.getName(), x, y - 5);

                x += 120; // næste fisk
            }
        }
    }
}