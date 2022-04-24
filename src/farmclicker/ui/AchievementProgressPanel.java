package farmclicker.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementProgressPanel extends JPanel {

    private static List<Integer> shownAchievements;
    private static JLabel shownAchievementLabel;

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

    public static void updateShownAchievement() {
        loadAchievement();
        StringBuilder sb = new StringBuilder();
        sb.append("MILESTONES REACHED:");
        shownAchievements.forEach(achievement -> {
            sb.append(achievement).append(" ");
        });
        shownAchievementLabel.setText(sb.toString());

    }

    private static void loadAchievement() {
        if (AchievementAlertPane.completedMilestonesList != null) {
            AchievementAlertPane.completedMilestonesList.forEach(compAchievement -> {
                if (!shownAchievements.contains(compAchievement)) {
                    shownAchievements.add(compAchievement);
                }
            });
        }
    }

    public static void addAchievement(int achievement) {
        shownAchievements.add(achievement);
        System.out.println("added " + achievement);
    }

}
