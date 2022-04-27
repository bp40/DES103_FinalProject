package farmclicker.upgrades;

/**
 * Abstract class that act as base of purchasable upgrade/item
 *
 * @see Item
 * @see PowerUp
 */
public abstract class Upgrade {

    /**
     * name of the Upgrade
     */
    public String name;
    /**
     * current cost of Upgrade
     */
    public int purchaseCost;

    /**
     * @param name     Name of Upgrade
     * @param baseCost starting cost for the upgrade
     */
    public Upgrade(String name, int baseCost) {
        this.name = name;
        this.purchaseCost = baseCost;

    }

    /**
     * @see Item#purchase()
     * @see PowerUp#purchase()
     */
    public abstract void purchase();

}
