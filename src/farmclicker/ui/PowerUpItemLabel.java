package farmclicker.ui;

import farmclicker.upgrades.PowerUp;
import farmclicker.utility.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * {@link JLabel} for showing and buying power ups
 */
public class PowerUpItemLabel extends JLabel {

    /**
     * Power up of this label
     */
    PowerUp powerUp;
    /**
     * Color of the name of label text
     */
    Color currentColor = Color.RED;

    /**
     * @param powerUp powerUp that will be assigned to this label
     */
    public PowerUpItemLabel(PowerUp powerUp) {
        this.powerUp = powerUp;

        this.setBackground(Color.blue);
        this.setForeground(currentColor);
        this.setText(powerUp.name);
        this.setToolTipText("Cost : " + powerUp.currentCost);
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
            this.setToolTipText("ON COOLDOWN Cost : " + powerUp.currentCost);
        } else if (Player.canPurchase(powerUp)) {
            currentColor = new Color(0, 143, 17);
            this.setToolTipText("Cost : " + powerUp.currentCost);
        } else {
            currentColor = Color.RED;
        }

        this.setForeground(currentColor);
    }


}
