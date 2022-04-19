package farmclicker.uilogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SaveManager {

    //Gets the file path for save file
    private static String getAbsoluteFilePath() {
        URL url = SaveManager.class.getResource("../../assets/save.txt");
        File file = new File(url.getPath());

        return file.getAbsolutePath();
    }

    public static void saveProgress() {

        try {
            try (FileWriter writer = new FileWriter(getAbsoluteFilePath())) {
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

    public static void loadSaveFromFile() {

        URL url = SaveManager.class.getResource("../../assets/save.txt");
        File upgradeInfoFile = new File(url.getPath());
        try {
            Scanner fileReader = new Scanner(upgradeInfoFile);

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

    public static void resetSave() {
        try (FileWriter writer = new FileWriter(getAbsoluteFilePath())) {
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
