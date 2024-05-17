public abstract class Container {
    private int ID;
    private int weight;

    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    public abstract double consumption();


    static boolean equals(int containerID) {
        for (Container container : Main.containers) {
            if (container.getID() == containerID) {
                return true;
            }
        }

//        return this.getClass() == other.getClass() &&
//                this.ID == other.ID &&
//                this.weight == other.weight;
//    }
        return false;
    }
}