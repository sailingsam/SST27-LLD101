public class Shipment {
    private String type; 
    private double weightKg;
    
    public Shipment(String type, double weightKg) { 
        this.type = type; 
        this.weightKg = weightKg; 
    }
    
    public String getType() {
        return type;
    }
    
    public double getWeightKg() {
        return weightKg;
    }
}