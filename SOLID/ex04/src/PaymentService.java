
import java.util.Map;
import java.util.HashMap;

public class PaymentService implements IPaymentProcessor {
    private Map<String, IPaymentStrategy> strategies;
    
    public PaymentService() {
        strategies = new HashMap<>();
        strategies.put("CARD", new CardPaymentStrategy());
        strategies.put("UPI", new UpiPaymentStrategy());
        strategies.put("WALLET", new WalletPaymentStrategy());
    }
    
    public String pay(Payment payment) {
        IPaymentStrategy strategy = strategies.get(payment.getProvider());
        if (strategy == null) {
            throw new RuntimeException("No provider");
        }
        return strategy.processPayment(payment.getAmount());
    }
}