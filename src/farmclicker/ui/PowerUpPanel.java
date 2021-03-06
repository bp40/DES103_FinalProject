package farmclicker.ui;

import farmclicker.upgrades.PowerUp;
import farmclicker.utility.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * {@link JPanel} that handles loading power ups from file and showing them on screen
 */
public class PowerUpPanel extends JPanel {

    List<PowerUp> powerUpList;

    /**
     * Generate {@link JPanel} with GridLayout
     * sets timer to update every 2 seconds
     */
    public PowerUpPanel() {

        powerUpList = new ArrayList<>();
        this.setLayout(new GridLayout(4, 3));
        loadPowerUpsFromFile();

        Timer updateTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        updateTimer.setRepeats(true);
        updateTimer.setCoalesce(false);
        updateTimer.start();

    }

    /**
     * load name, price, targetUpgrade from PowerUpsInfo.txt
     */
    private void loadPowerUpsFromFile() {
        try {
            Scanner fileReader = new Scanner(SaveManager.getFile("../../assets/PowerUpsInfo.txt"));

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");

                String powerUpName = parts[0];
                int purchasePrice = Integer.parseInt(parts[1]);
                String targetUpgradeName = parts[2];

                powerUpList.add(new PowerUp(powerUpName, purchasePrice, targetUpgradeName));
            }

            powerUpList.forEach(powerUp -> {
                this.add(new PowerUpItemLabel(powerUp));
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
