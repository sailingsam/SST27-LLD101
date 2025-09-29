/**
 * Enum representing different types of parking spots
 */
public enum SpotType {
    COMPACT("Compact", 1),
    REGULAR("Regular", 1.2),
    LARGE("Large", 1.5),
    HANDICAPPED("Handicapped", 1.3),
    ELECTRIC("Electric", 1.2);

    private final String displayName;
    private final double priceMultiplier;

    SpotType(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
