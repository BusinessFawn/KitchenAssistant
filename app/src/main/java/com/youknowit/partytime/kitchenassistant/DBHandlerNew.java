package com.youknowit.partytime.kitchenassistant;

/**
 * Created by johnkonderla on 6/13/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHandlerNew extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "kitchenAssistant";
    // Ingredients table name
    private static final String TABLE_INGREDIENTS = "ingredients";

    // Recipe table name
    private static final String TABLE_RECIPE = "recipe";

    // Recipes to Ingredients table name
    private static final String TABLE_RECIPE_TO_INGREDIENT = "recipeToIngredient";

    //Recipe Table Column names
    private static final String RECIPE_ID = "recipeId";
    private static final String RECIPE_NAME = "recipeName";
    private static final String RECIPE_SERVINGS_MADE = "recipeServingsMade";
    private static final String RECIPE_LAST_MADE = "recipeLastMade";

    private static final String R2I_INGREDIENT_CAPACITY_USED = "r2iIngredientUsed";


    // Ingredients Table Column names
    private static final String INGREDIENT_ID = "ingredientId";
    private static final String INGREDIENT_NAME = "name";
    private static final String INGREDIENT_CAPACITY = "capacity";
    private static final String INGREDIENT_TYPE = "type";
    private static final String INGREDIENT_EXPIRATION = "expiration";



    public DBHandlerNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Ingredient Table
        String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + INGREDIENT_NAME + " TEXT,"
                + INGREDIENT_CAPACITY + " INTEGER DEFAULT 0," + INGREDIENT_TYPE + " " +
                "INTEGER DEFAULT 0," + INGREDIENT_EXPIRATION + " TEXT" + ")";
        db.execSQL(CREATE_INGREDIENT_TABLE);
        //Create Recipe Table
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
                + RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECIPE_NAME + " TEXT,"
                + RECIPE_LAST_MADE + " TEXT," + RECIPE_SERVINGS_MADE + " INTEGER" + ")";
        db.execSQL(CREATE_RECIPE_TABLE);
        //Creating Recipe to Item Table
        String CREATE_RECIPE_2_ITEM_TABLE = "CREATE TABLE " + TABLE_RECIPE_TO_INGREDIENT + "("
                + RECIPE_ID + " INTEGER, " + INGREDIENT_ID + " INTEGER, " + R2I_INGREDIENT_CAPACITY_USED +
                " INTEGER " + ")";
        db.execSQL(CREATE_RECIPE_2_ITEM_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_TO_INGREDIENT);
// Creating tables again
        onCreate(db);
    }

    public void dropDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE " + TABLE_RECIPE);
        db.execSQL("DROP TABLE " + TABLE_RECIPE_TO_INGREDIENT);
    }


    // Adding new ingredient
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, ingredient.getIngredientName()); // Ingredient Name
        values.put(INGREDIENT_CAPACITY, ingredient.getIngredientCapacity()); // Ingredient Capacity
        values.put(INGREDIENT_TYPE, ingredient.getIngredientType()); //Ingredient Type
        values.put(INGREDIENT_EXPIRATION, ingredient.getIngredientExpiration()); //Ingredient Expiration Date

// Inserting Row into
        db.insert(TABLE_INGREDIENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one ingredient
    public Ingredient getIngredient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INGREDIENTS, new String[]{INGREDIENT_ID,
                        INGREDIENT_NAME, INGREDIENT_CAPACITY, INGREDIENT_TYPE, INGREDIENT_EXPIRATION}, INGREDIENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Ingredient ingredientList = new Ingredient(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), cursor.getString(4));
        cursor.close();
        db.close();
// return ingredient
        return ingredientList;
    }
    // Getting All Ingredients
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(Integer.parseInt(cursor.getString(0)));
                ingredient.setIngredientName(cursor.getString(1));
                ingredient.setIngredientCapacity(Integer.parseInt(cursor.getString(2)));
                ingredient.setIngredientType(Integer.parseInt(cursor.getString(3)));
                ingredient.setIngredientExpiration(cursor.getString(4));
// Adding ingredient to list
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

// return ingredient list
        return ingredientList;
    }




    public ArrayList<Ingredient> getLikeIngredientName(String ingredientName) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
// Select items with names like the entered
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS + " WHERE " + INGREDIENT_NAME + " LIKE '%" + ingredientName + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(Integer.parseInt(cursor.getString(0)));
                ingredient.setIngredientName(cursor.getString(1));
                ingredient.setIngredientCapacity(Integer.parseInt(cursor.getString(2)));
                ingredient.setIngredientType(Integer.parseInt(cursor.getString(3)));
                ingredient.setIngredientExpiration(cursor.getString(4));
// Adding contact to list
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

// return contact list
        return ingredientList;
    }
    // Getting ingredients Count
    public int getIngredientsCount() {
        String countQuery = "SELECT * FROM " + TABLE_INGREDIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();

// return count
        return cursor.getCount();
    }
    // Updating a ingredient
    public int updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, ingredient.getIngredientName());
        values.put(INGREDIENT_CAPACITY, ingredient.getIngredientCapacity());
        values.put(INGREDIENT_TYPE, ingredient.getIngredientType());
        values.put(INGREDIENT_EXPIRATION, ingredient.getIngredientExpiration());

// updating row
        return db.update(TABLE_INGREDIENTS, values, INGREDIENT_ID + " = ?",
                new String[]{String.valueOf(ingredient.getIngredientId())});
    }

    // Deleting an ingredient
    public void deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS, INGREDIENT_ID + " = ?",
                new String[] { String.valueOf(ingredient.getIngredientId()) });
        db.close();
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

    // Updating a recipe
    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipe.getRecipeName());
        values.put(RECIPE_LAST_MADE, recipe.getRecipeLastMade());
        values.put(RECIPE_SERVINGS_MADE, recipe.getRecipeServingsMade());
        // updating row
        return db.update(TABLE_RECIPE, values, RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipe.getRecipeId())});
    }


    // Getting All Recipes
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
// Adding recipe to list
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

// return recipe list
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
    public int getLastRecipeId() {
        String selectQuery = "select * from sqlite_sequence where name=" + "'" + TABLE_RECIPE + "'";
        int recipeId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                recipeId = Integer.parseInt(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipeId;
    }


    // Getting All Ingredient's IDs used in the recipe
    public ArrayList<Integer> getRecipeIngredientIds(int recipeId) {
        ArrayList<Integer> recipeIngredientList = new ArrayList<>();
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
        cursor.close();
        db.close();

// return ingredient list
        return recipeIngredientList;
    }


    // Getting All Ingredient's capacity used in the recipe
    public ArrayList<Integer> getRecipeIngredientCapacity(int recipeId) {
        ArrayList<Integer> recipeIngredientList = new ArrayList<>();
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
        cursor.close();
        db.close();

// return ingredient list
        return recipeIngredientList;
    }

    //Delete Ingredients used in a particular Recipe. This is how I will update Ingredients in tandem with @addIngredientToRecipe
    public void deleteIngredientsUsed(int recipeId) {
        String deleteIngredients = "DELETE FROM " + TABLE_RECIPE_TO_INGREDIENT + "WHERE " + RECIPE_ID + " + " + recipeId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(deleteIngredients, null);
        cursor.close();
        db.close();
    }


}