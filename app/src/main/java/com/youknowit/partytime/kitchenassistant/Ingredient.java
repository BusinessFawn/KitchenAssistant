package com.youknowit.partytime.kitchenassistant;

/**
 * Created by pyrobones07 on 5/23/16.
 */
public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private int ingredientCapacity;
    private int ingredientType;
    private String ingredientTypeString;
    public Ingredient()
    {
    }
    public Ingredient(int ingredientId,String ingredientName, int ingredientCapacity, int ingredientType)
    {
        this.ingredientId=ingredientId;
        this.ingredientName=ingredientName;
        this.ingredientCapacity=ingredientCapacity;
        this.ingredientType=ingredientType;
        switch (ingredientType) {
            case 0:
                ingredientTypeString = "Other";
                break;
            case 1:
                ingredientTypeString = "Percent";
                break;
            case 2:
                ingredientTypeString = "Ounces";
                break;
            case 3:
                ingredientTypeString = "Grams";
                break;
        }
    }
    @Override
    public String toString() {
        return ingredientName + " with " + ingredientCapacity + " " + ingredientTypeString;
    }
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientCapacity(int ingredientCapacity) {
        this.ingredientCapacity = ingredientCapacity;
    }
    public void setIngredientType(int ingredientType) {
        this.ingredientType=ingredientType;
        switch (ingredientType) {
            case 0:
                ingredientTypeString = "other";
                break;
            case 1:
                ingredientTypeString = "percent";
                break;
            case 2:
                ingredientTypeString = "ounces";
                break;
            case 3:
                ingredientTypeString = "grams";
                break;
        }
    }
    public int getIngredientId() {
        return ingredientId;
    }
    public int getIngredientCapacity() {
        return ingredientCapacity;
    }
    public String getIngredientName() {
        return ingredientName;
    }

    public int getIngredientType() {
        return ingredientType;
    }

    public String getIngredientTypeString() {
        return ingredientTypeString;
    }
}
