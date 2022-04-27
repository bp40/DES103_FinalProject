package farmclicker.upgrades;

import farmclicker.utility.Player;

/**
 * control logic of buying upgrades and increasing coin income
 */
public class Item extends Upgrade {
    private final double baseCoinIncreaseRate;
    private double coinIncreaseRate;
    private int currentAmount;

    /**
     * @param name             name of the item upgrade
     * @param baseCost         starting cost of the item
     * @param coinIncreaseRate how much coin the item generates per second
     */
    public Item(String name, int baseCost, double coinIncreaseRate) {
        super(name, baseCost);
        this.coinIncreaseRate = coinIncreaseRate;
        this.baseCoinIncreaseRate = coinIncreaseRate;
        this.currentAmount = 0;
    }

    /**
     * @return current coin income per second of item
     */
    public double getCoinIncreaseRate() {
        return coinIncreaseRate;
    }

    /**
     * @param coinIncreaseRate rate of coin income per second must be more than 0
     */
    public void setCoinIncreaseRate(double coinIncreaseRate) {
        if (coinIncreaseRate < 0) {
            return;
        }
        this.coinIncreaseRate = coinIncreaseRate;
    }

    /**
     * @return get default value of coin income of item
     */
    public double getBaseCoinIncreaseRate() {
        return baseCoinIncreaseRate;
    }

    /**
     * @return get number of item that the player has
     */
    public int getCurrentAmount() {
        return currentAmount;
    }

    /**
     * @param amount the number of item that the player will have
     */
    public void setCurrentAmount(int amount) {
        currentAmount = amount;
    }

    /**
     * Increment the price and amount of item
     */
    @Override
    public void purchase() {

        if (!Player.canPurchase(this)) {
            return;
        }

        Player.purchase(this);
        this.currentAmount += 1;
        super.purchaseCost *= 1.15;
        Player.setIncomePerSecond(Player.getIncomePerSecond() + this.coinIncreaseRate);
    }

}
