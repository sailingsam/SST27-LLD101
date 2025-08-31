package com.example.orders;

import java.util.List;

/**
 * Service for creating immutable Orders using the Builder pattern.
 * All validation centralized in Order.Builder.build().
 */
public class OrderService {

    // Default constructor
    public OrderService() {
    }

    /**
     * Creates an Order with all specified parameters.
     * All validation happens in the Builder.
     */
    public Order createOrder(String id, String email, List<OrderLine> lines, 
                           Integer discount, boolean expedited, String notes) {
        return Order.builder()
                .id(id)
                .customerEmail(email)
                .lines(lines) // Defensive copy made in builder
                .discountPercent(discount)
                .expedited(expedited)
                .notes(notes)
                .build(); // All validation happens here
    }

    /**
     * Creates a simple Order with just basic info and one line.
     */
    public Order createSimpleOrder(String id, String email, String sku, 
                                 int quantity, int unitPriceCents) {
        return Order.builder()
                .id(id)
                .customerEmail(email)
                .addLine(sku, quantity, unitPriceCents)
                .build();
    }

    /**
     * Creates an expedited Order with discount.
     */
    public Order createExpeditedOrder(String id, String email, List<OrderLine> lines, 
                                    Integer discountPercent, String notes) {
        return Order.builder()
                .id(id)
                .customerEmail(email)
                .lines(lines)
                .discountPercent(discountPercent)
                .expedited(true) // Expedited
                .notes(notes)
                .build();
    }

    /**
     * Since Orders are immutable, we create a new Order with updated discount
     * instead of modifying the existing one.
     */
    public Order withUpdatedDiscount(Order existing, Integer newDiscountPercent) {
        return Order.builder()
                .id(existing.getId())
                .customerEmail(existing.getCustomerEmail())
                .lines(existing.getLines()) // Safe - already immutable
                .discountPercent(newDiscountPercent) // New discount
                .expedited(existing.isExpedited())
                .notes(existing.getNotes())
                .build(); // Validation ensures discount is valid
    }
}
