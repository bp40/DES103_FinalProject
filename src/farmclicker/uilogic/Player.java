package farmclicker.uilogic;

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

    public static void purchase(Item item) {
        if (!canPurchase(item)) {
            return;
        }

        currentCoins -= item.purchaseCost;
        item.purchase();
        incomePerSecond += item.getCoinIncreaseRate();
    }

    public static void recalculateIncome(){

        incomePerSecond = 0;

        UpgradesPanel.upgradeList.forEach(item -> {
            incomePerSecond += (item.getCoinIncreaseRate() * item.getCurrentAmount());
        });
    }

    public static void click() {
        currentCoins += perClickIncrease;
    }

    public static void superClick() {
        currentCoins += (perClickIncrease * 2);
    }

}
