package farmclicker.utility;

import farmclicker.ui.AchievementAlertPane;
import farmclicker.ui.UpgradesPanel;
import farmclicker.upgrades.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

/**
 * Helper class that handles saving,loading and resetting game progress
 */
public class SaveManager {

    /**
     * @param relativeFilePath file path relative to the {@link SaveManager} file
     * @return File object retrieved from relative path
     */
    public static File getFile(String relativeFilePath) {
        URL fileURL = SaveManager.class.getResource(relativeFilePath);

        try {
            return new File(fileURL.getPath());
        } catch (Exception e) {
            URL url = SaveManager.class.getResource("../../assets");
            System.out.println("Cannot find save file, creating new one at: " + url);
            String[] pathParts = relativeFilePath.split("/");
            return new File(url.getPath(), pathParts[pathParts.length - 1]);
        }

    }

    /**
     * Saves current game progress, creates new file if not exist. Then recalculate player's income
     * Save is encoded in base64 to prevent easy cheating
     */
    public static void saveProgress() {

        try {
            try (FileWriter writer = new FileWriter(getFile("../../assets/save.txt").getAbsolutePath())) {
                StringBuilder sb = new StringBuilder();
                sb.append(Player.getCurrentCoins()).append("\n");

                //for each upgrade the game has get price and append to save string
                UpgradesPanel.upgradeList.forEach(item -> {
                    sb.append(item.getCurrentAmount()).append("\n");
                });

                //signify beginning of achievement section of save
                sb.append("ACHIEVEMENT").append("\n");

                //add gotten achievement to save string
                if (!AchievementAlertPane.completedMilestonesList.isEmpty()) {
                    AchievementAlertPane.completedMilestonesList.forEach(achievement -> {
                        sb.append(achievement).append("\n");
                    });
                }
                //Encode save string to base64 then write it to file
                String encodedSaveString = Base64.getEncoder().encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
                writer.write(encodedSaveString);
            }

            Player.recalculateIncome();
            System.out.println("Auto saved successful");
        } catch (IOException IOe) {
            IOe.printStackTrace();
        }

    }

    /**
     * Loads current game progress from save file, then recalculate player's income
     * Decodes the save file from Base64 to string then assigns to appropriate variables.
     */
    public static void loadSaveFromFile() {

        try {
            File file = getFile("../../assets/save.txt");
            Scanner fileReader = new Scanner(file);
            System.out.println("Loaded save from : " + file);
            if (fileReader.hasNextLine()) {
                byte[] decodedByte = Base64.getDecoder().decode(fileReader.nextLine());
                String decodedString = new String(decodedByte);
                String[] saveDataArray = decodedString.split("\n");

                int currentIndex = 0;

                //Debug Arrays.stream(saveDataArray).forEach(item-> System.out.println("!" + item + "!"));

                Player.setCurrentCoins(Double.parseDouble(saveDataArray[currentIndex++]));//Sets player coin to saved amount

                //loops through save until hit ACHIEVEMENT section, updates amount of upgrade in upgradeList
                for (int i = currentIndex; i < saveDataArray.length; i++) {
                    if (saveDataArray[i].equals("ACHIEVEMENT")) {
                        currentIndex = i + 1;
                        break;
                    } else {
                        Item upgrade = UpgradesPanel.upgradeList.get(i - 1);
                        int amount = Integer.parseInt(saveDataArray[i]);
                        upgrade.setCurrentAmount(amount);
                        int finalPrice = upgrade.baseCost;
                        for (int j = 0; j < amount; j++) {
                            finalPrice *= 1.15;
                        }
                        upgrade.currentCost = finalPrice;
                    }
                }

                //loops through the rest of the save and write list of already gotten achievement
                for (int i = currentIndex; i < saveDataArray.length; i++) {
                    AchievementAlertPane.completedMilestonesList.add(Integer.valueOf(saveDataArray[i]));
                }

                Player.recalculateIncome();
                System.out.println("Loaded from file successfully");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Save not file found");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * rewrites save file to zero (reset save)
     */
    public static void resetSave() {
        try (FileWriter writer = new FileWriter(getFile("../../assets/save.txt"))) {
            writer.write("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UpgradesPanel.resetUpgrades();
            Player.setCurrentCoins(0);
            Player.recalculateIncome();
            AchievementAlertPane.resetAchievement();
        }

    }

}
