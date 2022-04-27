package farmclicker.upgrades;

public abstract class Upgrade {

    public String name;
    public int purchaseCost;

    public Upgrade(String name, int currentCost) {
        this.name = name;
        this.purchaseCost = currentCost;

    }

    public abstract void purchase();

}
