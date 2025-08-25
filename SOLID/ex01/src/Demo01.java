
public class Demo01 {
    public static void main(String[] args) {
        // Dependency injection setup
        EmailService emailService = new EmailClient();
        TaxCalculator taxCalculator = new StandardTaxCalculator(0.18);
        OrderRepository orderRepository = new InMemoryOrderRepository();
        
        // Create OrderService with dependencies
        OrderService orderService = new OrderService(emailService, taxCalculator, orderRepository);
        
        // Process order
        orderService.checkout("a@shop.com", 100.0);
        
        // Demo: Easy to test with different implementations
        System.out.println("\n--- Demo with different tax rate ---");
        TaxCalculator highTaxCalculator = new StandardTaxCalculator(0.25);
        OrderService premiumOrderService = new OrderService(emailService, highTaxCalculator, orderRepository);
        premiumOrderService.checkout("premium@shop.com", 100.0);
    }
}
