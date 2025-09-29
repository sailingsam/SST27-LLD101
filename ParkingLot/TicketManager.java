import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TicketManager class to manage parking tickets and pricing
 */
public class TicketManager {
    private final Map<String, Ticket> activeTickets;
    private final Map<String, Ticket> completedTickets;
    private int ticketCounter;
    private final DateTimeFormatter formatter;

    public TicketManager() {
        this.activeTickets = new HashMap<>();
        this.completedTickets = new HashMap<>();
        this.ticketCounter = 1;
        this.formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    /**
     * Generates a new ticket for a vehicle
     */
    public Ticket generateTicket(Vehicle vehicle, ParkingSpot spot, String entryGateId) {
        String ticketId = generateTicketId();
        Ticket ticket = new Ticket(ticketId, vehicle, spot, entryGateId);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    /**
     * Processes payment for a ticket
     */
    public boolean processPayment(String ticketId, double amountPaid) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            return false;
        }

        double totalAmount = ticket.calculateParkingFee();
        if (amountPaid >= totalAmount) {
            ticket.markAsPaid();
            return true;
        }
        return false;
    }

    /**
     * Completes parking session and moves ticket to completed tickets
     */
    public Ticket completeParking(String ticketId, String exitGateId) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket != null) {
            ticket.completeParking(exitGateId);
            completedTickets.put(ticketId, ticket);
        }
        return ticket;
    }

    /**
     * Finds ticket by vehicle license plate
     */
    public Ticket findTicketByVehicle(String licensePlate) {
        return activeTickets.values().stream()
                .filter(ticket -> ticket.getVehicle().getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all active tickets
     */
    public Collection<Ticket> getActiveTickets() {
        return new ArrayList<>(activeTickets.values());
    }

    /**
     * Gets all completed tickets
     */
    public Collection<Ticket> getCompletedTickets() {
        return new ArrayList<>(completedTickets.values());
    }

    /**
     * Gets tickets by vehicle type
     */
    public List<Ticket> getTicketsByVehicleType(VehicleType vehicleType) {
        List<Ticket> result = new ArrayList<>();

        // Add from active tickets
        activeTickets.values().stream()
                .filter(ticket -> ticket.getVehicle().getType() == vehicleType)
                .forEach(result::add);

        // Add from completed tickets
        completedTickets.values().stream()
                .filter(ticket -> ticket.getVehicle().getType() == vehicleType)
                .forEach(result::add);

        return result;
    }

    /**
     * Gets revenue statistics
     */
    public Map<String, Double> getRevenueStats() {
        Map<String, Double> stats = new HashMap<>();

        double totalRevenue = completedTickets.values().stream()
                .filter(Ticket::isPaid)
                .mapToDouble(Ticket::getTotalAmount)
                .sum();

        double evChargingRevenue = completedTickets.values().stream()
                .filter(ticket -> ticket.isPaid() && ticket.hasEVCharging())
                .mapToDouble(ticket -> ticket.getParkingDurationHours() * 2.0)
                .sum();

        stats.put("totalRevenue", totalRevenue);
        stats.put("evChargingRevenue", evChargingRevenue);
        stats.put("parkingRevenue", totalRevenue - evChargingRevenue);

        return stats;
    }

    /**
     * Gets occupancy statistics by vehicle type
     */
    public Map<VehicleType, Integer> getVehicleTypeStats() {
        Map<VehicleType, Integer> stats = new EnumMap<>(VehicleType.class);

        for (VehicleType type : VehicleType.values()) {
            stats.put(type, 0);
        }

        activeTickets.values().forEach(ticket -> {
            VehicleType type = ticket.getVehicle().getType();
            stats.put(type, stats.get(type) + 1);
        });

        return stats;
    }

    /**
     * Gets unpaid tickets (for enforcement)
     */
    public List<Ticket> getUnpaidTickets() {
        return activeTickets.values().stream()
                .filter(ticket -> !ticket.isPaid() && ticket.getParkingDurationHours() > 0)
                .collect(ArrayList::new, (list, ticket) -> list.add(ticket), ArrayList::addAll);
    }

    /**
     * Gets overdue tickets (parked for more than 24 hours)
     */
    public List<Ticket> getOverdueTickets() {
        return activeTickets.values().stream()
                .filter(ticket -> ticket.getParkingDurationHours() > 24)
                .collect(ArrayList::new, (list, ticket) -> list.add(ticket), ArrayList::addAll);
    }

    /**
     * Generates a unique ticket ID
     */
    private String generateTicketId() {
        String datePrefix = LocalDateTime.now().format(formatter);
        return String.format("TKT-%s-%04d", datePrefix, ticketCounter++);
    }

    // Getters
    public Ticket getTicket(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            ticket = completedTickets.get(ticketId);
        }
        return ticket;
    }

    public int getActiveTicketCount() {
        return activeTickets.size();
    }

    public int getCompletedTicketCount() {
        return completedTickets.size();
    }

    public int getTotalTicketCount() {
        return activeTickets.size() + completedTickets.size();
    }

    @Override
    public String toString() {
        return String.format("TicketManager: %d active, %d completed tickets",
                activeTickets.size(), completedTickets.size());
    }
}
