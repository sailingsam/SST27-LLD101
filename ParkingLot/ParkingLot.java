import java.util.*;
import java.util.stream.Collectors;

/**
 * Main ParkingLot class that orchestrates the entire parking system
 * Supports multiple floors with different vehicle types and pricing
 */
public class ParkingLot {
    private final String parkingLotId;
    private final String name;
    private final String address;
    private final Map<Integer, Floor> floors;
    private final List<EntryExit> entryExitGates;
    private final TicketManager ticketManager;
    private final int totalFloors;

    public ParkingLot(String parkingLotId, String name, String address, int totalFloors) {
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.address = address;
        this.totalFloors = totalFloors;
        this.floors = new HashMap<>();
        this.entryExitGates = new ArrayList<>();
        this.ticketManager = new TicketManager();

        // Initialize floors
        for (int i = 0; i < totalFloors; i++) {
            floors.put(i, new Floor(i));
        }
    }

    /**
     * Adds entry/exit gates (typically on ground floor)
     */
    public void addEntryExitGate(EntryExit gate) {
        entryExitGates.add(gate);
    }

    /**
     * Adds a parking spot to a specific floor
     */
    public void addParkingSpot(int floorNumber, ParkingSpot spot) {
        Floor floor = floors.get(floorNumber);
        if (floor != null) {
            floor.addParkingSpot(spot);
        } else {
            throw new IllegalArgumentException("Floor " + floorNumber + " does not exist");
        }
    }

    /**
     * Parks a vehicle in the parking lot
     */
    public Ticket parkVehicle(Vehicle vehicle, String entryGateId) {
        // Validate entry gate
        EntryExit entryGate = findGate(entryGateId);
        if (entryGate == null || !entryGate.canProcessEntry()) {
            throw new IllegalArgumentException("Invalid or non-operational entry gate: " + entryGateId);
        }

        // Check if vehicle is already parked
        Ticket existingTicket = ticketManager.findTicketByVehicle(vehicle.getLicensePlate());
        if (existingTicket != null) {
            throw new IllegalStateException("Vehicle " + vehicle.getLicensePlate() + " is already parked");
        }

        // Find available parking spot
        ParkingSpot availableSpot = findAvailableSpot(vehicle);
        if (availableSpot == null) {
            throw new IllegalStateException("No available parking spot for " + vehicle.getType());
        }

        // Park the vehicle
        if (availableSpot.parkVehicle(vehicle)) {
            entryGate.recordVehiclePassage();
            return ticketManager.generateTicket(vehicle, availableSpot, entryGateId);
        }

        throw new IllegalStateException("Failed to park vehicle");
    }

    /**
     * Removes a vehicle from the parking lot
     */
    public double removeVehicle(String licensePlate, String exitGateId) {
        // Validate exit gate
        EntryExit exitGate = findGate(exitGateId);
        if (exitGate == null || !exitGate.canProcessExit()) {
            throw new IllegalArgumentException("Invalid or non-operational exit gate: " + exitGateId);
        }

        // Find the ticket
        Ticket ticket = ticketManager.findTicketByVehicle(licensePlate);
        if (ticket == null) {
            throw new IllegalArgumentException("No active parking session found for vehicle: " + licensePlate);
        }

        // Remove vehicle from spot
        ParkingSpot spot = ticket.getAssignedSpot();
        Vehicle vehicle = spot.removeVehicle();
        if (vehicle == null) {
            throw new IllegalStateException("Vehicle not found in assigned spot");
        }

        // Complete parking session
        ticketManager.completeParking(ticket.getTicketId(), exitGateId);
        exitGate.recordVehiclePassage();

        return ticket.getTotalAmount();
    }

    /**
     * Processes payment for a parking ticket
     */
    public boolean processPayment(String ticketId, double amountPaid) {
        return ticketManager.processPayment(ticketId, amountPaid);
    }

    /**
     * Finds an available parking spot for a vehicle
     */
    private ParkingSpot findAvailableSpot(Vehicle vehicle) {
        // Try each floor starting from ground floor
        for (int floorNum = 0; floorNum < totalFloors; floorNum++) {
            Floor floor = floors.get(floorNum);
            ParkingSpot spot = floor.findAvailableSpot(vehicle);
            if (spot != null) {
                return spot;
            }
        }
        return null;
    }

    /**
     * Finds a gate by ID
     */
    private EntryExit findGate(String gateId) {
        return entryExitGates.stream()
                .filter(gate -> gate.getGateId().equals(gateId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets parking lot status summary
     */
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();

        // Basic info
        status.put("parkingLotId", parkingLotId);
        status.put("name", name);
        status.put("totalFloors", totalFloors);

        // Capacity info
        int totalCapacity = floors.values().stream()
                .mapToInt(Floor::getTotalCapacity)
                .sum();
        int occupiedSpots = floors.values().stream()
                .mapToInt(Floor::getOccupiedSpotCount)
                .sum();
        int availableSpots = totalCapacity - occupiedSpots;

        status.put("totalCapacity", totalCapacity);
        status.put("occupiedSpots", occupiedSpots);
        status.put("availableSpots", availableSpots);
        status.put("occupancyRate", totalCapacity > 0 ? (double) occupiedSpots / totalCapacity : 0.0);

        // Floor-wise status
        Map<Integer, String> floorStatus = new HashMap<>();
        floors.forEach((floorNum, floor) -> floorStatus.put(floorNum, floor.toString()));
        status.put("floorStatus", floorStatus);

        // Gate status
        List<String> gateStatus = entryExitGates.stream()
                .map(EntryExit::toString)
                .collect(Collectors.toList());
        status.put("gateStatus", gateStatus);

        // Ticket statistics
        status.put("activeTickets", ticketManager.getActiveTicketCount());
        status.put("completedTickets", ticketManager.getCompletedTicketCount());

        // Vehicle type distribution
        status.put("vehicleTypeStats", ticketManager.getVehicleTypeStats());

        // Revenue statistics
        status.put("revenueStats", ticketManager.getRevenueStats());

        return status;
    }

    /**
     * Gets available spots by type across all floors
     */
    public Map<SpotType, Integer> getAvailableSpotsByType() {
        Map<SpotType, Integer> totalAvailable = new EnumMap<>(SpotType.class);

        // Initialize counts
        for (SpotType type : SpotType.values()) {
            totalAvailable.put(type, 0);
        }

        // Sum up from all floors
        floors.values().forEach(floor -> {
            Map<SpotType, Integer> floorAvailable = floor.getAvailableSpotsByType();
            floorAvailable.forEach((type, count) -> totalAvailable.put(type, totalAvailable.get(type) + count));
        });

        return totalAvailable;
    }

    /**
     * Gets all vehicles currently parked (for security/management)
     */
    public List<Vehicle> getCurrentlyParkedVehicles() {
        return floors.values().stream()
                .flatMap(floor -> floor.getOccupiedSpots().stream())
                .map(ParkingSpot::getParkedVehicle)
                .collect(Collectors.toList());
    }

    /**
     * Finds vehicle location by license plate
     */
    public ParkingSpot findVehicleLocation(String licensePlate) {
        return floors.values().stream()
                .flatMap(floor -> floor.getAllSpots().stream())
                .filter(spot -> spot.getParkedVehicle() != null &&
                        spot.getParkedVehicle().getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);
    }

    // Getters
    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    public List<EntryExit> getEntryExitGates() {
        return new ArrayList<>(entryExitGates);
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    @Override
    public String toString() {
        Map<String, Object> status = getStatus();
        return String.format("%s - %d floors, %d/%d spots occupied (%.1f%% occupancy)",
                name, totalFloors,
                (Integer) status.get("occupiedSpots"),
                (Integer) status.get("totalCapacity"),
                (Double) status.get("occupancyRate") * 100);
    }
}
