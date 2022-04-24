package farmclicker.ui;

import farmclicker.utility.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AchievementAlertPane extends JOptionPane {

    private final List<Integer> cookieMilestoneList;
    private final String[] MilestoneTitle;
    private final String[] MilestoneText;
    public static List<Integer> completedMilestonesList;

    public AchievementAlertPane() {
        cookieMilestoneList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            cookieMilestoneList.add((int) Math.pow(10, i));
        }
        MilestoneTitle = new String[]{
                "First 10!",
                "Getting busy",
                "Family Business",
                "Neighborhood Farm",
                "Mass Production",
                "Country wide shipment",
                "International Farm",
                "Global Overgrowth!"
        };
        MilestoneText = new String[]{
                "A great start for farming!",
                "Your hard work is slowly Paying off!",
                "Enough to give to others!",
                "Your whole town knows about your farm",
                "Selling plants like hot cakes",
                "Congrats! You're a big business now",
                "International Farming Conglomerate",
                "Your plants are taking over the world!"
        };
        completedMilestonesList = new ArrayList<>();

        Timer achievementCheckTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAchievement();
            }
        });
        achievementCheckTimer.setRepeats(true);
        achievementCheckTimer.start();

    }

    /**
     * Check if player has reached an achievement amount of coin
     */
    private void checkAchievement() {
        int currentCoin = (int) Player.getCurrentCoins();

        for (int i = 0; i < cookieMilestoneList.size(); i++) {
            int currentMilestone = cookieMilestoneList.get(i);
            if (completedMilestonesList.contains(currentMilestone)) {
                continue;
            }

            if (currentCoin >= currentMilestone) {
                completedMilestonesList.add(currentMilestone);
                AchievementProgressPanel.addAchievement(currentMilestone);
                showNewAchievement(MilestoneText[i], MilestoneTitle[i]);
                break;
            }
        }
    }

    private void showNewAchievement(String achievementText, String achievementTitle) {
        AchievementAlertPane.showMessageDialog(
                null,
                achievementTitle,
                achievementText,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void resetAchievement() {
        completedMilestonesList.clear();
    }

}
