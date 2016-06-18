package com.youknowit.partytime.kitchenassistant;

/**
 * Created by pyrobones07 on 5/23/16.
 */
public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private String ingredientCapacity;
    public Ingredient()
    {
    }
    public Ingredient(int ingredientId,String ingredientName,String ingredientCapacity)
    {
        this.ingredientId=ingredientId;
        this.ingredientName=ingredientName;
        this.ingredientCapacity=ingredientCapacity;
    }
    @Override
    public String toString() {
        return ingredientName + " with a capacity of " + ingredientCapacity;
    }
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientCapacity(String ingredientCapacity) {
        this.ingredientCapacity = ingredientCapacity;
    }
    public int getIngredientId() {
        return ingredientId;
    }
    public String getIngredientCapacity() {
        return ingredientCapacity;
    }
    public String getIngredientName() {
        return ingredientName;
    }

}
