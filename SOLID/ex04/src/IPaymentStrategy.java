public interface IPaymentStrategy {
    String processPayment(double amount);
    String getProviderName();
}
