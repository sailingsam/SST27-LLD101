
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
        
    }
}
