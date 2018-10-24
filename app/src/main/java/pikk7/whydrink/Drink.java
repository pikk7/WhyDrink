package pikk7.whydrink;

class Drink {
    DrinkType drinkType;
    int db;
    double alcoholDose;
    Glasses glass;

    public Drink(DrinkType drinkType, int db,Glasses glass) {
        this.drinkType = drinkType;
        this.db = db;
        this.glass=glass;

        this.alcoholDose=glass.getAmount()*0.0338140227*db*drinkType.getAlcohol();
    }

    public double getAlcoholDose() {
        return alcoholDose;
    }
}
