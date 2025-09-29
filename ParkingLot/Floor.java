import java.util.*;
import java.util.stream.Collectors;

/**
 * Floor class representing a floor in the parking lot with multiple parking
 * spots
 */
public class Floor {
    private final int floorNumber;
    private final Map<String, ParkingSpot> spots;
    private final Map<SpotType, List<ParkingSpot>> spotsByType;
    private int totalCapacity;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new HashMap<>();
        this.spotsByType = new EnumMap<>(SpotType.class);
        this.totalCapacity = 0;

        // Initialize spot type maps
        for (SpotType type : SpotType.values()) {
            spotsByType.put(type, new ArrayList<>());
        }
    }

    /**
     * Adds a parking spot to this floor
     */
    public void addParkingSpot(ParkingSpot spot) {
        if (spot.getFloor() != this.floorNumber) {
            throw new IllegalArgumentException("Spot floor number doesn't match this floor");
        }

        spots.put(spot.getSpotId(), spot);
        spotsByType.get(spot.getSpotType()).add(spot);
        totalCapacity++;
    }

    /**
     * Removes a parking spot from this floor
     */
    public boolean removeParkingSpot(String spotId) {
        ParkingSpot spot = spots.remove(spotId);
        if (spot != null) {
            spotsByType.get(spot.getSpotType()).remove(spot);
            totalCapacity--;
            return true;
        }
        return false;
    }

    /**
     * Finds an available spot for the given vehicle
     */
    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        // Try to find the most appropriate spot type for the vehicle
        List<SpotType> preferredSpotTypes = getPreferredSpotTypes(vehicle);

        for (SpotType spotType : preferredSpotTypes) {
            List<ParkingSpot> spotsOfType = spotsByType.get(spotType);
            for (ParkingSpot spot : spotsOfType) {
                if (spot.canParkVehicle(vehicle)) {
                    return spot;
                }
            }
        }
        return null;
    }

    /**
     * Gets preferred spot types for a vehicle (in order of preference)
     */
    private List<SpotType> getPreferredSpotTypes(Vehicle vehicle) {
        List<SpotType> preferred = new ArrayList<>();

        switch (vehicle.getType()) {
            case MOTORCYCLE:
                preferred.addAll(Arrays.asList(SpotType.COMPACT, SpotType.REGULAR,
                        SpotType.ELECTRIC, SpotType.HANDICAPPED, SpotType.LARGE));
                break;
            case CAR:
                preferred.addAll(Arrays.asList(SpotType.REGULAR, SpotType.ELECTRIC,
                        SpotType.HANDICAPPED, SpotType.LARGE));
                break;
            case VAN:
                preferred.addAll(Arrays.asList(SpotType.LARGE, SpotType.REGULAR));
                break;
            case TRUCK:
                preferred.add(SpotType.LARGE);
                break;
        }
        return preferred;
    }

    /**
     * Parks a vehicle on this floor
     */
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot availableSpot = findAvailableSpot(vehicle);
        if (availableSpot != null && availableSpot.parkVehicle(vehicle)) {
            return availableSpot;
        }
        return null;
    }

    /**
     * Removes a vehicle from a specific spot
     */
    public Vehicle removeVehicle(String spotId) {
        ParkingSpot spot = spots.get(spotId);
        if (spot != null) {
            return spot.removeVehicle();
        }
        return null;
    }

    /**
     * Gets the spot where a specific vehicle is parked
     */
    public ParkingSpot getSpotByVehicle(Vehicle vehicle) {
        return spots.values().stream()
                .filter(spot -> vehicle.equals(spot.getParkedVehicle()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all available spots on this floor
     */
    public List<ParkingSpot> getAvailableSpots() {
        return spots.values().stream()
                .filter(ParkingSpot::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Gets all occupied spots on this floor
     */
    public List<ParkingSpot> getOccupiedSpots() {
        return spots.values().stream()
                .filter(spot -> spot.getStatus() == SpotStatus.OCCUPIED)
                .collect(Collectors.toList());
    }

    /**
     * Gets count of available spots by type
     */
    public Map<SpotType, Integer> getAvailableSpotsByType() {
        Map<SpotType, Integer> availableCounts = new EnumMap<>(SpotType.class);

        for (SpotType type : SpotType.values()) {
            long count = spotsByType.get(type).stream()
                    .filter(ParkingSpot::isAvailable)
                    .count();
            availableCounts.put(type, (int) count);
        }

        return availableCounts;
    }

    /**
     * Gets the occupancy rate of this floor
     */
    public double getOccupancyRate() {
        if (totalCapacity == 0)
            return 0.0;

        long occupiedCount = spots.values().stream()
                .filter(spot -> spot.getStatus() == SpotStatus.OCCUPIED)
                .count();

        return (double) occupiedCount / totalCapacity;
    }

    // Getters
    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSpot getSpot(String spotId) {
        return spots.get(spotId);
    }

    public Collection<ParkingSpot> getAllSpots() {
        return spots.values();
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getAvailableSpotCount() {
        return (int) spots.values().stream()
                .filter(ParkingSpot::isAvailable)
                .count();
    }

    public int getOccupiedSpotCount() {
        return (int) spots.values().stream()
                .filter(spot -> spot.getStatus() == SpotStatus.OCCUPIED)
                .count();
    }

    @Override
    public String toString() {
        return String.format("Floor %d: %d/%d spots occupied (%.1f%% occupancy)",
                floorNumber, getOccupiedSpotCount(), totalCapacity, getOccupancyRate() * 100);
    }
}
