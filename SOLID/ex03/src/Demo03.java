public class Demo03 {
    public static void main(String[] args) {
        // Create calculator with dependency injection
        ICostCalculator calculator = new ShippingCostCalculator();
        
        // Original test
        System.out.println(calculator.cost(new Shipment("EXPRESS", 2.0)));
        
        // demo
        System.out.println();
        System.out.println("--- Demo: Different shipping types ---");
        double standardCost = calculator.cost(new Shipment("STANDARD", 1.0));
        System.out.println("STANDARD 1kg: " + standardCost);
        double overnightCost = calculator.cost(new Shipment("OVERNIGHT", 1.5));
        System.out.println("OVERNIGHT 1.5kg: " + overnightCost);
    }
}
