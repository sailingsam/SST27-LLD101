public class ExpressShippingStrategy implements IShippingStrategy {
    public double calculateCost(double weightKg) {
        return 80 + 8 * weightKg;  // Same as original logic
    }
    
    public String getShippingType() {
        return "EXPRESS";
    }
}
