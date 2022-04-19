package farmclicker.upgrades;

public class Item extends Upgrade {
    private final double coinIncreaseRate;
    private final int baseCost;
    private int currentAmount;

    public Item(String name, int baseCost, double coinIncreaseRate) {
        super(name, baseCost);
        this.baseCost = baseCost;
        this.coinIncreaseRate = coinIncreaseRate;
        this.currentAmount = 0;
    }

    public double getCoinIncreaseRate() {
        return coinIncreaseRate;
    }

    public int getCurrentAmount(){
        return currentAmount;
    }

    public void setCurrentAmount(int amount){
        currentAmount = amount;
    }

    public void purchase() {

        currentAmount += 1;
        super.purchaseCost *= 1.15;

    }

}
