package beverages_decorator;

public class Cappuccino extends Beverage{

	@Override
	public int cost() {
		return 10;
	}
	
	@Override
	public String getDescription() {
		return "Cappuccino";
	}

}