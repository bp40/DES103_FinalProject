package farmclicker.utility;

import farmclicker.ui.UpgradesPanel;
import farmclicker.upgrades.Item;

//Helper class
public class Player {

    private static final double perClickIncrease = 1;
    private static double currentCoins = 0;
    private static double incomePerSecond = 0;

    public static void setCurrentCoins(double currentCoins) {
        Player.currentCoins = currentCoins;
    }

    public static double getCurrentCoins() {
        return currentCoins;
    }

    public static double getIncomePerSecond() {
        return incomePerSecond;
    }

    public static void autoIncrementCoin() {
        currentCoins += incomePerSecond;
    }

    public static boolean canPurchase(Item item) {
        return item.purchaseCost <= currentCoins;
    }

    /**
     * Deducts item's price from player's coins
     * Then, Increment player's coin rate
     *
     * @param item Item that will be bought by the player.
     */
    public static void purchase(Item item) {
        if (!canPurchase(item)) {
            return;
        }

        currentCoins -= item.purchaseCost;
        item.purchase();
        incomePerSecond += item.getCoinIncreaseRate();
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
