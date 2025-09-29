import java.time.LocalDateTime;

/**
 * EntryExit class representing entry and exit gates on the ground floor
 */
public class EntryExit {
    private final String gateId;
    private final EntryExitType type;
    private boolean isOperational;
    private int vehicleCount;
    private LocalDateTime lastUsed;

    public EntryExit(String gateId, EntryExitType type) {
        this.gateId = gateId;
        this.type = type;
        this.isOperational = true;
        this.vehicleCount = 0;
        this.lastUsed = null;
    }

    /**
     * Processes vehicle entry (generates ticket)
     */
    public boolean canProcessEntry() {
        return isOperational && (type == EntryExitType.ENTRY || type == EntryExitType.BOTH);
    }

    /**
     * Processes vehicle exit (validates ticket and calculates payment)
     */
    public boolean canProcessExit() {
        return isOperational && (type == EntryExitType.EXIT || type == EntryExitType.BOTH);
    }

    /**
     * Records vehicle passage through this gate
     */
    public void recordVehiclePassage() {
        if (isOperational) {
            vehicleCount++;
            lastUsed = LocalDateTime.now();
        }
    }

    /**
     * Sets gate operational status
     */
    public void setOperational(boolean operational) {
        this.isOperational = operational;
    }

    /**
     * Resets the vehicle count (typically done daily)
     */
    public void resetVehicleCount() {
        this.vehicleCount = 0;
    }

    // Getters
    public String getGateId() {
        return gateId;
    }

    public EntryExitType getType() {
        return type;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    @Override
    public String toString() {
        return String.format("Gate[%s] Type:%s Status:%s Vehicles:%d",
                gateId, type.getDisplayName(),
                isOperational ? "Operational" : "Out of Order",
                vehicleCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        EntryExit gate = (EntryExit) obj;
        return gateId.equals(gate.gateId);
    }

    @Override
    public int hashCode() {
        return gateId.hashCode();
    }
}
