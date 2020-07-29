package com.akhilesh.coffeemachine.controller;

import com.akhilesh.coffeemachine.model.Beverage;
import com.akhilesh.coffeemachine.model.BeverageCounters;
import com.akhilesh.coffeemachine.model.Ingredient;
import com.akhilesh.coffeemachine.model.IngredientInventory;
import com.akhilesh.coffeemachine.properties.CoffeeMachineProperties;
import org.springframework.stereotype.Controller;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.concurrent.*;

/**
 * Central controller class. Creates context and makes beverages.
 */
@Controller
public class CoffeeMachineController {
    private List<Beverage> beverageList;

    public List<Beverage> getBeverageList() {
        return beverageList;
    }

    public void setBeverageList(List<Beverage> beverageList) {
        this.beverageList = beverageList;
    }

    /**
     * Bsed on the configuration provided in input
     * Initialise inventory, available beverages and outlets
     * @param machine: Machine configuration in Map
     */
    public void initializeControllerContext(LinkedHashMap<String, ?> machine){
        for(Map.Entry<String, ?> entry: machine.entrySet()){
            if(entry.getKey().equals("total_items_quantity")){
                initializeIngredientInventory((LinkedHashMap<String, ?>) entry.getValue());
            }
            if (entry.getKey().equals("beverages")){
                initializeBeverageList((LinkedHashMap<String, ?>) entry.getValue());
            }
            if (entry.getKey().equals("outlets")){
                initializeBeverageCounters((LinkedHashMap<String, ?>)entry.getValue());
            }
        }
    }

    /**
     * Creates a threadpool equalling to the number of counters
     * Using threadpool we call parallel requests of making beverage.
     * Uses executorservice
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void makeBeverages() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(BeverageCounters.getNumberOfCounters());
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        List<Beverage> beverageList = getBeverageList();
        for(Beverage beverage: beverageList){
            callables.add(new Callable<String>() {
                public String call() throws Exception {
                    beverage.prepare();
                    return beverage.getBeverageName();
                }
            });

        }

        executorService.invokeAll(callables);

        executorService.shutdown();

    }

    /**
     * Initialize available ingredient inventory
     * @param ingredients: Map view of available ingredients
     */
    private void initializeIngredientInventory(LinkedHashMap<String, ?> ingredients){
        IngredientInventory ingredientInventory = IngredientInventory.getIngredientInventoryInstance();
        ingredientInventory.setIngredientList(getIngredientList(ingredients));
    }

    /**
     * Initialize number of counters
     * @param beverageCounters
     */
    private void initializeBeverageCounters(LinkedHashMap<String, ?> beverageCounters){
        for (Map.Entry<String, ?> entry: beverageCounters.entrySet()){
            if(entry.getKey().equals("count_n")){
                BeverageCounters.setNumberOfCounters(Integer.parseInt(entry.getValue().toString()));
            }
        }
    }

    /**
     * Initialize beverage catalog
     * @param beverages: Map view of beverages
     */
    private void initializeBeverageList(LinkedHashMap<String, ?> beverages){
        List<Beverage> beverageList = new ArrayList<>();
        for (Map.Entry<String, ?> entry: beverages.entrySet()){
            String beverageName = entry.getKey();
            List<Ingredient> ingredientList = getIngredientList((LinkedHashMap<String, ?>)entry.getValue());
            beverageList.add(new Beverage(beverageName, ingredientList));
        }
        setBeverageList(beverageList);
    }

    /**
     * Map view of ingredients is converted to list view
     * @param ingredients: Map view of ingredients
     * @return: List view of ingredients
     */
    private List<Ingredient> getIngredientList(LinkedHashMap<String, ?> ingredients){
        List<Ingredient> ingredientList = new ArrayList<>();
        for(Map.Entry<String, ?> entry: ingredients.entrySet()){
            ingredientList.add(new Ingredient(entry.getKey(), Integer.parseInt(entry.getValue().toString())));
        }
        return ingredientList;
    }
}
