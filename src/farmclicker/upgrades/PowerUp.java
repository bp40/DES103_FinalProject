package farmclicker.upgrades;

import farmclicker.ui.UpgradesPanel;
import farmclicker.utility.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PowerUp extends Upgrade {

    private final String targetUpgrade;
    private final int cooldownTimeInSeconds = 60;
    public boolean isOnCooldown;

    public PowerUp(String name, int purchaseCost, String targetUpgrade) {
        super(name, purchaseCost);
        this.targetUpgrade = targetUpgrade;
        this.isOnCooldown = false;
    }

    /**
     * @return Item from upgradeList that has the same name
     */
    private Item getUpgradeItemInList() {
        for (Item item : UpgradesPanel.upgradeList) {
            if (targetUpgrade.equals(item.name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * set power up on cooldown for 60 seconds
     * Temporarily increase coin increase rate by 25%
     */
    @Override
    public void purchase() {
        this.isOnCooldown = true;
        System.out.println(name + " on cooldown");
        Player.purchase(this);
        System.out.println("Purchased " + name + " for " + purchaseCost);
        Item item = this.getUpgradeItemInList();
        if (item != null) {
            item.setCoinIncreaseRate(item.getBaseCoinIncreaseRate() * 1.25);
            Player.recalculateIncome();
        }
        Timer cooldownTimer = new Timer(cooldownTimeInSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOnCooldown = false;
                System.out.println(name + " cooldown lifted");

                Item item = getUpgradeItemInList();
                item.setCoinIncreaseRate(item.getBaseCoinIncreaseRate());
                Player.recalculateIncome();
            }
        });
        cooldownTimer.start();

    }
}
