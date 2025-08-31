package com.example.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable Order with Builder pattern.
 * All fields are final, defensive copies protect internal state.
 */
public final class Order {
    // All fields are private final - immutable
    private final String id;
    private final String customerEmail;
    private final List<OrderLine> lines; // Defensive copy stored
    private final Integer discountPercent; // 0..100, validated
    private final boolean expedited;
    private final String notes;

    // Private constructor - only accessible through Builder
    private Order(Builder builder) {
        this.id = builder.id;
        this.customerEmail = builder.customerEmail;
        // Defensive copy - caller cannot modify our internal list
        this.lines = Collections.unmodifiableList(new ArrayList<>(builder.lines));
        this.discountPercent = builder.discountPercent;
        this.expedited = builder.expedited;
        this.notes = builder.notes;
    }

    // Static factory method to create builder
    public static Builder builder() {
        return new Builder();
    }

    // Only getters - no setters for immutability
    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    
    // Return unmodifiable view - no mutation possible
    public List<OrderLine> getLines() { 
        return lines; // Already unmodifiable from constructor
    }
    
    public Integer getDiscountPercent() { return discountPercent; }
    public boolean isExpedited() { return expedited; }
    public String getNotes() { return notes; }

    public int totalBeforeDiscount() {
        int sum = 0;
        for (OrderLine line : lines) {
            sum += line.getLineTotalCents();
        }
        return sum;
    }

    public int totalAfterDiscount() {
        int base = totalBeforeDiscount();
        if (discountPercent == null || discountPercent == 0) {
            return base;
        }
        return base - (base * discountPercent / 100);
    }

    /**
     * Builder class for Order construction with validation.
     * Required fields: id, customerEmail, at least one OrderLine
     * Optional fields: discountPercent, expedited, notes
     */
    public static class Builder {
        // Required fields
        private String id;
        private String customerEmail;
        private final List<OrderLine> lines = new ArrayList<>();
        
        // Optional fields with defaults
        private Integer discountPercent; // null = no discount
        private boolean expedited = false;
        private String notes;

        private Builder() {}

        // Required field setters
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        // Line management
        public Builder addLine(OrderLine line) {
            if (line != null) {
                this.lines.add(line);
            }
            return this;
        }

        public Builder addLine(String sku, int quantity, int unitPriceCents) {
            return addLine(new OrderLine(sku, quantity, unitPriceCents));
        }

        public Builder lines(List<OrderLine> lines) {
            if (lines != null) {
                // Clear existing and add all - defensive copy
                this.lines.clear();
                this.lines.addAll(lines);
            }
            return this;
        }

        // Optional field setters
        public Builder discountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }

        public Builder expedited(boolean expedited) {
            this.expedited = expedited;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        /**
         * Builds the Order with centralized validation.
         * All validation happens here in one place.
         */
        public Order build() {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Order ID cannot be null or empty");
            }

            if (!PricingRules.isValidEmail(customerEmail)) {
                throw new IllegalArgumentException("Invalid customer email format");
            }

            // Must have at least one line
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Order must have at least one line item");
            }

            // Validate discount range
            if (!PricingRules.isValidDiscount(discountPercent)) {
                throw new IllegalArgumentException("Discount percent must be between 0 and 100");
            }

            // Additional business validation
            if (notes != null && notes.length() > 500) {
                throw new IllegalArgumentException("Notes cannot exceed 500 characters");
            }

            // Trim whitespace
            this.id = id.trim();

            return new Order(this);
        }
    }
}
