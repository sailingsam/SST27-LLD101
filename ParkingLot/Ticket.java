import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Ticket class representing a parking session
 */
public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot assignedSpot;
    private final LocalDateTime entryTime;
    private final String entryGateId;
    private LocalDateTime exitTime;
    private String exitGateId;
    private double totalAmount;
    private boolean isPaid;
    private boolean hasEVCharging;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot assignedSpot, String entryGateId) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.assignedSpot = assignedSpot;
        this.entryTime = LocalDateTime.now();
        this.entryGateId = entryGateId;
        this.exitTime = null;
        this.exitGateId = null;
        this.totalAmount = 0.0;
        this.isPaid = false;
        this.hasEVCharging = assignedSpot.getSpotType() == SpotType.ELECTRIC;
    }

    /**
     * Calculates parking fee based on duration, vehicle type, spot type, and EV
     * charging
     */
    public double calculateParkingFee() {
        if (exitTime == null) {
            exitTime = LocalDateTime.now();
        }

        Duration parkingDuration = Duration.between(entryTime, exitTime);
        long hours = parkingDuration.toHours();
        if (parkingDuration.toMinutesPart() > 0) {
            hours++; // Round up to next hour
        }

        // Minimum 1 hour charge
        if (hours == 0) {
            hours = 1;
        }

        // Base rate per hour
        double baseRate = 5.0; // $5 per hour base rate

        // Apply vehicle type multiplier
        double vehicleMultiplier = vehicle.getType().getSizeMultiplier();

        // Apply spot type multiplier
        double spotMultiplier = assignedSpot.getSpotType().getPriceMultiplier();

        // Calculate base parking fee
        double parkingFee = baseRate * hours * vehicleMultiplier * spotMultiplier;

        // Add EV charging fee if applicable
        double evChargingFee = 0.0;
        if (hasEVCharging) {
            evChargingFee = hours * 2.0; // $2 per hour for EV charging
        }

        totalAmount = parkingFee + evChargingFee;
        return totalAmount;
    }

    /**
     * Marks the ticket as paid
     */
    public void markAsPaid() {
        this.isPaid = true;
    }

    /**
     * Completes the parking session
     */
    public void completeParking(String exitGateId) {
        this.exitTime = LocalDateTime.now();
        this.exitGateId = exitGateId;
        calculateParkingFee();
    }

    /**
     * Gets the parking duration in hours
     */
    public long getParkingDurationHours() {
        LocalDateTime endTime = exitTime != null ? exitTime : LocalDateTime.now();
        Duration duration = Duration.between(entryTime, endTime);
        return duration.toHours();
    }

    /**
     * Gets the parking duration in minutes
     */
    public long getParkingDurationMinutes() {
        LocalDateTime endTime = exitTime != null ? exitTime : LocalDateTime.now();
        Duration duration = Duration.between(entryTime, endTime);
        return duration.toMinutes();
    }

    /**
     * Checks if the parking session is active
     */
    public boolean isActive() {
        return exitTime == null;
    }

    // Getters
    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getAssignedSpot() {
        return assignedSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public String getEntryGateId() {
        return entryGateId;
    }

    public String getExitGateId() {
        return exitGateId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean hasEVCharging() {
        return hasEVCharging;
    }

    @Override
    public String toString() {
        return String.format("Ticket[%s] Vehicle:%s Spot:%s Entry:%s Duration:%d min Amount:$%.2f %s %s",
                ticketId,
                vehicle.getLicensePlate(),
                assignedSpot.getSpotId(),
                entryTime.toString().substring(11, 16), // HH:MM format
                getParkingDurationMinutes(),
                totalAmount,
                isPaid ? "PAID" : "UNPAID",
                hasEVCharging ? "EV-CHARGING" : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Ticket ticket = (Ticket) obj;
        return ticketId.equals(ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return ticketId.hashCode();
    }
}
