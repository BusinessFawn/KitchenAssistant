package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RecipeDetail extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public final static String EXTRA_MESSAGE = "com.youknowit.partytime.kitchenassistant.MESSAGE";
    Recipe currentRecipe = new Recipe();
    Button commitRecipe;
    EditText recipeName;
    EditText recipeServingsMade;
    private String datePickedString;
    private Calendar recipeCalendar;
    private TextView recipeDateDisplay;
    private int year, month, day;
    private Ingredient ingredientToAdd = new Ingredient();
    private DBHandlerNew dbHandlerNew = new DBHandlerNew(this);
    private ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();
    private ArrayList<Integer> ingredientsCapacityUsed = new ArrayList<>();
    private ArrayList<Integer> ingredientsIds = new ArrayList<>();
    private EditText editIngredientSearch;
    private Button buttonIngredientSearch;
    Integer commitRecipeServings = 0;
    String commitRecipeName;
    //TODO add ingredient selection/paring
    //TODO make layout more functional
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //inflate some date EditText objects
        recipeName = (EditText) findViewById(R.id.recipeName);
        recipeServingsMade = (EditText) findViewById(R.id.recipeServingsMade);
        editIngredientSearch = (EditText) findViewById(R.id.et_ingredient_search);


        //inflate datepicker, calendar, and date selected textview

        recipeDateDisplay = (TextView) findViewById(R.id.datePickingDisplay);
        recipeCalendar = Calendar.getInstance();
        year = recipeCalendar.get(Calendar.YEAR);
        month = recipeCalendar.get(Calendar.MONTH);
        day = recipeCalendar.get(Calendar.DAY_OF_MONTH);
        if (currentRecipe.getRecipeLastMade() == null || currentRecipe.getRecipeLastMade().equals("")) {
            showDate(year, month + 1, day);
        }else {
            showDate();
        }

        //inflate buttons
        commitRecipe = (Button) findViewById(R.id.recipeCommit);
        buttonIngredientSearch = (Button) findViewById(R.id.b_ingredient_search);

        //inflate list adapter
        ListView itemList = (ListView) findViewById(R.id.recipeIngredientList);
        itemList.setOnItemClickListener(this);



        ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ingredientsAdded);
        itemList.setAdapter(arrayAdapter);


        //receive intent from ingredient list view
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            int id = intent.getIntExtra("id", 0);
            if (id != 0) {
                if (intent.getIntegerArrayListExtra("ingredientIdsPassBack") == null) {
                    ingredientsIds.add(id);
                    for (int i = 0; i < ingredientsIds.size(); i++) {
                        ingredientToAdd = dbHandlerNew.getIngredient(ingredientsIds.get(i));
                        ingredientsAdded.add(ingredientToAdd);
                    }
                } else {
                    ingredientsIds = intent.getIntegerArrayListExtra("ingredientIdsPassBack");
                    ingredientsIds.add(id);
                    for (int i = 0; i < ingredientsIds.size(); i++) {
                        ingredientToAdd = dbHandlerNew.getIngredient(ingredientsIds.get(i));
                        ingredientsAdded.add(ingredientToAdd);
                    }
                }
            }
            currentRecipe = intent.getParcelableExtra("recipePassBack");
            recipeName.setText(currentRecipe.getRecipeName());
            String servingsMadeString = String.valueOf(currentRecipe.getRecipeServingsMade());
            recipeServingsMade.setText(servingsMadeString);
            recipeDateDisplay.setText(currentRecipe.getRecipeLastMade());
            ListViewSizer.setListViewHeightBasedOnChildren(itemList);
        }



    }





    @SuppressWarnings("deprecation")
    public void datePickerListener(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(),"ca",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
            setIngredientDate(arg1, arg2+1, arg3);
        }
    };
    private void showDate(int year, int month, int day) {
        recipeDateDisplay.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    private void showDate() {
        recipeDateDisplay.setText(currentRecipe.getRecipeLastMade());
    }
    private void setIngredientDate(int year, int month, int day) {
        datePickedString = year + "-" + month + "-" + day;
    }

    //Button onClick Listener
    public void recipeButtonListener(View view) {

        //inflate EditText
        recipeName = (EditText) findViewById(R.id.recipeName);
        recipeServingsMade = (EditText) findViewById(R.id.recipeServingsMade);
        Intent intent = new Intent(this, IngredientListRecipeAdd.class);
        commitRecipeName = recipeName.getText().toString();
        switch (view.getId()) {
            case R.id.b_ingredient_search:
                String selectingIngredient = editIngredientSearch.getText().toString();
                if (selectingIngredient.equals("")) {
                    selectingIngredient = " ";
                    intent.putExtra(EXTRA_MESSAGE, selectingIngredient);
                } else {
                    intent.putExtra(EXTRA_MESSAGE, selectingIngredient);
                }

                intent.putExtra(EXTRA_MESSAGE, selectingIngredient);

                if (commitRecipeName == null || commitRecipeName.equals("")) {
                    currentRecipe.setRecipeName("");
                } else {
                    currentRecipe.setRecipeName(commitRecipeName);
                }
                if (recipeServingsMade.getText().toString().equals("")) {
                    currentRecipe.setRecipeServingsMade(0);
                } else {
                    commitRecipeServings = Integer.parseInt(recipeServingsMade.getText().toString());
                    currentRecipe.setRecipeServingsMade(commitRecipeServings);
                }
                currentRecipe.setRecipeLastMade(datePickedString);

                if (ingredientsAdded.size() > 0) {
                    for (int i = 0; i < ingredientsAdded.size(); i++)
                    currentRecipe.setIngredientIds(ingredientsAdded.get(i));
                }

                if (ingredientsCapacityUsed.size() > 0) {
                    for (int i = 0; i < ingredientsCapacityUsed.size(); i++)
                        currentRecipe.setIngredientInventoryUsed(ingredientsCapacityUsed.get(i));
                }

                //TODO figure out how to pass a recipe to another activity
                intent.putExtra("recipePass", currentRecipe);
                intent.putIntegerArrayListExtra("ingredientList", ingredientsIds);

                startActivity(intent);
                finish();
                break;
            case R.id.recipeCommit:
                //TODO commit recipe


                try
                {


                    currentRecipe.setRecipeName(commitRecipeName);
                    currentRecipe.setRecipeServingsMade(commitRecipeServings);
                    currentRecipe.setRecipeLastMade(datePickedString);

                    dbHandlerNew.addRecipe(currentRecipe);
                    dbHandlerNew.addIngredientToRecipe(currentRecipe,
                            currentRecipe.getIngredientIds(),
                            currentRecipe.getIngredientInventoryUsed());
                }catch(ArrayIndexOutOfBoundsException e1)
                {
                    System.out.println("Something went wrong!");
                }


                break;

        }
    }

    public void onItemClick(AdapterView<?> l, View v, int id, long position) {
        Intent intent = new Intent();
        intent.setClass(this, IngredientDetail.class);
        ingredientToAdd = ingredientsAdded.get(id);
        int passingID = ingredientToAdd.getIngredientId();
        intent.putExtra("id", passingID);
        startActivity(intent);
    }
}
