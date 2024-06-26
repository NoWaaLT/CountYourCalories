package edu.vvk_pit_21_i_nt.countyourcalories;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {
    String recipeName;
    String[] recipeProducts;
    int recipeProductsCount;
    float recipeTotalProtein;
    float recipeTotalFat;
    float recipeTotalCarbs;
    float recipeTotalCalories;

    public Recipe() {
        recipeName = "";
        recipeProducts = new String[recipeProductsCount];
        recipeTotalProtein = 0.01F;
        recipeTotalFat = 0.01F;
        recipeTotalCarbs = 0.01F;
        recipeTotalCalories = 0.01F;

    }

    public Recipe(String recipeName, String[] recipeProducts, int recipeProductsCount, float recipeTotalProtein, float recipeTotalFat, float recipeTotalCarbs, float recipeTotalCalories) {
        this.recipeName = recipeName;
        this.recipeProducts = recipeProducts;
        this.recipeProductsCount = recipeProductsCount;
        this.recipeTotalProtein = recipeTotalProtein;
        this.recipeTotalFat = recipeTotalFat;
        this.recipeTotalCarbs = recipeTotalCarbs;
        this.recipeTotalCalories = recipeTotalCalories;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String[] getRecipeProducts() {
        return recipeProducts;
    }

    public void setRecipeProducts(String[] recipeProducts) {
        this.recipeProducts = recipeProducts;
    }

    public int getRecipeProductsCount() {
        return recipeProductsCount;
    }

    public void setRecipeProductsCount(int recipeProductsCount) {
        this.recipeProductsCount = recipeProductsCount;
    }

    public float getRecipeTotalProtein() {
        return recipeTotalProtein;
    }

    public void setRecipeTotalProtein(float recipeTotalProtein) {
        this.recipeTotalProtein = recipeTotalProtein;
    }

    public float getRecipeTotalFat() {
        return recipeTotalFat;
    }

    public void setRecipeTotalFat(float recipeTotalFat) {
        this.recipeTotalFat = recipeTotalFat;
    }

    public float getRecipeTotalCarbs() {
        return recipeTotalCarbs;
    }

    public void setRecipeTotalCarbs(float recipeTotalCarbs) {
        this.recipeTotalCarbs = recipeTotalCarbs;
    }

    public float getRecipeTotalCalories() {
        return recipeTotalCalories;
    }

    public void setRecipeTotalCalories(float recipeTotalCalories) {
        this.recipeTotalCalories = recipeTotalCalories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeProducts=" + Arrays.toString(recipeProducts) +
                ", recipeProductsCount=" + recipeProductsCount +
                ", recipeTotalProtein=" + recipeTotalProtein +
                ", recipeTotalFat=" + recipeTotalFat +
                ", recipeTotalCarbs=" + recipeTotalCarbs +
                ", recipeTotalCalories=" + recipeTotalCalories +
                '}';
    }
}

