package pikk7.whydrink;

enum DrinkTypeEn implements DrinkType {
    beer(0.05),wine(0.07),vodka(0.4),jägermeister(0.35),whiskey(0.4),gin(0.37),champagne(0.07);

    private final double alcohol;

    DrinkTypeEn(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getAlcohol() {
        return alcohol;
    }
}
