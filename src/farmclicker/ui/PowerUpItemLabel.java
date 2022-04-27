package farmclicker.ui;

import farmclicker.upgrades.PowerUp;
import farmclicker.utility.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PowerUpItemLabel extends JLabel {

    PowerUp powerUp;
    Color currentColor = Color.RED;

    public PowerUpItemLabel(PowerUp powerUp) {
        this.powerUp = powerUp;

        this.setBackground(Color.blue);
        this.setForeground(currentColor);
        this.setText(powerUp.name);
        this.setToolTipText("Cost : " + powerUp.purchaseCost);
        this.setBorder(BorderFactory.createLineBorder(Color.black));


        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked to buy " + powerUp.name);
                if (Player.canPurchase(powerUp)) {
                    powerUp.purchase();
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

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (powerUp.isOnCooldown) {
            currentColor = Color.orange;
            this.setToolTipText("ON COOLDOWN Cost : " + powerUp.purchaseCost);
        } else if (Player.canPurchase(powerUp)) {
            currentColor = new Color(0, 143, 17);
            this.setToolTipText("Cost : " + powerUp.purchaseCost);
        } else {
            currentColor = Color.RED;
        }

        this.setForeground(currentColor);
    }


}
