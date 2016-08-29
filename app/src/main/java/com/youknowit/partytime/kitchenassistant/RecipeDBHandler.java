package com.youknowit.partytime.kitchenassistant;

/**
 * Created by pyrobones07 on 6/28/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class RecipeDBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "kitchenAssistant";

    // Recipe table name
    private static final String TABLE_RECIPE = "recipe";

    // Recipes to Ingredients table name
    private static final String TABLE_RECIPE_TO_INGREDIENT = "recipeToIngredient";

    //Recipe Table Column names
    private static final String RECIPE_ID = "recipeId";
    private static final String RECIPE_NAME = "recipeName";
    private static final String RECIPE_SERVINGS_MADE = "recipeServingsMade";
    private static final String RECIPE_LAST_MADE = "recipeLastMade";

    private static final String INGREDIENT_ID = "id";
    private static final String R2I_INGREDIENT_CAPACITY_USED = "r2iIngredientUsed";


    public RecipeDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Recipe Table
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
                + RECIPE_ID + " INTEGER PRIMARY KEY, " + RECIPE_NAME + " TEXT,"
                + RECIPE_LAST_MADE + " TEXT," + RECIPE_SERVINGS_MADE + " INTEGER" + ")";
        db.execSQL(CREATE_RECIPE_TABLE);

        String CREATE_RECIPE_2_ITEM_TABLE = "CREATE TABLE " + TABLE_RECIPE_TO_INGREDIENT + "("
                + RECIPE_ID + " INTEGER " + INGREDIENT_ID + " INTEGER " + R2I_INGREDIENT_CAPACITY_USED +
                " INTEGER " + ")";
        db.execSQL(CREATE_RECIPE_2_ITEM_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_TO_INGREDIENT);
// Creating tables again
        onCreate(db);
    }

    public void dropDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_RECIPE);
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipe.getRecipeName()); // Recipe Name
        values.put(RECIPE_LAST_MADE, recipe.getRecipeLastMade()); // Last Time the Recipe Was Made
        values.put(RECIPE_SERVINGS_MADE, recipe.getRecipeServingsMade()); //How Many Servings the Recipe Makes
// Inserting Row into Recipe
        db.insert(TABLE_RECIPE, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Ingredients
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setRecipeId(Integer.parseInt(cursor.getString(0)));
                recipe.setRecipeName(cursor.getString(1));
                recipe.setRecipeLastMade(cursor.getString(2));
                recipe.setRecipeServingsMade(Integer.parseInt(cursor.getString(3)));
// Adding ingredient to list
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

// return ingredient list
        return recipeList;
    }
    public void addIngredientToRecipe(Recipe recipe, ArrayList<Integer> ingredientIdArray,
                                      ArrayList<Integer> ingredientCapacityUsed) {
        SQLiteDatabase db = this.getReadableDatabase();

        for (int i = 0; i < ingredientIdArray.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(RECIPE_ID, recipe.getRecipeId());
            values.put(INGREDIENT_ID, ingredientIdArray.get(i));
            values.put(R2I_INGREDIENT_CAPACITY_USED, ingredientCapacityUsed.get(i));
            db.insert(TABLE_RECIPE_TO_INGREDIENT, null, values);
        }
        db.close(); // Closing database connection
    }


    // Getting All Ingredient's IDs used in the recipe
    public List<Integer> getRecipeIngredientIds(int recipeId) {
        List<Integer> recipeIngredientList = new ArrayList<>();
        String selectingRecipeId = Integer.toString(recipeId);
// Select All Query
        String selectQuery = "SELECT " + INGREDIENT_ID + " FROM " + TABLE_RECIPE_TO_INGREDIENT +
                " WHERE " + RECIPE_ID +" = " + selectingRecipeId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int ingredient = Integer.parseInt(cursor.getString(0));
// Adding ingredient to list
                recipeIngredientList.add(ingredient);
            } while (cursor.moveToNext());
        }

// return ingredient list
        return recipeIngredientList;
    }


    // Getting All Ingredient's capacity used in the recipe
    public List<Integer> getRecipeIngredientCapacity(int recipeId) {
        List<Integer> recipeIngredientList = new ArrayList<>();
        String selectingRecipeId = Integer.toString(recipeId);
// Select All Query
        String selectQuery = "SELECT " + R2I_INGREDIENT_CAPACITY_USED + " FROM " + TABLE_RECIPE_TO_INGREDIENT +
                " WHERE " + RECIPE_ID +" = " + selectingRecipeId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int ingredient = Integer.parseInt(cursor.getString(0));
// Adding ingredient to list
                recipeIngredientList.add(ingredient);
            } while (cursor.moveToNext());
        }

// return ingredient list
        return recipeIngredientList;
    }
}
