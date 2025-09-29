import java.util.*;

/**
 * Demo class showcasing the ParkingLot system with multiple floors,
 * different vehicle types, spot types, and EV charging facilities
 */
public class ParkingLotDemo {

    public static void main(String[] args) {
        System.out.println("=== PARKING LOT SYSTEM DEMO ===\n");

        // Create parking lot with 3 floors
        ParkingLot parkingLot = new ParkingLot("PL001", "Downtown Mall Parking", "123 Main St", 3);

        // Setup entry/exit gates on ground floor
        setupEntryExitGates(parkingLot);

        // Setup parking spots on each floor
        setupParkingSpots(parkingLot);

        // Create various vehicles
        List<Vehicle> vehicles = createTestVehicles();

        // Demonstrate parking operations
        demonstrateParkingOperations(parkingLot, vehicles);

        // Show parking lot status
        showParkingLotStatus(parkingLot);

        // Demonstrate payment and exit
        demonstratePaymentAndExit(parkingLot, vehicles);

        // Show final status and revenue
        showFinalStats(parkingLot);
    }

    private static void setupEntryExitGates(ParkingLot parkingLot) {
        System.out.println("Setting up Entry/Exit Gates on Ground Floor:");

        parkingLot.addEntryExitGate(new EntryExit("ENTRY-1", EntryExitType.ENTRY));
        parkingLot.addEntryExitGate(new EntryExit("ENTRY-2", EntryExitType.ENTRY));
        parkingLot.addEntryExitGate(new EntryExit("EXIT-1", EntryExitType.EXIT));
        parkingLot.addEntryExitGate(new EntryExit("BOTH-1", EntryExitType.BOTH));

        System.out.println("✓ Added 4 gates: 2 entry, 1 exit, 1 both\n");
    }

    private static void setupParkingSpots(ParkingLot parkingLot) {
        System.out.println("Setting up Parking Spots on Multiple Floors:");

        // Ground Floor (Floor 0) - Mixed spots
        setupFloorSpots(parkingLot, 0, "Ground Floor");

        // First Floor (Floor 1) - More regular spots
        setupFloorSpots(parkingLot, 1, "First Floor");

        // Second Floor (Floor 2) - Premium spots with more EV charging
        setupFloorSpots(parkingLot, 2, "Second Floor");

        System.out.println();
    }

    private static void setupFloorSpots(ParkingLot parkingLot, int floorNum, String floorName) {
        System.out.println(floorName + ":");

        // Add different types of spots
        int spotCounter = 1;

        // Compact spots (for motorcycles mainly)
        for (int i = 0; i < 10; i++) {
            parkingLot.addParkingSpot(floorNum,
                    new ParkingSpot(String.format("F%d-C%02d", floorNum, spotCounter++), SpotType.COMPACT, floorNum));
        }

        // Regular spots
        for (int i = 0; i < 20; i++) {
            parkingLot.addParkingSpot(floorNum,
                    new ParkingSpot(String.format("F%d-R%02d", floorNum, spotCounter++), SpotType.REGULAR, floorNum));
        }

        // Large spots (for trucks and vans)
        for (int i = 0; i < 8; i++) {
            parkingLot.addParkingSpot(floorNum,
                    new ParkingSpot(String.format("F%d-L%02d", floorNum, spotCounter++), SpotType.LARGE, floorNum));
        }

        // Electric spots (EV charging)
        int evSpots = (floorNum == 2) ? 8 : 5; // More EV spots on floor 2
        for (int i = 0; i < evSpots; i++) {
            parkingLot.addParkingSpot(floorNum,
                    new ParkingSpot(String.format("F%d-EV%02d", floorNum, spotCounter++), SpotType.ELECTRIC, floorNum));
        }

        // Handicapped spots
        for (int i = 0; i < 3; i++) {
            parkingLot.addParkingSpot(floorNum,
                    new ParkingSpot(String.format("F%d-H%02d", floorNum, spotCounter++), SpotType.HANDICAPPED,
                            floorNum));
        }

        Floor floor = parkingLot.getFloor(floorNum);
        System.out.println(
                String.format("  ✓ Added %d spots (10 Compact, 20 Regular, 8 Large, %d Electric, 3 Handicapped)",
                        floor.getTotalCapacity(), evSpots));
    }

    private static List<Vehicle> createTestVehicles() {
        System.out.println("Creating Test Vehicles:");

        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("ABC-123", VehicleType.CAR, "Red", "John Doe"),
                new Vehicle("XYZ-789", VehicleType.CAR, "Blue", "Jane Smith"),
                new Vehicle("BIKE-01", VehicleType.MOTORCYCLE, "Black", "Mike Johnson"),
                new Vehicle("BIKE-02", VehicleType.MOTORCYCLE, "Yellow", "Sarah Wilson"),
                new Vehicle("TRUCK-1", VehicleType.TRUCK, "White", "Bob's Delivery"),
                new Vehicle("VAN-456", VehicleType.VAN, "Green", "Express Cargo"),
                new Vehicle("EV-CAR1", VehicleType.CAR, "Silver", "Tech Enthusiast"), // Will use EV spot
                new Vehicle("LUXURY1", VehicleType.CAR, "Black", "VIP Customer"));

        vehicles.forEach(vehicle -> System.out.println("  ✓ " + vehicle.toString()));

        System.out.println();
        return vehicles;
    }

    private static void demonstrateParkingOperations(ParkingLot parkingLot, List<Vehicle> vehicles) {
        System.out.println("=== PARKING OPERATIONS ===");

        List<Ticket> tickets = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            try {
                String entryGate = (tickets.size() % 2 == 0) ? "ENTRY-1" : "ENTRY-2";
                Ticket ticket = parkingLot.parkVehicle(vehicle, entryGate);
                tickets.add(ticket);

                System.out.println(String.format("✓ Parked %s at %s via %s",
                        vehicle.getLicensePlate(),
                        ticket.getAssignedSpot().getSpotId(),
                        entryGate));

            } catch (Exception e) {
                System.out.println(String.format("✗ Failed to park %s: %s",
                        vehicle.getLicensePlate(), e.getMessage()));
            }
        }

        System.out.println();
    }

    private static void showParkingLotStatus(ParkingLot parkingLot) {
        System.out.println("=== PARKING LOT STATUS ===");
        System.out.println(parkingLot.toString());
        System.out.println();

        // Show floor-wise status
        for (int i = 0; i < parkingLot.getTotalFloors(); i++) {
            Floor floor = parkingLot.getFloor(i);
            System.out.println(floor.toString());
        }
        System.out.println();

        // Show available spots by type
        System.out.println("Available Spots by Type:");
        Map<SpotType, Integer> availableSpots = parkingLot.getAvailableSpotsByType();
        availableSpots.forEach(
                (type, count) -> System.out.println(String.format("  %s: %d spots", type.getDisplayName(), count)));
        System.out.println();

        // Show vehicle type distribution
        System.out.println("Current Vehicle Distribution:");
        Map<VehicleType, Integer> vehicleStats = parkingLot.getTicketManager().getVehicleTypeStats();
        vehicleStats
                .forEach((type, count) -> System.out.println(String.format("  %s: %d vehicles", type.name(), count)));
        System.out.println();
    }

    private static void demonstratePaymentAndExit(ParkingLot parkingLot, List<Vehicle> vehicles) {
        System.out.println("=== PAYMENT AND EXIT DEMO ===");

        // Let's simulate some time passing and then exit a few vehicles
        Vehicle firstVehicle = vehicles.get(0); // Car
        Vehicle bikeVehicle = vehicles.get(2); // Motorcycle
        Vehicle evVehicle = vehicles.get(6); // EV Car

        try {
            // Process exit for regular car
            double amount1 = parkingLot.removeVehicle(firstVehicle.getLicensePlate(), "EXIT-1");
            System.out.println(String.format("✓ %s exited. Amount due: $%.2f",
                    firstVehicle.getLicensePlate(), amount1));

            // Process exit for motorcycle
            double amount2 = parkingLot.removeVehicle(bikeVehicle.getLicensePlate(), "BOTH-1");
            System.out.println(String.format("✓ %s exited. Amount due: $%.2f",
                    bikeVehicle.getLicensePlate(), amount2));

            // Process exit for EV car (should have charging fee)
            double amount3 = parkingLot.removeVehicle(evVehicle.getLicensePlate(), "EXIT-1");
            System.out.println(String.format("✓ %s exited. Amount due: $%.2f (includes EV charging)",
                    evVehicle.getLicensePlate(), amount3));

        } catch (Exception e) {
            System.out.println("✗ Exit error: " + e.getMessage());
        }

        System.out.println();
    }

    private static void showFinalStats(ParkingLot parkingLot) {
        System.out.println("=== FINAL STATISTICS ===");

        // Revenue stats
        Map<String, Double> revenueStats = parkingLot.getTicketManager().getRevenueStats();
        System.out.println("Revenue Statistics:");
        System.out.println(String.format("  Total Revenue: $%.2f", revenueStats.get("totalRevenue")));
        System.out.println(String.format("  Parking Revenue: $%.2f", revenueStats.get("parkingRevenue")));
        System.out.println(String.format("  EV Charging Revenue: $%.2f", revenueStats.get("evChargingRevenue")));
        System.out.println();

        // Ticket stats
        TicketManager ticketManager = parkingLot.getTicketManager();
        System.out.println("Ticket Statistics:");
        System.out.println(String.format("  Active Tickets: %d", ticketManager.getActiveTicketCount()));
        System.out.println(String.format("  Completed Tickets: %d", ticketManager.getCompletedTicketCount()));
        System.out.println(String.format("  Total Tickets Issued: %d", ticketManager.getTotalTicketCount()));
        System.out.println();

        // Show some active tickets
        System.out.println("Sample Active Tickets:");
        ticketManager.getActiveTickets().stream()
                .limit(5)
                .forEach(ticket -> System.out.println("  " + ticket.toString()));
        System.out.println();

        // Gate usage
        System.out.println("Gate Usage:");
        parkingLot.getEntryExitGates().forEach(gate -> System.out.println("  " + gate.toString()));
        System.out.println();

        System.out.println("=== DEMO COMPLETED ===");
        System.out.println("This parking lot system supports:");
        System.out.println("✓ Multiple floors with different spot types");
        System.out.println("✓ Different vehicle types (Car, Motorcycle, Truck, Van)");
        System.out.println("✓ Variable pricing based on vehicle and spot type");
        System.out.println("✓ EV charging facilities with additional fees");
        System.out.println("✓ Multiple entry/exit gates on ground floor");
        System.out.println("✓ Comprehensive ticket management and revenue tracking");
        System.out.println("✓ Real-time occupancy monitoring");
    }
}
