package com.youknowit.partytime.kitchenassistant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyrobones07 on 5/23/16.
 */
public class DBHandler extends SQLiteOpenHelper {
    //DB version
    private static final int DATABASE_VERSION = 1;
    //DB name
    private static final String DATABASE_NAME = "kitchenAssistent";
    //Tables
    private static final String TABLE_INGREDIENTS = "Ingredients";
    //Ingredients table Column names
    private static final String INGREDIENT_ID = "ingredientID";
    private static final String INGREDIENT_NAME = "ingredientName";
    //private static final String INGREDIENT_CAPACITY = "ingredientCapacity";
    //private static final String INGREDIENT_BARCODE = "ingredientBarcode";

    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + INGREDIENT_ID + " INTEGER PRIMARY KEY," +
                INGREDIENT_NAME + "TEXT " + ")";
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        System.out.println(CREATE_INGREDIENTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }

    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, ingredient.getIngredientName());
        //values.put(INGREDIENT_CAPACITY, ingredient.getIngredientCapacity());
        //values.put(INGREDIENT_BARCODE, ingredient.getIngredientBarcode());
        //inserting Row
        db.insert(TABLE_INGREDIENTS, null, values);
        db.close();

    }
    public Ingredient getIngredient(int id) { //Get Ingredient by ID
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INGREDIENTS, new String[] {
                INGREDIENT_ID, INGREDIENT_NAME, /*INGREDIENT_CAPACITY, INGREDIENT_BARCODE*/
        }, INGREDIENT_ID + "=?", new String[] {
                String.valueOf(id)
        }, null, null, null, null);


       /* if (cursor != null)
            cursor.moveToFirst();
            Ingredient concat = new Ingredient(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));*/
        return /*concat*/null;
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        //select *...
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(Integer.parseInt(cursor.getString(0)));
                ingredient.setIngredientName(cursor.getString(1));
                /*ingredient.setIngredientCapacity(Integer.parseInt(cursor.getString(2)));*/
                /*ingredient.setIngredientBarcode(Integer.parseInt(cursor.getString(3)));*/
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }
        return ingredientList;
    }

    //get a count of ingredients
    public int getIngredientCount() {
        String countQuery = "SELECT * FROM " + TABLE_INGREDIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
    //Update record
    public int updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INGREDIENT_NAME, ingredient.getIngredientName());
        /*values.put(INGREDIENT_CAPACITY, ingredient.getIngredientCapacity());*/
        /*values.put(INGREDIENT_BARCODE, ingredient.getIngredientBarcode());*/
        return db.update(TABLE_INGREDIENTS, values, INGREDIENT_ID +
                " = ?", new String[]{String.valueOf(ingredient.getIngredientId())});
    }

    //Delete record
    public void deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS, INGREDIENT_ID + " = ?", new String[] {String.valueOf((ingredient.getIngredientId()))});
        db.close();
    }
}
