package farmclicker.utility;

import farmclicker.ui.UpgradesPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SaveManager {

    /**
     * @return Absolute file path (X://a/assets/save.txt) to save.txt in assets folder
     */
    private static File getSaveFile() {
        URL url = SaveManager.class.getResource("../../assets/save.txt");

        return new File(url.getPath());
    }

    /**
     * Saves current game progress, creates new file if not exist. Then recalculate player's income
     */
    public static void saveProgress() {

        try {
            try (FileWriter writer = new FileWriter(getSaveFile().getAbsolutePath())) {
                writer.write(Player.getCurrentCoins() + "\n");
                UpgradesPanel.upgradeList.forEach(item -> {
                    try {
                        writer.write(item.getCurrentAmount() + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            Player.recalculateIncome();
            System.out.println("Auto saved successful");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads current game progress from save file, then recalculate player's income
     */
    public static void loadSaveFromFile() {

        try {
            Scanner fileReader = new Scanner(getSaveFile());

            Player.setCurrentCoins(Double.parseDouble(fileReader.nextLine()));

            UpgradesPanel.upgradeList.forEach(item -> {
                item.setCurrentAmount(Integer.parseInt(fileReader.nextLine()));
            });

            Player.recalculateIncome();
            System.out.println("Loaded from file successfully");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * rewrites save file to zero
     */
    public static void resetSave() {
        try (FileWriter writer = new FileWriter(getSaveFile())) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UpgradesPanel.resetUpgrades();
            Player.setCurrentCoins(0);
            Player.recalculateIncome();
        }

    }

}
