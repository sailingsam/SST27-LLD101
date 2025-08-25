public class UpiPaymentStrategy implements IPaymentStrategy {
    public String processPayment(double amount) {
        return "Paid via UPI: " + amount;  // Same as original logic
    }
    
    public String getProviderName() {
        return "UPI";
    }
}
