import java.util.Map;
import java.util.HashMap;

public class ShippingCostCalculator implements ICostCalculator {
    private Map<String, IShippingStrategy> strategies;
    
    public ShippingCostCalculator() {
        strategies = new HashMap<>();
        strategies.put("STANDARD", new StandardShippingStrategy());
        strategies.put("EXPRESS", new ExpressShippingStrategy());
        strategies.put("OVERNIGHT", new OvernightShippingStrategy());
    }
    
    public double cost(Shipment shipment) {
        IShippingStrategy strategy = strategies.get(shipment.getType());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown type: " + shipment.getType());
        }
        return strategy.calculateCost(shipment.getWeightKg());
    }
}
