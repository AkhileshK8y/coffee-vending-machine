package com.akhilesh.coffeemachine;

import com.akhilesh.coffeemachine.model.Ingredient;
import com.akhilesh.coffeemachine.model.IngredientInventory;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CoffeemachineApplicationTests.class})
public class CoffeemachineInventoryTests {

    /**
     * Fetch ingredients when available
     * check inventory behaviour
     * Expected no deficit message
     */
    @Test
    public void fetchIngredientsWhenAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> reqIngredients = new ArrayList<>();
        reqIngredients.add(new Ingredient("water", 50));
        reqIngredients.add(new Ingredient("sugar", 5));
        String deficit = ingredientInventory.fetchIngredients(reqIngredients);
        assertEquals(deficit,"");

    }

    /**
     * Fetch ingredients when not available
     * check inventory behaviour
     * Expected deficit message
     * 'ingredient not available in required amount'
     */
    @Test
    public void fetchIngredientsWhenNotAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> reqIngredients = new ArrayList<>();
        reqIngredients.add(new Ingredient("water", 150));
        reqIngredients.add(new Ingredient("sugar", 5));
        String actualOutput = ingredientInventory.fetchIngredients(reqIngredients);
        String expectedOutput = "water is not available in required amount.";
        assertEquals(actualOutput, expectedOutput);

    }

    /**
     * Fetch ingredients when not present
     * check inventory behaviour
     * Expected deficit message
     * 'ingredient not present'
     */
    @Test
    public void fetchIngredientsWhenNotPresent(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        List<Ingredient> reqIngredients = new ArrayList<>();
        reqIngredients.add(new Ingredient("water", 50));
        reqIngredients.add(new Ingredient("sugar", 5));
        reqIngredients.add(new Ingredient("salt", 5));
        String actualOutput = ingredientInventory.fetchIngredients(reqIngredients);
        String expectedOutput = "salt is not present.";
        assertEquals(actualOutput, expectedOutput);
    }

    /**
     * Refill ingredient when present in inventory
     * Updates ingredient quantity
     */
    @Test
    public void refillInventoryWhenAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        ingredientInventory.refillIngredient("water", 50);
        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("water")){
                assertEquals(ingredient.getIngredientQuantity(),150);
            }
        }
    }

    /**
     * Refill ingredient when not present in inventory
     * Throws Exception
     */
    @Test
    public void refillInventoryWhenNotAvailable(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        assertThrows(IllegalArgumentException.class, () -> {
            ingredientInventory.refillIngredient("rose_water", 70);
        });
    }

    /**
     * Refill ingredient in inventory with negative value
     * Throws Exception
     */
    @Test
    public void refillInventoryWithNegativeValue(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        assertThrows(IllegalArgumentException.class, () -> {
            ingredientInventory.refillIngredient("water", -10);
        });
    }

    /**
     * Add ingredient to inventory with negative value
     * Throws Exception
     */
    @Test
    public void addNewIngredientToInventoryWithNegativeValue(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        assertThrows(IllegalArgumentException.class, () -> {
            ingredientInventory.addNewIngredient("rose_water", -10);
        });
    }

    /**
     * Add ingredient to inventory with correct value
     * new ingredient is added with specified quantity
     */
    @Test
    public void addNewIngredientToInventoryWithCorrectValue(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        ingredientInventory.addNewIngredient("rose_water", 10);

        Boolean found_rose_water = false;
        for(Ingredient ingredient: invIngredients){
            if(ingredient.getIngredientName().equals("rose_water")){
                assertEquals(10, ingredient.getIngredientQuantity());
                found_rose_water = true;
            }
        }
        assertTrue(found_rose_water);
    }

    /**
     * Test if inventory is running low
     * Returns true is quantity is less than 100 false otherwise
     */
    @Test
    public void testIfInventryRunningLow(){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();

        List<Ingredient> invIngredients = new ArrayList<>();
        invIngredients.add(new Ingredient("water", 100));
        invIngredients.add(new Ingredient("sugar", 10));
        invIngredients.add(new Ingredient("lemon", 10));
        ingredientInventory.setIngredientList(invIngredients);

        ingredientInventory.addNewIngredient("rose_water", 10);

        Boolean isSugarRunninglow = ingredientInventory.isIngredientRunningLow("sugar");
        assertTrue(isSugarRunninglow);
        Boolean isWaterRunninglow = ingredientInventory.isIngredientRunningLow("water");
        assertTrue(!isWaterRunninglow);
    }

}
