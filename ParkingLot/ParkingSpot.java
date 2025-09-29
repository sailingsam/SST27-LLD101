import java.time.LocalDateTime;

/**
 * ParkingSpot class representing an individual parking space
 */
public class ParkingSpot {
    private final String spotId;
    private final SpotType spotType;
    private final int floor;
    private SpotStatus status;
    private Vehicle parkedVehicle;
    private LocalDateTime occupiedSince;

    public ParkingSpot(String spotId, SpotType spotType, int floor) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.floor = floor;
        this.status = SpotStatus.AVAILABLE;
        this.parkedVehicle = null;
        this.occupiedSince = null;
    }

    /**
     * Checks if the spot is available for parking
     */
    public boolean isAvailable() {
        return status == SpotStatus.AVAILABLE;
    }

    /**
     * Checks if a vehicle can park in this spot
     */
    public boolean canParkVehicle(Vehicle vehicle) {
        return isAvailable() && vehicle.canFitInSpot(this.spotType);
    }

    /**
     * Parks a vehicle in this spot
     */
    public boolean parkVehicle(Vehicle vehicle) {
        if (!canParkVehicle(vehicle)) {
            return false;
        }

        this.parkedVehicle = vehicle;
        this.status = SpotStatus.OCCUPIED;
        this.occupiedSince = LocalDateTime.now();
        return true;
    }

    /**
     * Removes the vehicle from this spot
     */
    public Vehicle removeVehicle() {
        if (status != SpotStatus.OCCUPIED || parkedVehicle == null) {
            return null;
        }

        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        this.status = SpotStatus.AVAILABLE;
        this.occupiedSince = null;
        return vehicle;
    }

    /**
     * Reserves the spot
     */
    public void reserve() {
        if (status == SpotStatus.AVAILABLE) {
            status = SpotStatus.RESERVED;
        }
    }

    /**
     * Makes the spot unavailable (out of order)
     */
    public void markOutOfOrder() {
        if (status == SpotStatus.AVAILABLE) {
            status = SpotStatus.OUT_OF_ORDER;
        }
    }

    /**
     * Makes the spot available again
     */
    public void markAvailable() {
        if (status == SpotStatus.OUT_OF_ORDER || status == SpotStatus.RESERVED) {
            status = SpotStatus.AVAILABLE;
        }
    }

    // Getters
    public String getSpotId() {
        return spotId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public int getFloor() {
        return floor;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public LocalDateTime getOccupiedSince() {
        return occupiedSince;
    }

    @Override
    public String toString() {
        return String.format("Spot[%s] Floor:%d Type:%s Status:%s %s",
                spotId, floor, spotType.getDisplayName(), status.getDisplayName(),
                parkedVehicle != null ? "Vehicle:" + parkedVehicle.getLicensePlate() : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ParkingSpot spot = (ParkingSpot) obj;
        return spotId.equals(spot.spotId);
    }

    @Override
    public int hashCode() {
        return spotId.hashCode();
    }
}
