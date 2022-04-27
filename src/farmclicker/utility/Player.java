package farmclicker.utility;

import farmclicker.ui.UpgradesPanel;
import farmclicker.upgrades.Upgrade;

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
        recalculateIncome();
        return incomePerSecond;
    }

    public static void autoIncrementCoin() {
        currentCoins += incomePerSecond;
    }

    public static void setIncomePerSecond(double income) {

        if (income > 0) {
            incomePerSecond = income;
        }

        Player.recalculateIncome();

    }

    public static boolean canPurchase(Upgrade upgrade) {
        return upgrade.purchaseCost <= currentCoins;
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

        currentCoins -= upgrade.purchaseCost;
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
