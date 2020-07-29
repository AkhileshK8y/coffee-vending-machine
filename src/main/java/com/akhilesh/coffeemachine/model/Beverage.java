package com.akhilesh.coffeemachine.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Beverage Class
 * Beverage has name and list of ingredients used to prepare it
 */
public class Beverage {
    String beverageName;
    List<Ingredient> ingredientList;

    /**
     * Constructor to initialize a beverage
     * @param beverageName
     * @param ingredientList
     */
    public Beverage(String beverageName, List<Ingredient> ingredientList) {
        this.beverageName = beverageName;
        this.ingredientList = ingredientList;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * Modify ingredient of the beverage
     * @param ingredientName
     * @param newQuantity
     */
    public void modifyBeverageIngredient(String ingredientName, int newQuantity){
        for(Ingredient ingredient: ingredientList){
            if (ingredient.getIngredientName().equals(ingredientName)){
                ingredient.setIngredientQuantity(newQuantity);
            }
        }
    }

    /**
     * Add new ingredient to the beverage
     * @param ingredientName
     * @param ingredientQuantity
     */
    public void addIngredient(String ingredientName, int ingredientQuantity){
        ingredientList.add(new Ingredient(ingredientName, ingredientQuantity));
    }

    /**
     * Prepare the beverage.
     * Firstly try to fetch required ingredients from the inventory.
     * if successful, bevverage is prepared
     * if any deficit, beverage is not prepared.
     */
    public void prepare(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();
        if(ingredientInventory == null){
            System.out.println("Null ingredient Inventory");
            throw new NullPointerException("Ingredient inventory is NULL!!!!");
        }
        String inventoryDeficiencies = ingredientInventory.fetchIngredients(ingredientList);
        if(inventoryDeficiencies.isEmpty()){
            System.out.println(beverageName + " is prepared");
        } else {
            System.out.println(beverageName + " is not prepared. " + inventoryDeficiencies);

        }
    }
}
