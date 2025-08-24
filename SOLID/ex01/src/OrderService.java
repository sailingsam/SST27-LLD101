public class OrderService {
    private final EmailService emailService;
    private final TaxCalculator taxCalculator;
    private final OrderRepository orderRepository;

    public OrderService(EmailService emailService, TaxCalculator taxCalculator, OrderRepository orderRepository) {
        this.emailService = emailService;
        this.taxCalculator = taxCalculator;
        this.orderRepository = orderRepository;
    }

    public void checkout(String customerEmail, double subtotal) {
        double total = taxCalculator.calculateTotalWithTax(subtotal);
        emailService.send(customerEmail, "Thanks! Your total is " + total);
        orderRepository.saveOrder(customerEmail, total);
    }
}