package com.akhilesh.coffeemachine.model;

/**
 * Ingredient class contains ingredientName and quantity.
 */
public class Ingredient {
    String ingredientName;
    int ingredientQuantity;

    public Ingredient(String ingredientName, int ingredientQuantity) {
        if (ingredientQuantity < 0){
            throw new IllegalArgumentException("Ingredient Quantity cannot be less than 0...");
        }
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(int ingredientQuantity) {
        if (ingredientQuantity < 0){
            throw new IllegalArgumentException("Ingredient Quantity cannot be less than 0...");
        }
        this.ingredientQuantity = ingredientQuantity;
    }
}
