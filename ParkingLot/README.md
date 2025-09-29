# Parking Lot Management System

A comprehensive Low-Level Design (LLD) implementation of a multi-floor parking lot system with support for different vehicle types, spot types, and EV charging facilities.

## Features

### 🚗 Vehicle Types Supported
- **Cars**: Standard passenger vehicles
- **Motorcycles/Bikes**: Two-wheelers with smaller space requirements
- **Trucks**: Large commercial vehicles requiring large spots
- **Vans**: Medium-sized vehicles

### 🅿️ Parking Spot Types
- **Compact**: Ideal for motorcycles and small vehicles
- **Regular**: Standard parking spots for cars
- **Large**: For trucks, vans, and large vehicles
- **Handicapped**: Accessible parking spots with premium pricing
- **Electric**: EV charging-enabled spots with additional charging fees

### 💰 Dynamic Pricing System
- **Base Rate**: $5 per hour
- **Vehicle Size Multiplier**:
  - Motorcycle: 0.5x (50% discount)
  - Car: 1.0x (standard rate)
  - Van: 1.5x (50% premium)
  - Truck: 2.0x (100% premium)
- **Spot Type Multiplier**:
  - Compact: 1.0x
  - Regular: 1.2x
  - Large: 1.5x
  - Handicapped: 1.3x
  - Electric: 1.2x + $2/hour charging fee

### 🏢 Multi-Floor Architecture
- Ground Floor (Floor 0): Entry/exit gates and mixed parking
- Multiple upper floors with optimized spot distribution
- Smart spot allocation based on vehicle type preferences

### 🚪 Entry/Exit Management
- Multiple entry gates for efficient traffic flow
- Dedicated exit gates with payment processing
- Dual-purpose gates for flexible operations
- Real-time gate usage tracking

## System Architecture

### Core Components

1. **Vehicle Hierarchy**
   - `Vehicle` (abstract base class)
   - `Car`, `Motorcycle`, `Truck`, `Van` (concrete implementations)

2. **Parking Infrastructure**
   - `ParkingSpot`: Individual parking spaces with status tracking
   - `Floor`: Manages spots on each floor level
   - `EntryExit`: Gate management for vehicle flow
   - `ParkingLot`: Main orchestrator class

3. **Ticket Management**
   - `Ticket`: Represents parking sessions with pricing calculation
   - `TicketManager`: Handles ticket lifecycle and revenue tracking

4. **Enums**
   - `VehicleType`: Vehicle classifications with size multipliers
   - `SpotType`: Parking spot categories with pricing
   - `SpotStatus`: Real-time spot availability
   - `EntryExitType`: Gate operation types

## Usage Example

```java
// Create parking lot with 3 floors
ParkingLot parkingLot = new ParkingLot("PL001", "Downtown Mall", "123 Main St", 3);

// Add entry/exit gates
parkingLot.addEntryExitGate(new EntryExit("ENTRY-1", EntryExitType.ENTRY));
parkingLot.addEntryExitGate(new EntryExit("EXIT-1", EntryExitType.EXIT));

// Add parking spots
parkingLot.addParkingSpot(0, new ParkingSpot("F0-R01", SpotType.REGULAR, 0));
parkingLot.addParkingSpot(0, new ParkingSpot("F0-EV01", SpotType.ELECTRIC, 0));

// Park a vehicle
Vehicle car = new Car("ABC-123", "Red", "John Doe");
Ticket ticket = parkingLot.parkVehicle(car, "ENTRY-1");

// Process exit and payment
double amount = parkingLot.removeVehicle("ABC-123", "EXIT-1");
System.out.println("Amount due: $" + amount);
```

## Running the Demo

```bash
# Compile all Java files
javac *.java

# Run the comprehensive demo
java ParkingLotDemo
```

The demo showcases:
- Setting up a 3-floor parking lot with 141 total spots
- Parking different types of vehicles
- Real-time occupancy tracking
- Payment processing with dynamic pricing
- EV charging fee calculation
- Revenue and usage statistics

## Key Design Principles

### SOLID Principles Applied
- **Single Responsibility**: Each class has a focused purpose
- **Open/Closed**: Easy to extend with new vehicle or spot types
- **Liskov Substitution**: Vehicle hierarchy maintains behavioral contracts
- **Interface Segregation**: Clean separation of concerns
- **Dependency Inversion**: High-level modules don't depend on low-level details

### Design Patterns Used
- **Strategy Pattern**: Different pricing strategies for vehicle/spot types
- **Factory Pattern**: Vehicle creation and spot allocation
- **Observer Pattern**: Real-time status updates
- **State Pattern**: Parking spot status management

## System Capabilities

### Real-time Monitoring
- Live occupancy rates per floor
- Available spots by type
- Vehicle distribution analytics
- Gate usage statistics

### Revenue Management
- Automatic fee calculation
- EV charging fee tracking
- Revenue breakdown by service type
- Payment processing integration

### Operational Features
- Spot reservation system
- Maintenance mode for spots
- Overflow handling across floors
- Vehicle location tracking

## Scalability Considerations

- **Horizontal Scaling**: Easy to add more floors and gates
- **Vertical Scaling**: Support for different facility types
- **Performance**: Efficient spot allocation algorithms
- **Maintenance**: Modular design for easy updates

## Future Enhancements

- Mobile app integration
- Automated payment systems
- IoT sensor integration
- Predictive analytics
- Loyalty programs
- Dynamic pricing based on demand

---

This parking lot system demonstrates comprehensive LLD principles with practical real-world applications, showcasing object-oriented design, scalable architecture, and business logic implementation.
