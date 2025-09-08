package beverages_decorator;

public class WhippedCreamDecorator extends BeverageDecorator {
    
    public WhippedCreamDecorator(Beverage beverage) {
        super(beverage);
    }
    
    @Override
    public int cost() {
        return beverage.cost() + 7;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }
}
