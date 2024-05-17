import java.util.ArrayList;
import java.util.Comparator;

public class Ship implements IShip {
    private int ID;
    private double fuel;
    private Port currentPort;
    private int totalWeightCapacity;
    private int maxNumberOfAllContainers;
    private int maxNumberOfHeavyContainers;
    private int maxNumberOfRefrigeratedContainers;
    private int maxNumberOfLiquidContainers;
    private double fuelConsumptionPerKM;

    private double fuelTankCapacity;
    private double currentFuelLevel;
    private ArrayList<Container> containers;

    public Ship(int ID, Port currentPort, int totalWeightCapacity, int maxNumberOfAllContainers,
                int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers,
                int maxNumberOfLiquidContainers, double fuelConsumptionPerKM, double fuelTankCapacity, double currentFuelLevel) {
        this.ID = ID;
        this.currentPort = currentPort;
        this.totalWeightCapacity = totalWeightCapacity;
        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
        this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.fuelTankCapacity = fuelTankCapacity;
        this.currentFuelLevel = currentFuelLevel;
        this.containers = new ArrayList<>();
    }

    public ArrayList<Container> getCurrentContainers() {
        ArrayList<Container> sortedContainers = new ArrayList<>(containers);
        sortedContainers.sort(Comparator.comparingInt(Container::getID));
        return sortedContainers;
    }

    public boolean sailTo(Port p) {
        double requiredFuel = calculateRequiredFuel(p);
        if (requiredFuel <=this.fuel) {
            this.fuel -= requiredFuel;
            this.currentPort.outgoingShip(this);
            this.currentPort = p;
            p.incomingShip(this);
            return true;
        } else {
            return false;
        }
    }

    public void reFuel(double newFuel) {
        if (this.fuelTankCapacity < newFuel)
        {
            System.out.println("Fuel Tank Capacity reached You can't refuel!!");

        } else if ((this.currentFuelLevel + newFuel) > this.fuelTankCapacity) {
            System.out.println("Fuel Tank Capacity reached You can't refuel!!");
        } else
        {
            this.fuel += newFuel;
            System.out.println("Ship refueled successfully.");

        }

    }

    public boolean load(Container cont) {
        if (canLoadContainer(cont)) {
            containers.add(cont);
            return true;
        } else {
            return false;
        }
    }

    public boolean unLoad(Container cont) {
        if (containers.contains(cont)) {
            containers.remove(cont);
            return true;
        } else {
            return false;
        }
    }

    private boolean canLoadContainer(Container cont) {
        if (containers.size() >= maxNumberOfAllContainers) {
            return false;
        }

        int currentWeight = getCurrentWeight();
        int newWeight = currentWeight + cont.getWeight();
        if (newWeight > totalWeightCapacity) {
            return false;
        }

        int heavyContainers = 0;
        int refrigeratedContainers = 0;
        int liquidContainers = 0;
        for (Container container : containers) {
            if (container instanceof HeavyContainer) {
                heavyContainers++;
            } else if (container instanceof RefrigeratedContainer) {
                refrigeratedContainers++;
            } else if (container instanceof LiquidContainer) {
                liquidContainers++;
            }
        }
        if (cont instanceof HeavyContainer && heavyContainers >= maxNumberOfHeavyContainers) {
            System.out.println("Maximum number of containers that the ship can carry have been reached");
            return false;
        } else if (cont instanceof RefrigeratedContainer && refrigeratedContainers >= maxNumberOfRefrigeratedContainers) {
            return false;
        } else if (cont instanceof LiquidContainer && liquidContainers >= maxNumberOfLiquidContainers) {
            return false;
        }

        return true;
    }

    private int getCurrentWeight() {
        int currentWeight = 0;
        for (Container container : getCurrentContainers()) {
            currentWeight += container.getWeight();
        }
        return currentWeight;
    }

    private double calculateRequiredFuel(Port p) {
        double distance = currentPort.getDistance(p);
        return distance * fuelConsumptionPerKM;
    }

    public int getShipID() {
        return ID;
    }
}