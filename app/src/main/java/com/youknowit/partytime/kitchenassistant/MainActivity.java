package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.youknowit.partytime.kitchenassistant.MESSAGE";
    Button submitButton;
    Button queryButton;
    Button queryRecipeButton;
    Button deleteButton;
    Button sendMessageButton;
    private Recipe aNewRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        submitButton = (Button) findViewById(R.id.commit);
        queryButton = (Button) findViewById(R.id.view);
        queryRecipeButton = (Button) findViewById(R.id.viewRecipes);
        deleteButton = (Button) findViewById(R.id.delete);
        sendMessageButton = (Button) findViewById(R.id.sendMessage);


    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayIngredients.class);
        final EditText ingredientToSelect = (EditText) findViewById(R.id.numToSelect);
        switch (view.getId()) {
            case R.id.sendMessage:

                String selectingIngredient = ingredientToSelect.getText().toString();
                if (selectingIngredient.equals("")) {
                } else {

                    intent.putExtra(EXTRA_MESSAGE, selectingIngredient);
                    startActivity(intent);
                }
        }
    }
    public void recipes(View view) {
        Intent intent = new Intent(this, RecipeDetail.class);
        switch (view.getId()) {
            case R.id.addRecipe:
                //intent.putExtra(EXTRA_MESSAGE, "lolz");
                startActivity(intent);
        }
    }
    public void recipeView(View view) {
        Intent intent = new Intent(this, RecipeList.class);
        switch (view.getId()) {
            case R.id.viewRecipe:
                startActivity(intent);
        }
    }
    public void pushIt(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        final EditText ingredientName = (EditText) findViewById(R.id.name);
        final EditText ingredientCapacity = (EditText) findViewById(R.id.capacity);
        final EditText ingredientType = (EditText) findViewById(R.id.type);
        final EditText ingredientExpiration = (EditText) findViewById(R.id.expiration);
        final EditText ingredientDelete = (EditText) findViewById(R.id.deleteInput);
        DBHandlerNew dbIngredient = new DBHandlerNew(this);
        Ingredient negotiateIngredient = new Ingredient();
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRStUVWXYZ";

        switch (view.getId()) {
            case R.id.view:
                String allIngredients = "";
                ArrayList<Ingredient> ingredients = dbIngredient.getAllIngredients();
                for (Ingredient ingredient : ingredients) {
                    allIngredients = allIngredients + "Id: " + ingredient.getIngredientId() + ", Name: "
                            + ingredient.getIngredientName() + ", Capacity: " + ingredient.getIngredientCapacity() +
                            ", Type: " + ingredient.getIngredientTypeString() + ", Expiration: " +
                            ingredient.getIngredientExpiration() + "\n";
                }
                output.setText(allIngredients);
                break;
            case R.id.viewRecipes:
                String allRecipes = "";
                ArrayList<Recipe> recipes = dbIngredient.getRecipes();
                for (Recipe recipe : recipes) {
                    recipe.setIngredientIds(dbIngredient.getRecipeIngredientIds(recipe.getRecipeId()));
                    allRecipes = allRecipes + "Id: " + recipe.getRecipeId() + ", Name: "
                            + recipe.getRecipeName() + ", Last Made: " + recipe.getRecipeLastMade() + ", Ingredients: " + recipe.getIngredientIds().toString() +
                            ", Servings Made: " + recipe.getRecipeServingsMade() +  "\n";
                }
                output.setText(allRecipes);
                break;
            case R.id.commit:

                String nameOfIngredient = ingredientName.getText().toString();
                int capacityOfIngredient = Integer.parseInt(ingredientCapacity.getText().toString());
                int typeOfIngredient = Integer.parseInt(ingredientType.getText().toString());
                String expirationOfIngredient = ingredientExpiration.getText().toString();

                if (nameOfIngredient.equals("") || typeOfIngredient < 0 || typeOfIngredient > 3) {
                    output.setText("Input is required, type must be a number between 0 and 3");
                } else {
                    dbIngredient.addIngredient(new Ingredient(0, nameOfIngredient, capacityOfIngredient, typeOfIngredient, expirationOfIngredient));
                }
                break;
            case R.id.delete:
                String deletingIngredient = ingredientDelete.getText().toString();
                if (deletingIngredient.equals("") || deletingIngredient.contains(letters)) {
                    output.setText("You must input a number");
                } else {

                    int i = Integer.parseInt(ingredientDelete.getText().toString());
                    dbIngredient.deleteIngredient(dbIngredient.getIngredient(i));
                    //dbIngredient.dropDB();
                    //dbIngredient.onCreate(dbIngredient.getWritableDatabase());
                    break;

                }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
