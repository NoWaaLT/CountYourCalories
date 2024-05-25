package edu.vvk_pit_21_i_nt.countyourcalories;

import java.util.HashMap;

public class RecipeProducts {
    private HashMap<String, product> productDetails;

    public RecipeProducts() {
        productDetails = new HashMap<>();
    }

    public HashMap<String, product> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(HashMap<String, product> productDetails) {
        this.productDetails = productDetails;
    }
}