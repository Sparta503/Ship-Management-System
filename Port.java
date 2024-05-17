import java.util.ArrayList;

public class Port implements IPort {
    private int ID;
    private double latitude;
    private double longitude;

    private static int portIdSimulator = 0;
    private ArrayList<Container> containers; //keeps record of the containers.
    private ArrayList<Ship> history; //keeps track of every ship that has visited
    private ArrayList<Ship> current; // keeps track of the ships currently here


    Port(int ID, double latitude, double longitude) {
        this.ID = portIdSimulator++;
        this.latitude = latitude;
        this.longitude = longitude;
        this.containers = new ArrayList<>();
        this.history = new ArrayList<>();
        this.current = new ArrayList<>();
    }

    public void incomingShip(Ship s) {
        if (!current.contains(s)) {
            current.add(s);
        }
    }

    public void outgoingShip(Ship s) {
        if (current.contains(s)) {
            current.remove(s);
        }
        if (!history.contains(s)) {
            history.add(s);
        }
    }

    public double   getDistance(Port other) {
  //      double latDiff = Math.abs(this.latitude - other.latitude);
  //      double lonDiff = Math.abs(this.longitude - other.longitude);
  //      System.out.println(Math.sqrt(latDiff * latDiff + lonDiff * lonDiff));
  //      return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
        int Radius = 6371;
        double distance_latitude = Math.toRadians(other.latitude - this.latitude);
        double distance_longitude = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(distance_latitude / 2) * Math.sin(distance_latitude/2) +
                Math.cos(Math.toRadians(other.longitude)) * Math.cos(Math.toRadians(this.latitude))*
                        Math.sin(distance_longitude / 2) * Math.sin(distance_longitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sin(1 - a));

        double distance = Radius * c;
        return distance;

    }

    public int getPortID() {
        return ID;
    }
}
