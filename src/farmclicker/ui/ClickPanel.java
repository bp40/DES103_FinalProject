package farmclicker.ui;

import farmclicker.utility.Player;
import farmclicker.utility.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Base panel of game
 * Act as click area, control coin incrementation, and refreshing achievements
 */
public class ClickPanel extends JPanel {

    Image backgroundImage;
    JPanel coinPanel;
    JPanel achievementPanel;
    JLabel currentCoinLabel;
    JLabel currentCoinIncrementLabel;
    JButton resetButton;
    JButton saveButton;

    private static int clickCircleSize;
    private static int circleOffset;
    private final int[] treeXCoordinate;
    private final int[] treeYCoordinate;

    /**
     * creates a ClickPanel
     * acts as click area and set timers for screen repaints
     */
    public ClickPanel() {

        this.setLayout(new BorderLayout());
        this.addMouseListener(new ClickHandler());
        clickCircleSize = 170;
        circleOffset = 0;

        treeXCoordinate = new int[]{294, 324, 320, 340, 350, 351, 352, 357, 344, 315, 289, 268, 254, 262, 276, 303};
        treeYCoordinate = new int[]{278, 278, 205, 211, 209, 208, 195, 183, 163, 142, 147, 159, 183, 205, 212, 209};

        //Call method to  increment coin every second based on upgrades the player has
        Timer incrementTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.autoIncrementCoin();
            }
        });
        incrementTimer.setRepeats(true);
        incrementTimer.start();

        //Updates the screen every 50ms to refresh the coin amount on top of the screen
        Timer updateTimer = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        updateTimer.setRepeats(true);
        updateTimer.setCoalesce(false);
        updateTimer.start();

        try {
            backgroundImage = new ImageIcon(this.getClass().getResource("../../assets/gardenbkg.png")).getImage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.setBackground(Color.GRAY);
        }

        coinPanel = new JPanel(new FlowLayout());
        coinPanel.setBackground(new Color(97, 90, 86));
        currentCoinLabel = new JLabel();
        currentCoinLabel.setFont(new Font("Arial", Font.BOLD, 24));
        currentCoinLabel.setForeground(Color.white);
        currentCoinIncrementLabel = new JLabel("+0/s");
        currentCoinIncrementLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentCoinIncrementLabel.setForeground(Color.white);
        resetButton = new JButton("RESET");
        saveButton = new JButton("SAVE");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Game reset!");
                if (choice == JOptionPane.YES_OPTION) {
                    SaveManager.resetSave();
                    AchievementProgressPanel.refreshAchievementPanel();
                }

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveManager.saveProgress();
                JOptionPane.showMessageDialog(null, "Game saved!");
            }
        });

        coinPanel.add(currentCoinLabel);
        coinPanel.add(currentCoinIncrementLabel);
        coinPanel.add(resetButton);
        coinPanel.add(saveButton);

        this.add(coinPanel, BorderLayout.NORTH);

        achievementPanel = new AchievementProgressPanel();

        Timer achievementRefreshTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AchievementProgressPanel.updateShownAchievement();
                achievementPanel.repaint();
            }
        });
        achievementRefreshTimer.setRepeats(true);
        achievementRefreshTimer.start();

        this.add(achievementPanel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);

        g.setColor(Color.BLACK);
        g.fillOval(220 + circleOffset, 130 + circleOffset, clickCircleSize + 10, clickCircleSize + 10);

        g.setColor(new Color(52, 163, 24));
        g.fillOval(225 + circleOffset, 135 + circleOffset, clickCircleSize, clickCircleSize);

        g.setColor(Color.white);
        g.drawPolygon(treeXCoordinate, treeYCoordinate, treeXCoordinate.length);

        g.drawLine(320, 205, 324, 185);
        g.drawLine(312, 205, 318, 185);
        g.drawLine(300, 208, 297, 185);
        g.drawLine(307, 207, 304, 185);

        currentCoinLabel.setText(String.format("%.1f", Player.getCurrentCoins()));
        currentCoinIncrementLabel.setText("+" + String.format("%.1f", Player.getIncomePerSecond()) + "/s");

    }

    /**
     * Listen for clicks, 0.05 percent chance to trigger superClick();
     */
    private class ClickHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            double criticalChance = Math.random();
            if (criticalChance < 0.05) {
                Player.superClick();
                System.out.println("Super clicked!");
                return;
            }
            clickCircleSize = 160;
            circleOffset = 5;
            repaint();
            Timer clickAnimation = new Timer(55, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    circleOffset = 0;
                    clickCircleSize = 170;
                }
            });
            clickAnimation.setCoalesce(false);
            if (clickAnimation.isRunning()) {
                clickAnimation.restart();
            } else {
                clickAnimation.start();
            }

            Player.click();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

}
