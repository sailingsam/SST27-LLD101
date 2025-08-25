public class WalletPaymentStrategy implements IPaymentStrategy {
    public String processPayment(double amount) {
        return "Wallet debit: " + amount;  // Same as original logic
    }
    
    public String getProviderName() {
        return "WALLET";
    }
}
