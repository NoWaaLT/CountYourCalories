package edu.vvk_pit_21_i_nt.countyourcalories;

import java.util.HashMap;

public class RecipeProducts {

    HashMap<String, Float> recipeProducts;

    public RecipeProducts() {
        recipeProducts = new HashMap<String, Float>();
    }

    public HashMap<String, Float> getRecipeProducts() {
        return recipeProducts;
    }

    public void setRecipeProducts(HashMap<String, Float> recipeProducts) {
        this.recipeProducts = recipeProducts;
    }
}
