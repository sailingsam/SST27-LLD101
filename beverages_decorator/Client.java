package beverages_decorator;

public class Client {

	public static void main(String[] args) {
		System.out.println("=== Beverage Decorator Pattern Demo ===\n");
		
		// Basic beverages
		Beverage cappuccino = new Cappuccino();
		System.out.println(cappuccino.getDescription() + " - Cost: $" + cappuccino.cost());
		
		Beverage latte = new Latte();
		System.out.println(latte.getDescription() + " - Cost: $" + latte.cost());
		
		System.out.println("\n=== Decorated Beverages ===\n");
		
		// Cappuccino with milk and sugar
		Beverage decoratedCappuccino = new SugarDecorator(new MilkDecorator(new Cappuccino()));
		System.out.println(decoratedCappuccino.getDescription() + " - Cost: $" + decoratedCappuccino.cost());
		
		// Latte with whipped cream
		Beverage decoratedLatte = new WhippedCreamDecorator(new Latte());
		System.out.println(decoratedLatte.getDescription() + " - Cost: $" + decoratedLatte.cost());
		
		// Cappuccino with multiple decorators
		Beverage fancyCappuccino = new WhippedCreamDecorator(
			new SyrupDecorator(
				new MilkDecorator(
					new SugarDecorator(new Cappuccino())
				), "Vanilla"
			)
		);
		System.out.println(fancyCappuccino.getDescription() + " - Cost: $" + fancyCappuccino.cost());
		
		// Latte with syrup
		Beverage latteWithSyrup = new SyrupDecorator(new Latte(), "Caramel");
		System.out.println(latteWithSyrup.getDescription() + " - Cost: $" + latteWithSyrup.cost());
		
		System.out.println("\n=== Multiple Same Decorations ===\n");
		
		// Double milk, double sugar on Latte
		Beverage extraSweet = new SugarDecorator(
			new SugarDecorator(
				new MilkDecorator(
					new MilkDecorator(new Latte())
				)
			)
		);
		System.out.println(extraSweet.getDescription() + " - Cost: $" + extraSweet.cost());
	}

}