/**
 * Enum representing different types of vehicles that can park in the parking
 * lot
 */
public enum VehicleType {
    CAR(1),
    MOTORCYCLE(0.5),
    TRUCK(2),
    VAN(1.5);

    private final double sizeMultiplier;

    VehicleType(double sizeMultiplier) {
        this.sizeMultiplier = sizeMultiplier;
    }

    public double getSizeMultiplier() {
        return sizeMultiplier;
    }
}
