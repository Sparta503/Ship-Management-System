public class LiquidContainer extends HeavyContainer {
    public LiquidContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        return 4.00 * this.getWeight();
    }
}
