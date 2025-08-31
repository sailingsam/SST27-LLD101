package com.example.orders;

/**
 * Immutable OrderLine - no setters, all fields final.
 * Prevents corruption of Order totals.
 */
public final class OrderLine {
    private final String sku;
    private final int quantity;
    private final int unitPriceCents;

    public OrderLine(String sku, int quantity, int unitPriceCents) {
        // Basic validation
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPriceCents < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        
        this.sku = sku.trim();
        this.quantity = quantity;
        this.unitPriceCents = unitPriceCents;
    }

    public String getSku() { return sku; }
    public int getQuantity() { return quantity; }
    public int getUnitPriceCents() { return unitPriceCents; }

    // No setters - immutable!
    
    public int getLineTotalCents() {
        return quantity * unitPriceCents;
    }

    @Override
    public String toString() {
        return String.format("OrderLine{sku='%s', qty=%d, price=%d cents}", 
                           sku, quantity, unitPriceCents);
    }
}
