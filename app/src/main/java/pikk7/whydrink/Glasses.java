package pikk7.whydrink;

enum Glasses {
    glass(300),jug(500),shot(40);

    public double getAmount() {
        return amount;
    }

    private final double amount;
    Glasses(double v) {
        this.amount=v;
    }
}
