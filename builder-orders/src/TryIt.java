import com.example.orders.*;
import java.util.List;

public class TryIt {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        
        // 1. Create simple order using Builder pattern
        System.out.println("1. Creating simple order with Builder:");
        Order simpleOrder = service.createSimpleOrder("ORD-001", "alice@example.com", "LAPTOP", 1, 99900);
        System.out.println("   Order ID: " + simpleOrder.getId());
        System.out.println("   Customer: " + simpleOrder.getCustomerEmail());
        System.out.println("   Total: " + simpleOrder.totalAfterDiscount() + " cents");
        System.out.println("   Lines count: " + simpleOrder.getLines().size());
        
        // 2. Create complex order with multiple lines and discount
        System.out.println("\n2. Creating complex order with multiple lines:");
        Order complexOrder = Order.builder()
                .id("ORD-002")
                .customerEmail("bob@company.com")
                .addLine("MOUSE", 2, 2500)
                .addLine("KEYBOARD", 1, 7500)
                .addLine("MONITOR", 1, 25000)
                .discountPercent(15)
                .expedited(true)
                .notes("Rush order for new employee setup")
                .build();
                
        System.out.println("   Order ID: " + complexOrder.getId());
        System.out.println("   Lines: " + complexOrder.getLines().size());
        System.out.println("   Before discount: " + complexOrder.totalBeforeDiscount() + " cents");
        System.out.println("   After 15% discount: " + complexOrder.totalAfterDiscount() + " cents");
        System.out.println("   Expedited: " + complexOrder.isExpedited());
        System.out.println("   Notes: " + complexOrder.getNotes());
        
        // 3. Demonstrate immutability - no mutation possible!
        System.out.println("\n3. Demonstrating immutability:");
        List<OrderLine> lines = complexOrder.getLines();
        System.out.println("   Original line count: " + lines.size());
        
        try {
            // This will throw UnsupportedOperationException!
            lines.add(new OrderLine("HACKER", 999, 1));
        } catch (UnsupportedOperationException e) {
            System.out.println("   ✓ Cannot add to order lines - list is unmodifiable!");
        }
        
        // OrderLine is also immutable - no setQuantity method exists!
        // OrderLine firstLine = lines.get(0);
        // firstLine.setQuantity(999); // This would cause compile error!
        System.out.println("   ✓ OrderLine has no setters - immutable!");
        
        // 4. Show "updating" by creating new order
        System.out.println("\n4. 'Updating' discount (creates new order):");
        Order originalOrder = complexOrder;
        Order updatedOrder = service.withUpdatedDiscount(originalOrder, 25); // 25% discount
        
        System.out.println("   Original discount: " + originalOrder.getDiscountPercent() + "%");
        System.out.println("   Original total: " + originalOrder.totalAfterDiscount() + " cents");
        System.out.println("   Updated discount: " + updatedOrder.getDiscountPercent() + "%");
        System.out.println("   Updated total: " + updatedOrder.totalAfterDiscount() + " cents");
        System.out.println("   ✓ Original order unchanged - true immutability!");
        
        // 5. Demonstrate validation
        System.out.println("\n5. Demonstrating validation:");
        try {
            Order invalidOrder = Order.builder()
                    .id("") // Empty ID
                    .customerEmail("invalid-email") // Bad email
                    .discountPercent(150) // Invalid discount > 100
                    .build(); // Will fail - no lines added
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Validation caught: " + e.getMessage());
        }
        
        try {
            Order noLinesOrder = Order.builder()
                    .id("ORD-003")
                    .customerEmail("test@example.com")
                    .build(); // No lines added - should fail
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ Validation caught: " + e.getMessage());
        }
        
        try {
            OrderLine invalidLine = new OrderLine("", -1, -100); // Invalid values
        } catch (IllegalArgumentException e) {
            System.out.println("   ✓ OrderLine validation caught: " + e.getMessage());
        }
        
        System.out.println("\n=== Orders are now bulletproof! ===");
        System.out.println("=== No mutation possible, all validation centralized ===");
    }
}
