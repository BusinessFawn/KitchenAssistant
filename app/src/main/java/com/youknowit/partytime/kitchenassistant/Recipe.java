package com.youknowit.partytime.kitchenassistant;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pyrobones07 on 6/20/16.
 */
public class Recipe implements Parcelable {
    private int recipeId;
    private String recipeName;
    private String recipeLastMade;
    private int recipeServingsMade;
    private ArrayList<Integer> ingredientIds;
    private ArrayList<Integer> ingredientInventoryUsed;

    public Recipe() {

    }
    public Recipe(int recipeId, String recipeName, String recipeLastMade, int recipeServingsMade, ArrayList<Integer> ingredientIds, ArrayList<Integer> ingredientInventoryUsed
    ) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeLastMade = recipeLastMade;
        this.recipeServingsMade = recipeServingsMade;
        this.ingredientIds = ingredientIds;
        this.ingredientInventoryUsed = ingredientInventoryUsed;
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
    public void setRecipeLastMade(String recipeLastMade) {
        this.recipeLastMade = recipeLastMade;
    }

    public void setRecipeServingsMade(int recipeServingsMade) {
        this.recipeServingsMade = recipeServingsMade;
    }

    public void setIngredientIds(Ingredient ingredient) {
        ingredientIds.add(ingredient.getIngredientId());
    }

    public void setIngredientIds(ArrayList<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    public void setIngredientInventoryUsed(ArrayList<Integer> ingredientInventoryUsed) {
        this.ingredientInventoryUsed = ingredientInventoryUsed;
    }

    public void setIngredientInventoryUsed(Integer capacityUsed) {
        ingredientInventoryUsed.add(capacityUsed);
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName(){
        return recipeName;
    }

    public String getRecipeLastMade() {
        return recipeLastMade;
    }

    public int getRecipeServingsMade() {
        return recipeServingsMade;
    }

    public ArrayList<Integer> getIngredientIds() {
        return ingredientIds;
    }

    public ArrayList<Integer> getIngredientInventoryUsed() {
        return ingredientInventoryUsed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recipeId);
        dest.writeString(this.recipeName);
        dest.writeString(this.recipeLastMade);
        dest.writeInt(this.recipeServingsMade);
        dest.writeList(this.ingredientIds);
        dest.writeList(this.ingredientInventoryUsed);
    }

    protected Recipe(Parcel in) {
        this.recipeId = in.readInt();
        this.recipeName = in.readString();
        this.recipeLastMade = in.readString();
        this.recipeServingsMade = in.readInt();
        this.ingredientIds = new ArrayList<Integer>();
        in.readList(this.ingredientIds, Integer.class.getClassLoader());
        this.ingredientInventoryUsed = new ArrayList<Integer>();
        in.readList(this.ingredientInventoryUsed, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
