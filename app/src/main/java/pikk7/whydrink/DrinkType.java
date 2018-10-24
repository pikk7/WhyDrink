package pikk7.whydrink;

enum DrinkType {
    BEER(0.05),WINE(0.07),VODKA(0.4),JAGER(0.35),WHISKEY(0.4),GIN(0.37),CHAMPAGNE(0.07);

    private final double alcohol;

    DrinkType(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getAlcohol() {
        return alcohol;
    }
}
