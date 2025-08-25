public class CardPaymentStrategy implements IPaymentStrategy {
    public String processPayment(double amount) {
        return "Charged card: " + amount;  // Same as original logic
    }
    
    public String getProviderName() {
        return "CARD";
    }
}
