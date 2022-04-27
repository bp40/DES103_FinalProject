package farmclicker.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * shows completed milestones to the player
 * loads achievement data from {@link AchievementAlertPane}
 */
public class AchievementProgressPanel extends JPanel {

    /**
     * list of achievements that is completed and shown on screen
     */
    private static List<Integer> shownAchievements;
    private static JLabel shownAchievementLabel;

    /**
     * generate an AchievementProgressPanel
     * loads and show completed achievement
     */
    public AchievementProgressPanel() {
        shownAchievements = new ArrayList<>();
        shownAchievementLabel = new JLabel();
        shownAchievementLabel.setForeground(Color.YELLOW);
        this.add(shownAchievementLabel);
        this.setBackground(Color.BLACK);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        loadAchievement();
        updateShownAchievement();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * refresh completed achievement and updates the milestone label
     */
    public static void updateShownAchievement() {
        loadAchievement();
        StringBuilder sb = new StringBuilder();
        sb.append("MILESTONES REACHED:");
        shownAchievements.forEach(achievement -> {
            sb.append(achievement).append(" ");
        });
        shownAchievementLabel.setText(sb.toString());

    }

    /**
     * copy over list of completed achievements from {@link AchievementAlertPane}
     */
    private static void loadAchievement() {
        if (AchievementAlertPane.completedMilestonesList != null) {
            AchievementAlertPane.completedMilestonesList.forEach(compAchievement -> {
                if (!shownAchievements.contains(compAchievement)) {
                    shownAchievements.add(compAchievement);
                }
            });
        }
    }

    /**
     * add and show new achievement
     *
     * @param achievement the achievement that will be added to list and shown
     */
    public static void addAchievement(int achievement) {
        shownAchievements.add(achievement);
        System.out.println("added " + achievement);
    }

}
