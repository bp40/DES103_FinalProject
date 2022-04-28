package farmclicker.ui;

import farmclicker.upgrades.Item;
import farmclicker.utility.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * {@link JLabel} that represents each upgrade available
 * handles purchasing of the upgrade
 */
public class UpgradeItemLabel extends JLabel {

    /**
     * Item of this label
     */
    private final Item item;
    /**
     * {@link JLabel} that shows current cost
     */
    private final JLabel costLabel;
    /**
     * Background of upgrade that change depending on {@link Player}'s coin amount
     */
    private Color hoverBackgroundColor = Color.GRAY;

    /**
     * creates and show Upgrade label
     *
     * @param upgradeItem Item that will be assigned to this {@link JLabel}
     */
    public UpgradeItemLabel(Item upgradeItem) {

        this.item = upgradeItem;
        this.setLayout(new BorderLayout(5, 20));

        this.addMouseListener(new BuyHandler());
        this.setFont(new Font("Arial", Font.BOLD, 14));
        this.setBackground(hoverBackgroundColor);
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setText(item.name);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        costLabel = new JLabel("Cost : " + item.currentCost);
        costLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        costLabel.setForeground(Color.yellow);
        this.add(costLabel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        costLabel.setText("Cost : " + item.currentCost);
        this.setBackground(hoverBackgroundColor);
        this.setToolTipText("Current gain : " + (item.getCoinIncreaseRate() * item.getCurrentAmount()));
    }

    /**
     * Mouse click handler that handles click to buy from player
     *
     * @see MouseListener
     */
    private class BuyHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (Player.canPurchase(item)) {
                item.purchase();
            }
            if (Player.canPurchase(item)) {
                hoverBackgroundColor = new Color(0, 143, 17);
            } else {
                hoverBackgroundColor = Color.RED;
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (Player.canPurchase(item)) {
                hoverBackgroundColor = new Color(0, 143, 17);
            } else {
                hoverBackgroundColor = Color.RED;
            }

            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hoverBackgroundColor = Color.GRAY;
            repaint();
        }
    }

}
