package farmclicker.uilogic;

import farmclicker.upgrades.Item;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class UpgradesPanel extends JPanel {

    JPanel buyPowerUpPanel;
    JPanel buyUpgradePanel;
    protected static ArrayList<Item> upgradeList;

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

        buyPowerUpPanel = new JPanel();
        buyPowerUpPanel.setBackground(Color.RED);
        buyPowerUpPanel.setLayout(new GridLayout(4, 2, 0, 3));

        this.add(buyPowerUpPanel, BorderLayout.NORTH);
        this.add(buyUpgradePanel, BorderLayout.CENTER);

    }


    private void loadUpgradeFromFile() {

        try {
            URL url = getClass().getResource("../../assets/gameInfo.txt");
            File upgradeInfoFile = new File(url.getPath());
            Scanner fileReader = new Scanner(upgradeInfoFile);
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

    protected static void resetUpgrades(){
        upgradeList.forEach(upgrade -> upgrade.setCurrentAmount(0));
    }

}
