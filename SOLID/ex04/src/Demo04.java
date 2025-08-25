
public class Demo04 {
    public static void main(String[] args) {
        // Create payment processor with dependency injection
        IPaymentProcessor processor = new PaymentService();
        
        // Original test - same output expected
        System.out.println(processor.pay(new Payment("UPI", 499)));
        
        // Demo
        System.out.println();
        System.out.println("--- Demo: Different payment methods ---");
        System.out.println(processor.pay(new Payment("CARD", 299)));
        System.out.println(processor.pay(new Payment("WALLET", 150)));
    }
}
