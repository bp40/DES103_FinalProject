package farmclicker.ui;

import farmclicker.utility.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls achievement alerts and player's completed achievement
 */
public class AchievementAlertPane extends JOptionPane {

    /**
     * list of player's completed milestones
     */
    public static List<Integer> completedMilestonesList;
    /**
     * list of all achievements/milestone's amount of coin required
     */
    private final List<Integer> coinMilestoneList;
    /**
     * list of title of all the achievements
     */
    private final String[] MilestoneTitle;
    /**
     * list of all sub text of achievements
     */
    private final String[] MilestoneText;

    /**
     * generate an AchievementAlertPane
     * adds all the milestones,assign names and text, start a achievement refresh timer
     */
    public AchievementAlertPane() {
        coinMilestoneList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            coinMilestoneList.add((int) Math.pow(10, i));
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
        achievementCheckTimer.setCoalesce(false);
        achievementCheckTimer.start();

    }

    /**
     * clear player's achievement
     */
    public static void resetAchievement() {
        completedMilestonesList.clear();
    }

    private void showNewAchievement(String achievementText, String achievementTitle) {
        AchievementAlertPane.showMessageDialog(
                null,
                achievementTitle,
                achievementText,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Check if player has reached an achievement amount of coin
     */
    private void checkAchievement() {
        int currentCoin = (int) Player.getCurrentCoins();

        for (int i = 0; i < coinMilestoneList.size(); i++) {
            int currentMilestone = coinMilestoneList.get(i);
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

}
