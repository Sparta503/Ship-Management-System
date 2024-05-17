import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    static ArrayList<Container> containers = new ArrayList<>();
    private static ArrayList<Port> ports = new ArrayList<>();
    private static ArrayList<Ship> ships = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);
    private static int portIDSimulator =1;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nSHIP MANAGEMENT SYSTEM\n" +
                    "\n1. Create container\n" +
                    "2. Create port\n" +
                    "3. Create ship\n" +
                    "4. Sail ship to port\n" +
                    "5. Refuel ship\n" +
                    "6. Load container to ship\n" +
                    "7. Unload +container from ship\n" +
                    "8. Exit\n");
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createContainer();
                    break;

                case 2:
                    createPort();
                    break;

                case 3:
                    createShip();
                    break;

                case 4:
                    sailShip();
                    break;

                case 5:
                   refuelShip();
                    break;

                case 6:
                    loadContainers();
                    break;

                case 7:
                    unloadContainers();
                    break;

                case 8:
                    System.out.println("Exiting The System...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }


        } while (choice != 8);
    }

    private static void createContainer() {
        System.out.print("Enter container ID: ");
        int containerID = sc.nextInt();
            if (Container.equals(containerID)){
                System.out.println("Container with ID " + containerID + " already exists!!");
                return;
            }

        System.out.print("Enter container weight: ");
        int weight = sc.nextInt();
        Container container;
        if (weight <= 5000) {
            container = new BasicContainer(containerID, weight);
            containers.add(container);
            System.out.println("You have successfully created a Basic Container");
        }
        else{
            container = new HeavyContainer(containerID, weight);
            containers.add(container);
            System.out.println("Your're about to create a Heavy Container!!");
            System.out.print("Enter container type (R) Refrigerated/ (L) Liquid / (O) Other): ");
            String containerType = sc.next();
        if (containerType.equalsIgnoreCase("R"))
        {
            container = new RefrigeratedContainer(containerID, weight);
            containers.add(container);
            System.out.println("You have successfully created a Refrigerated Container!!");
        }
        else if (containerType.equalsIgnoreCase("L"))
        {
                container = new LiquidContainer(containerID, weight);
                containers.add(container);

                System.out.println("You have successfully created a Liquid Container!!");

        } else if (containerType.equalsIgnoreCase("O"))
        {
            container = new HeavyContainer(containerID, weight);
            containers.add(container);
            System.out.println("You have successfully created a basic Heavy Container!!");
        }
        else{
            System.out.println("Container Not Created");
        }
        }

    }

    private static void createPort() {
        System.out.print("Enter the port latitude: ");
        double latitude = sc.nextDouble();

        System.out.print("Enter the port longitude: ");
        double longitude = sc.nextDouble();


        Port port = new Port(portIDSimulator++, latitude, longitude);
        ports.add(port);
        System.out.println("Port with ID " + port.getPortID() +" is created successfully!");
    }

    private static void createShip() {
        System.out.print("Enter the ship ID: ");
        int shipID = sc.nextInt();

        for(Ship ship : ships){
            if (ship.getShipID() == shipID){
                System.out.println("Ship " + shipID + " Already Exists");
                return;
            }
        }

        System.out.print("Enter ID OF The Port Where The Ship Initially Is: ");
        int currentPortID = sc.nextInt();
        Port currentPort = getPortByID(currentPortID);
        if (currentPort == null) {
            System.out.println("\nPort " + currentPortID + " Does Not Exist ! Create Port First Or Choose Another One");
            return;
        }

        System.out.print("Enter Maximum Weight Of All Containers In That Ship: ");
        int totalWeightCapacity = sc.nextInt();
        if (totalWeightCapacity <= 0) {
            System.out.println("Maximum Weight Can Not Be Zero");
            return;
        }

        System.out.print("Enter the maximum number of all containers the ship can carry: ");
        int maxNumberOfAllContainers = sc.nextInt();

        System.out.print("Enter the maximum number of heavy containers the ship can carry: ");
        int maxNumberOfHeavyContainers = sc.nextInt();

        System.out.print("Enter the maximum number of refrigerated containers the ship can carry: ");
        int maxNumberOfRefrigeratedContainers = sc.nextInt();

        System.out.print("Enter the maximum number of liquid containers the ship can carry: ");
        int maxNumberOfLiquidContainers = sc.nextInt();

        System.out.print("Enter Fuel Tank Capacity ");
        int fuelTankCapacity = sc.nextInt();
        if (fuelTankCapacity <= 0) {
            System.out.println("Fuel Tank Capacity Can Not Be Zero");
            return;
        }

        System.out.print("Enter Current Fuel Level ");
        int currentFuelLevel = sc.nextInt();


        System.out.print("Enter the fuel consumption per kilometer of the ship: ");
        double fuelConsumptionPerKM = sc.nextDouble();
        if (fuelConsumptionPerKM <= 0) {
            System.out.println("Fuel Consumption Can Not Be Zero");
            return;
        }


        Ship ship = new Ship(shipID, currentPort,
                totalWeightCapacity, maxNumberOfAllContainers,
                maxNumberOfHeavyContainers, maxNumberOfRefrigeratedContainers,
                maxNumberOfLiquidContainers, fuelConsumptionPerKM,
                fuelTankCapacity, currentFuelLevel);

        ships.add(ship);
        System.out.println("Ship created successfully!");
    }

    private static void sailShip() {
        System.out.print("Enter the ship ID: ");
        int shipID = sc.nextInt();

        Ship ship = getShipByID(shipID);
        if (ship == null) {
            System.out.println("Ship With ID : " + shipID + " Does Not Exist !");
            return;
        }

        System.out.print("Enter the destination port (Enter port ID): ");
        int destinationPortID = sc.nextInt();

        Port destinationPort = getPortByID(destinationPortID);
        if (destinationPort == null) {
            System.out.println("Port ID " +  destinationPortID + " not found, Unable to sail.");
            return;
        }

        boolean success = ship.sailTo(destinationPort);

        if (success) {
            System.out.println("Ship sailed successfully to the destination port.");
        } else {
            System.out.println("Unable to sail the ship to the destination port, Refuel to sail.");
        }
    }

    private static void loadContainers() {
        System.out.print("Enter the ship ID: ");
        int shipID = sc.nextInt();

        Ship ship = getShipByID(shipID);
        if (ship == null) {
            System.out.println("Ship With ID " + shipID + " Unable to load containers.");
            return;
        }

        System.out.print("Enter Container ID: ");
        int containerID = sc.nextInt();

        Container container = getContainerByID(containerID);
        if (container == null){
            System.out.println("Container With ID " + containerID + " not found. Unable to load containers");
            return;
        }
            boolean success = ship.load(container);

            if (success) {
                System.out.println("Container loaded successfully onto the ship.");
            } else {
                System.out.println("Unable to load the container onto the ship due to restrictions.");
            }
        }
    private static void unloadContainers() {
        System.out.print("Enter the ship ID: ");
        int shipID = sc.nextInt();

        Ship ship = getShipByID(shipID);
        if (ship == null) {
            System.out.println("Ship not found. Unable to unload containers.");
            return;
        }

        System.out.print("Enter Container ID: ");
        int containerID = sc.nextInt();

        Container container = getContainerByID(containerID);
        if (container == null){
            System.out.println("Container With ID " + containerID + " not found. Unable to unload containers");
            return;
        }

            boolean success = ship.unLoad(container);
            if (success) {
                System.out.println("Container unloaded successfully from the ship.");
            } else {
                System.out.println("Unable to unload the container from the ship.");
            }
        }

    private static void refuelShip(){

        System.out.print("Enter the ship ID: ");
        int shipId = sc.nextInt();

        System.out.print("Enter the amount of fuel to add: ");
        double fuelToAdd = sc.nextDouble();

        Ship ship = getShipByID(shipId);
        if (ship != null) {
            ship.reFuel(fuelToAdd);

        } else {
            System.out.println("Invalid ship ID.");
        }
    }


    private static Port getPortByID(int portID) {
        for (Port port : ports) {
            if (port.getPortID() == portID) {
                return port;
            }
        }
        return null;
    }

    private static Ship getShipByID(int shipID) {
        for (Ship ship : ships) {
            if (ship.getShipID() == shipID) {
                return ship;
            }
        }
        return null;
    }

    private static Container getContainerByID(int containerID) {
        for (Container container : containers){
            if (container.getID() == containerID){
                return container;
            }
        }
        return null;
    }
}



