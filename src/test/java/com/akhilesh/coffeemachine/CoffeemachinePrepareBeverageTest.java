package com.akhilesh.coffeemachine;

import com.akhilesh.coffeemachine.controller.CoffeeMachineController;
import com.akhilesh.coffeemachine.model.Beverage;
import com.akhilesh.coffeemachine.model.Ingredient;
import com.akhilesh.coffeemachine.model.IngredientInventory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CoffeemachineApplicationTests.class})
public class CoffeemachinePrepareBeverageTest {

    /**
     * inventory have exact amount of ingredient required to make beverage
     * check if inventory become 0
     */
    @Test
    public void prepareWhenInventoryEqualsRequired(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("water", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("lemon", 10));
        Beverage beverage = new Beverage("lime_juice", bevIngredients);

        beverage.prepare();

        for(Ingredient ingredient: invIngredients){
            assertEquals(ingredient.getIngredientQuantity(), 0);
        }
    }

    /**
     * inventory do not have ingredient required to make beverage
     * check if inventory remains same meaning nothing is changed
     */
    @Test
    public void prepareWhenIngredientNotAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        invIngredients.add(new Ingredient("salt", 30));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("water", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("apple_syrup", 10));
        Beverage beverage = new Beverage("apple_juice", bevIngredients);

        beverage.prepare();

        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("water")){
                assertEquals(ingredient.getIngredientQuantity(), 100);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 10);
            }
            if(ingredient.getIngredientName().equals("lemon")){
                assertEquals(ingredient.getIngredientQuantity(), 10);
            }
            if(ingredient.getIngredientName().equals("salt")){
                assertEquals(ingredient.getIngredientQuantity(), 30);
            }
        }
    }

    /**
     * inventory do not have required amount of ingredient required to make beverage
     * check if inventory remains same meaning nothing is changed
     */
    @Test
    public void prepareWhenIngredientNotAvailableInRequiredQuantity(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("cocoa_powder", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("milk", 50));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("cocoa_powder", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("milk", 80));
        Beverage beverage = new Beverage("choco_juice", bevIngredients);

        beverage.prepare();

        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("cocoa_powder")){
                assertEquals(ingredient.getIngredientQuantity(), 100);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 10);
            }
            if(ingredient.getIngredientName().equals("milk")){
                assertEquals(ingredient.getIngredientQuantity(), 50);
            }
        }
    }

    /**
     * inventory have ingredient required to make beverage
     * check if inventory is updated
     */
    @Test
    public void prepareWhenAllIngredientAvailableInRequiredQuantity(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("coffee", 200));
        invIngredients.add(new Ingredient("sugar", 15));
        invIngredients.add(new Ingredient("milk", 150));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("coffee", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("milk", 100));
        Beverage beverage = new Beverage("coffee_shake", bevIngredients);

        beverage.prepare();

        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("coffee")){
                assertEquals(ingredient.getIngredientQuantity(), 100);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 5);
            }
            if(ingredient.getIngredientName().equals("milk")){
                assertEquals(ingredient.getIngredientQuantity(), 50);
            }
        }
    }

    /**
     * When ingredient not available in quantity
     * refill and prepare beverage
     * inventory should be updated all ingredients are updated
     */
    @Test
    public void prepareAfterRefillWhenIngredientNotQuantityAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("cocoa_powder", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("milk", 50));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("cocoa_powder", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("milk", 80));
        Beverage beverage = new Beverage("choco_juice", bevIngredients);

        beverage.prepare();

        ingredientInventory.refillIngredient("milk", 100);

        beverage.prepare();

        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("cocoa_powder")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("milk")){
                assertEquals(ingredient.getIngredientQuantity(), 70);
            }
        }
    }

    /**
     * When ingredient not available
     * add ingredient to inventory and prepare beverage
     * inventory should be updated all ingredients are updated
     */
    @Test
    public void prepareAfterAddingIngredientNotAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        invIngredients.add(new Ingredient("salt", 30));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("water", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("apple_syrup", 10));
        Beverage beverage = new Beverage("apple_juice", bevIngredients);

        beverage.prepare();
        ingredientInventory.addNewIngredient("apple_syrup", 100);
        beverage.prepare();
        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("water")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("lemon")){
                assertEquals(ingredient.getIngredientQuantity(), 10);
            }
            if(ingredient.getIngredientName().equals("salt")){
                assertEquals(ingredient.getIngredientQuantity(), 30);
            }
            if(ingredient.getIngredientName().equals("apple_syrup")){
                assertEquals(ingredient.getIngredientQuantity(), 90);
            }
        }
    }

    /**
     * When special beverage request
     * add ingredient to beverage and prepare beverage
     * inventory should be updated all ingredients are updated
     */
    @Test
    public void prepareAfterAddingIngredientsToBeverage(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        invIngredients.add(new Ingredient("salt", 30));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> bevIngredients = new ArrayList<>();
        bevIngredients.add(new Ingredient("water", 100));
        bevIngredients.add(new Ingredient("sugar", 10));
        bevIngredients.add(new Ingredient("apple_syrup", 10));
        Beverage beverage = new Beverage("apple_juice", bevIngredients);

        beverage.prepare();
        ingredientInventory.addNewIngredient("apple_syrup", 100);
        beverage.addIngredient("lemon", 5);
        beverage.prepare();
        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("water")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("sugar")){
                assertEquals(ingredient.getIngredientQuantity(), 0);
            }
            if(ingredient.getIngredientName().equals("lemon")){
                assertEquals(ingredient.getIngredientQuantity(), 5);
            }
            if(ingredient.getIngredientName().equals("salt")){
                assertEquals(ingredient.getIngredientQuantity(), 30);
            }
            if(ingredient.getIngredientName().equals("apple_syrup")){
                assertEquals(ingredient.getIngredientQuantity(), 90);
            }
        }
    }
}
