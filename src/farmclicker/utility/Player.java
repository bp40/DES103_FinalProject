package farmclicker.utility;

import farmclicker.ui.UpgradesPanel;
import farmclicker.upgrades.Upgrade;

/**
 * Helper class for keeping track of coins and income
 */
public final class Player {

    private Player() {
    }

    private static final double perClickIncrease = 1;
    private static double currentCoins = 0;
    private static double incomePerSecond = 0;

    /**
     * @return current amount of coin
     */
    public static double getCurrentCoins() {
        return currentCoins;
    }

    /**
     * @param currentCoins {@link Player}'s new current coin
     */
    public static void setCurrentCoins(double currentCoins) {
        Player.currentCoins = currentCoins;
    }

    /**
     * recalculate the income then return income
     *
     * @return {@link Player}'s coin gain per second
     */
    public static double getIncomePerSecond() {
        recalculateIncome();
        return incomePerSecond;
    }

    /**
     * set player's income then recalculate the income
     *
     * @param income new player's income per second
     */
    public static void setIncomePerSecond(double income) {

        if (income > 0) {
            incomePerSecond = income;
        }

        Player.recalculateIncome();

    }

    /**
     * Increase the player's coin by income per second
     * supposed to be run once a second
     */
    public static void autoIncrementCoin() {
        currentCoins += incomePerSecond;
    }

    /**
     * @param upgrade upgrade object that will be compared
     * @return boolean of whether player has enough coins
     */
    public static boolean canPurchase(Upgrade upgrade) {
        return upgrade.currentCost <= currentCoins;
    }

    /**
     * Deducts item's price from player's coins
     * Then, Increment player's coin rate
     *
     * @param upgrade Item that will be bought by the player.
     */
    public static void purchase(Upgrade upgrade) {
        if (!canPurchase(upgrade)) {
            return;
        }

        currentCoins -= upgrade.currentCost;
    }

    /**
     * Recalculate player's income based on upgrades and their amounts;
     */
    public static void recalculateIncome() {

        incomePerSecond = 0;

        UpgradesPanel.upgradeList.forEach(item -> {
            incomePerSecond += (item.getCoinIncreaseRate() * item.getCurrentAmount());
        });
    }

    /**
     * Increment player's coin base on perClickIncrease
     */
    public static void click() {
        currentCoins += perClickIncrease;
    }

    /**
     * Increment player's coin base on twice of perClickIncrease
     */
    public static void superClick() {
        currentCoins += (perClickIncrease * 2);
    }

}
