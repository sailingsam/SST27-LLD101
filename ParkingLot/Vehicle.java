import java.util.*;

/**
 * Vehicle class that handles all vehicle types through configuration
 */
public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;
    private final String color;
    private final String ownerName;
    private final VehicleConfiguration config;

    public Vehicle(String licensePlate, VehicleType type, String color, String ownerName) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.color = color;
        this.ownerName = ownerName;
        this.config = VehicleConfigurationFactory.getConfiguration(type);
    }

    /**
     * Determines if vehicle can fit in the specified spot type
     */
    public boolean canFitInSpot(SpotType spotType) {
        return config.getCompatibleSpotTypes().contains(spotType);
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s (%s)",
                type.name(), licensePlate, color, ownerName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vehicle vehicle = (Vehicle) obj;
        return licensePlate.equals(vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
}

/**
 * Configuration class that defines vehicle behavior
 */
class VehicleConfiguration {
    private final Set<SpotType> compatibleSpotTypes;
    private final String displayName;

    public VehicleConfiguration(Set<SpotType> compatibleSpotTypes, String displayName) {
        this.compatibleSpotTypes = new HashSet<>(compatibleSpotTypes);
        this.displayName = displayName;
    }

    public Set<SpotType> getCompatibleSpotTypes() {
        return new HashSet<>(compatibleSpotTypes);
    }

    public String getDisplayName() {
        return displayName;
    }
}

/**
 * Factory that creates configurations for different vehicle types
 */
class VehicleConfigurationFactory {
    private static final Map<VehicleType, VehicleConfiguration> configurations = new HashMap<>();

    static {
        initializeConfigurations();
    }

    private static void initializeConfigurations() {
        // Motorcycle: can fit in any spot
        configurations.put(VehicleType.MOTORCYCLE,
                new VehicleConfiguration(
                        EnumSet.allOf(SpotType.class),
                        "Motorcycle"));

        // Car: regular, large, handicapped, electric - NOT compact
        configurations.put(VehicleType.CAR,
                new VehicleConfiguration(
                        EnumSet.of(SpotType.REGULAR, SpotType.LARGE, SpotType.HANDICAPPED, SpotType.ELECTRIC),
                        "Car"));

        // Van: large and regular spots
        configurations.put(VehicleType.VAN,
                new VehicleConfiguration(
                        EnumSet.of(SpotType.LARGE, SpotType.REGULAR),
                        "Van"));

        // Truck: only large spots
        configurations.put(VehicleType.TRUCK,
                new VehicleConfiguration(
                        EnumSet.of(SpotType.LARGE),
                        "Truck"));
    }

    public static VehicleConfiguration getConfiguration(VehicleType type) {
        return configurations.get(type);
    }

    /**
     * Add new vehicle types by adding configuration
     */
    public static void addVehicleType(VehicleType type, Set<SpotType> compatibleSpots, String displayName) {
        configurations.put(type, new VehicleConfiguration(compatibleSpots, displayName));
    }
}
