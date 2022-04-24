package farmclicker.utility;

import farmclicker.ui.AchievementAlertPane;
import farmclicker.ui.UpgradesPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
     * Save is encoded in base64 to prevent easy cheating
     */
    public static void saveProgress() {

        try {
            try (FileWriter writer = new FileWriter(getSaveFile().getAbsolutePath())) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads current game progress from save file, then recalculate player's income
     * Decodes the save file from Base64 to string then assigns to appropriate variables.
     */
    public static void loadSaveFromFile() {

        try {
            Scanner fileReader = new Scanner(getSaveFile());

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
                        UpgradesPanel.upgradeList.get(i - 1).setCurrentAmount(Integer.parseInt(saveDataArray[i]));
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
            throw new RuntimeException(e);
        }

    }

    /**
     * rewrites save file to zero (reset save)
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
            AchievementAlertPane.resetAchievement();
        }

    }

}
