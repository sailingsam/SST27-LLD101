public interface IShippingStrategy {
    double calculateCost(double weightKg);
    String getShippingType();
}
