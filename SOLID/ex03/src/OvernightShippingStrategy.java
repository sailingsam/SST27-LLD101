public class OvernightShippingStrategy implements IShippingStrategy {
    public double calculateCost(double weightKg) {
        return 120 + 10 * weightKg;  // Same as original logic
    }
    
    public String getShippingType() {
        return "OVERNIGHT";
    }
}
