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
import java.util.List;

public class DBHandlerNew extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "kitchenAssistant";
    // Ingredients table name
    private static final String TABLE_INGREDIENTS = "ingredients";

    // Recipes to Ingredients table name
    private static final String TABLE_RECIPE_TO_INGREDIENT = "recipeToIngredient";

    // Ingredients Table Column names
    private static final String INGREDIENT_ID = "id";
    private static final String INGREDIENT_NAME = "name";
    private static final String INGREDIENT_CAPACITY = "capacity";
    private static final String INGREDIENT_TYPE = "type";
    private static final String INGREDIENT_EXPIRATION = "expiration";

    //Recipe to Ingredient Table Column names
    private static final String R2I_ID = "r2iId";
    private static final String R2I_INGREDIENT_ID = "r2iIngredientId";
    private static final String R2I_RECIPE_ID = "r2iRecipeId";
    private static final String R2I_INGREDIENT_CAPACITY_USED = "r2iIngredientUsed";


    public DBHandlerNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Ingredient Table
        String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + INGREDIENT_ID + " INTEGER PRIMARY KEY," + INGREDIENT_NAME + " TEXT,"
                + INGREDIENT_CAPACITY + " INTEGER DEFAULT 0," + INGREDIENT_TYPE + " " +
                "INTEGER DEFAULT 0," + INGREDIENT_EXPIRATION + " TEXT" + ")";
        db.execSQL(CREATE_INGREDIENT_TABLE);
        //Create Recipe to Ingredient Table
        String CREATE_I2R_TABLE = "CREATE TABLE " + TABLE_RECIPE_TO_INGREDIENT + "("
                + R2I_ID + " INTEGER PRIMARY KEY," + R2I_RECIPE_ID + " INTEGER,"
                + R2I_INGREDIENT_ID + " INTEGER," + R2I_INGREDIENT_CAPACITY_USED + " INTEGER" + ")";
        db.execSQL(CREATE_I2R_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
// Creating tables again
        onCreate(db);
    }

    public void dropDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_INGREDIENTS);
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
// return ingredient
        return ingredientList;
    }
    // Getting All Ingredients
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
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

// return ingredient list
        return ingredientList;
    }




    public List<Ingredient> getLikeIngredientName(String ingredientName) {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
// Select items with names like the entered
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS + " where " + INGREDIENT_NAME + " like '%" + ingredientName + "%'";

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

}