package pikk7.whydrink;

enum DrinkTypeHu implements DrinkType {
    sör(0.05),bor(0.07),vodka(0.4),jägermeister(0.35),whiskey(0.4),gin(0.37),pezsgő(0.07);

    private final double alcohol;

    DrinkTypeHu(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getAlcohol() {
        return alcohol;
    }
}
