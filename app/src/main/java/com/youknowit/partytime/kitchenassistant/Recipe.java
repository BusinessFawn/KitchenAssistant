package com.youknowit.partytime.kitchenassistant;

/**
 * Created by pyrobones07 on 6/20/16.
 */
public class Recipe {
    private int recipeId;
    private String recipeName;
    private int recipeLastMade;
    private int recipeServingsMade;

    public Recipe() {

    }
    public Recipe(int recipeId, String recipeName, int recipeLastMade, int recipeServingsMade) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeLastMade = recipeLastMade;
        this.recipeServingsMade = recipeServingsMade;
    }
    @Override
    public String toString() {
        return recipeName + " last made " + recipeLastMade +
                " \r\n will make " + recipeServingsMade;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId=recipeId;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public void setRecipeLastMade(int recipeLastMade) {
        this.recipeLastMade = recipeLastMade;
    }

    public void setRecipeServingsMade(int recipeServingsMade) {
        this.recipeServingsMade = recipeServingsMade;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public String getRecipeName(){
        return recipeName;
    }

    public int getRecipeLastMade() {
        return recipeLastMade;
    }

    public int getRecipeServingsMade() {
        return recipeServingsMade;
    }
}