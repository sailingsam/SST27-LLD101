public class StandardShippingStrategy implements IShippingStrategy {
    public double calculateCost(double weightKg) {
        return 50 + 5 * weightKg;  // Same as original logic
    }
    
    public String getShippingType() {
        return "STANDARD";
    }
}
