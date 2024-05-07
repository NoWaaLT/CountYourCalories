package edu.vvk_pit_21_i_nt.countyourcalories;

public class UserHistory {

    private int kcal;
    private int carbo;
    private int fat;
    private int protein;
    private int water;
    UserHistory() {
        kcal = 0;
        carbo = 0;
        fat = 0;
        protein = 0;
        water = 0;
    }
    UserHistory(int kcal, int carbo, int fat, int protein, int water) {
        this.kcal = kcal;
        this.carbo = carbo;
        this.fat = fat;
        this.protein = protein;
        this.water = water;
    }
    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getCarbo() {
        return carbo;
    }

    public void setCarbo(int carbo) {
        this.carbo = carbo;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }
}
