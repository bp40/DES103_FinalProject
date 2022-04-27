package farmclicker.upgrades;

import farmclicker.utility.Player;

public class Item extends Upgrade {
    private final double baseCoinIncreaseRate;
    private double coinIncreaseRate;
    private int currentAmount;

    public Item(String name, int baseCost, double coinIncreaseRate) {
        super(name, baseCost);
        this.coinIncreaseRate = coinIncreaseRate;
        this.baseCoinIncreaseRate = coinIncreaseRate;
        this.currentAmount = 0;
    }

    public double getCoinIncreaseRate() {
        return coinIncreaseRate;
    }

    public double getBaseCoinIncreaseRate() {
        return baseCoinIncreaseRate;
    }

    public void setCoinIncreaseRate(double coinIncreaseRate) {
        if (coinIncreaseRate < 0) {
            return;
        }
        this.coinIncreaseRate = coinIncreaseRate;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

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

        this.currentAmount += 1;
        super.purchaseCost *= 1.15;
        Player.setCurrentCoins(Player.getCurrentCoins() - this.purchaseCost);
        Player.setIncomePerSecond(Player.getIncomePerSecond() + this.coinIncreaseRate);
    }

}
