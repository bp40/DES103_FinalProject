package farmclicker.ui;

import farmclicker.upgrades.Item;
import farmclicker.utility.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * {@link JPanel} that shows available upgrades and power ups
 */
public class UpgradesPanel extends JPanel {

    JPanel buyPowerUpPanel;
    JPanel buyUpgradePanel;
    /**
     * List of all purchasable upgrades
     */
    public static ArrayList<Item> upgradeList;

    /**
     * generate upgrades {@link JPanel}
     * loads upgrades/prices from file
     * add upgrades to {@link UpgradesPanel#upgradeList}
     */
    public UpgradesPanel() {

        this.setLayout(new BorderLayout());

        upgradeList = new ArrayList<>();
        loadUpgradeFromFile();
        buyUpgradePanel = new JPanel();
        buyUpgradePanel.setLayout(new GridLayout(upgradeList.size(), 1, 5, 5));
        for (Item upgradeItem : upgradeList) {
            buyUpgradePanel.add(new UpgradeItemLabel(upgradeItem));
        }
        buyUpgradePanel.setBackground(Color.BLACK);

        buyPowerUpPanel = new PowerUpPanel();

        this.add(buyPowerUpPanel, BorderLayout.NORTH);
        this.add(buyUpgradePanel, BorderLayout.CENTER);

    }


    /**
     * get the list of upgrades from upgradesInfo.txt
     */
    private void loadUpgradeFromFile() {

        try {
            Scanner fileReader = new Scanner(SaveManager.getFile("../../assets/upgradesInfo.txt"));
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");

                String upgradeName = parts[0];
                int initialPrice = Integer.parseInt(parts[1]);
                double perPieceIncrease = Double.parseDouble(parts[2]);

                Item item = new Item(upgradeName, initialPrice, perPieceIncrease);
                upgradeList.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * resets the amount of upgrades to zero
     */
    public static void resetUpgrades() {
        upgradeList.forEach(upgrade -> upgrade.setCurrentAmount(0));
    }

}
