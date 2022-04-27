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
    JPanel AchievementPanel;
    JLabel currentCoinLabel;
    JLabel currentCoinIncrementLabel;
    JButton resetButton;
    JButton saveButton;

    /**
     * creates a ClickPanel
     * acts as click area and set timers for screen repaints
     */
    public ClickPanel() {

        this.setLayout(new BorderLayout());
        this.addMouseListener(new ClickHandler());

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
        Timer updateTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        updateTimer.setRepeats(true);
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

        AchievementPanel = new AchievementProgressPanel();

        Timer achievementRefreshTimer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AchievementProgressPanel.updateShownAchievement();
                AchievementPanel.repaint();
            }
        });
        achievementRefreshTimer.setRepeats(true);
        achievementRefreshTimer.start();

        this.add(AchievementPanel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);

        g.setColor(Color.BLACK);
        g.fillOval(220, 130, 180, 180);

        g.setColor(new Color(52, 163, 24));
        g.fillOval(225, 135, 170, 170);

        currentCoinLabel.setText(String.format("%.1f", Player.getCurrentCoins()));
        currentCoinIncrementLabel.setText("+" + String.format("%.1f", Player.getIncomePerSecond()) + "/s");

    }

    /**
     * Listen for clicks, 0.05 percent chance to trigger superClick();
     */
    protected static class ClickHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            double criticalChance = Math.random();
            if(criticalChance < 0.05){
                Player.superClick();
                System.out.println("Super clicked!");
                return;
            }
            Player.click();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
