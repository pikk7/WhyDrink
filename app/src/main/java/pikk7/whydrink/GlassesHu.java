package pikk7.whydrink;

enum GlassesHu implements Glasses{
    pohár(300),korsó(500),feles(40);

    public double getAmount() {
        return amount;
    }

    private final double amount;
    GlassesHu(double v) {
        this.amount=v;
    }
}
