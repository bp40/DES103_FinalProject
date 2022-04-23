package farmclicker.ui;

import farmclicker.upgrades.Item;
import farmclicker.utility.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpgradeItemLabel extends JLabel {

    private final Item item;
    private final JLabel costLabel;
    private Color hoverBackgroundColor = Color.GRAY;

    public UpgradeItemLabel(Item upgradeItem) {

        this.item = upgradeItem;
        this.setLayout(new BorderLayout());

        this.addMouseListener(new BuyHandler());
        this.setFont(new Font("Arial", Font.BOLD, 14));
        this.setBackground(hoverBackgroundColor);
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setText(item.name);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        costLabel = new JLabel("Cost : " + item.purchaseCost);
        costLabel.setBorder(new EmptyBorder(25, 0, 0, 0));
        costLabel.setForeground(Color.yellow);
        this.add(costLabel, BorderLayout.SOUTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        costLabel.setText("Cost : " + item.purchaseCost);
        this.setBackground(hoverBackgroundColor);
    }

    private class BuyHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (Player.canPurchase(item)) {
                Player.purchase(item);
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
