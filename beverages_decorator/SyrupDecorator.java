package beverages_decorator;

public class SyrupDecorator extends BeverageDecorator {
    private String syrupType;
    
    public SyrupDecorator(Beverage beverage, String syrupType) {
        super(beverage);
        this.syrupType = syrupType;
    }
    
    @Override
    public int cost() {
        return beverage.cost() + 6;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", " + syrupType + " Syrup";
    }
}
