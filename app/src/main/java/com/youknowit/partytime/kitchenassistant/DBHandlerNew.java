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
    // Contacts table name
    private static final String TABLE_INGREDIENTS = "ingredients";
    // Ingredients Table Columns names
    private static final String INGREDIENT_ID = "id";
    private static final String INGREDIENT_NAME = "name";
    private static final String INGREDIENT_CAPACITY = "capacity";

    public DBHandlerNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + INGREDIENT_ID + " INTEGER PRIMARY KEY," + INGREDIENT_NAME + " TEXT,"
                + INGREDIENT_CAPACITY + " TEXT" + ")";
        db.execSQL(CREATE_INGREDIENT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
// Creating tables again
        onCreate(db);
    }
    // Adding new ingredient
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, ingredient.getIngredientName()); // Ingredient Name
        values.put(INGREDIENT_CAPACITY, ingredient.getIngredientCapacity()); // Ingredient Capacity

// Inserting Row
        db.insert(TABLE_INGREDIENTS, null, values);
        db.close(); // Closing database connection
    }
    // Getting one ingredient
    public Ingredient getIngredient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INGREDIENTS, new String[]{INGREDIENT_ID,
                        INGREDIENT_NAME, INGREDIENT_CAPACITY}, INGREDIENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Ingredient contact = new Ingredient(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
// return ingredient
        return contact;
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
                ingredient.setIngredientCapacity(cursor.getString(2));
// Adding contact to list
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }

// return contact list
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
                ingredient.setIngredientCapacity(cursor.getString(2));
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