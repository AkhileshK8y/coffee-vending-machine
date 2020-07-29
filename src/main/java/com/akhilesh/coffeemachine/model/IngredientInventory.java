package com.akhilesh.coffeemachine.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Singleton Class
 * Ingredients present in inventory.
 */
public class IngredientInventory {
    private static IngredientInventory ingredientInventoryInstance = null;
    private List<Ingredient> ingredientList;

    public IngredientInventory() {
    }

    public IngredientInventory(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public static IngredientInventory getIngredientInventoryInstance(){
        if (ingredientInventoryInstance == null){
            ingredientInventoryInstance = new IngredientInventory();
        }
        return ingredientInventoryInstance;
    }

    /**
     * Check if ingredient is present in inventory
     * @param ingredientName
     * @return
     */
    private boolean isIngredientPresent(String ingredientName){
        for(Ingredient ingredient: ingredientList){
            if(ingredient.getIngredientName().equals(ingredientName)){
                return true;
            }
        }
        return false;
    }

    /**
     * IF ingredient is available in required quantity
     * @param ingredientName
     * @param ingredientQuantity
     * @return
     */
    private boolean isIngredientAvailable(String ingredientName, int ingredientQuantity){
        for(Ingredient ingredient: ingredientList){
            if (ingredient.getIngredientName().equals(ingredientName) && ingredient.getIngredientQuantity() >= ingredientQuantity){
                return true;
            }
        }
        return false;
    }

    /**
     * When preparing a beverage, while fetching
     * if ingredients are available in reqired quantity
     * update ingredients in the inventory
     * @param ingredientName
     * @param ingredientQuantity
     */
    private void updateIngredients(String ingredientName, int ingredientQuantity){
        for(Ingredient ingredient:ingredientList){
            if(ingredient.getIngredientName().equals(ingredientName)){
                ingredient.setIngredientQuantity(ingredient.getIngredientQuantity() - ingredientQuantity);
            }
        }
    }

    /**
     * When preparing a beverage, fetch the ingredients from the inventory.
     * @param ingredients
     * @return
     */
    public synchronized String fetchIngredients(List<Ingredient> ingredients){
        String deficits = "";
        Boolean allIngredientPresentandAvailable = true;
        for(Ingredient ingredient: ingredients){
            if(isIngredientPresent(ingredient.getIngredientName())){
                if(isIngredientAvailable(ingredient.getIngredientName(), ingredient.getIngredientQuantity())){
                    continue;
                } else {
                    allIngredientPresentandAvailable = false;
                    deficits = ingredient.getIngredientName() + " is not available in required amount.";
                    break;
                }
            } else {
                allIngredientPresentandAvailable = false;
                deficits = ingredient.getIngredientName() + " is not present.";
                break;
            }
        }
        if (allIngredientPresentandAvailable){
            for(Ingredient ingredient: ingredients) {
                updateIngredients(ingredient.getIngredientName(), ingredient.getIngredientQuantity());
            }
        }
        return deficits;
    }

    /**
     * Refill the ingredient in inventory
     * @param ingredientName
     * @param ingredientQuantity
     */
    public synchronized void refillIngredient(String ingredientName, int ingredientQuantity){
        if (ingredientQuantity < 0){
            throw new IllegalArgumentException("Ingredient Quantity cannot be less than 0...");
        }
        for(Ingredient ingredient: ingredientList){
            if (ingredient.getIngredientName().equals(ingredientName)){
                ingredient.setIngredientQuantity(ingredient.getIngredientQuantity() + ingredientQuantity);
                return;
            }
        }
        throw new IllegalArgumentException("Ingredient not present. Please add ingredient and try...");
    }

    /**
     * Add new ingredient in inventory
     * @param ingredientName
     * @param ingredientQuantity
     */
    public synchronized void addNewIngredient(String ingredientName, int ingredientQuantity){
        ingredientList.add(new Ingredient(ingredientName, ingredientQuantity));
    }

    /**
     * Check if an ingredient is running low.
     * @param ingredientName
     * @return
     */
    public boolean isIngredientRunningLow(String ingredientName){
        for(Ingredient ingredient: ingredientList){
            if(ingredient.getIngredientName().equals(ingredientName)){
                if(ingredient.getIngredientQuantity() < 100){
                    return true;
                } else {
                    return false;
                }
            }
        }
        throw new IllegalArgumentException("Ingredient not found...");
    }
}
