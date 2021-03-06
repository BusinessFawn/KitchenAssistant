package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;


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
    private Button decrementInventory;
    private Button incrementInventory;
    Integer commitRecipeServings = 0;
    String commitRecipeName;
    boolean isItNew = false;
    boolean addIngredients = false;
    private static final int REQUEST_CODE = 10;
    private ListView itemList;
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
        } else {
            showDate();
        }

        //inflate buttons
        commitRecipe = (Button) findViewById(R.id.recipeCommit);
        buttonIngredientSearch = (Button) findViewById(R.id.b_ingredient_search);

        //inflate list adapter
        itemList = (ListView) findViewById(R.id.recipeIngredientList);
        itemList.setOnItemClickListener(this);


        RecipeListAdapter<Ingredient> arrayAdapter = new RecipeListAdapter<>(
                this,
                ingredientsAdded);
        itemList.setAdapter(arrayAdapter);


        //receive intent from ingredient list view
        Intent intent = getIntent();

        if (getIntent().getExtras() != null) {
            isItNew = intent.getBooleanExtra("isItNew", false);
            if (intent.getParcelableExtra("recipePassBack") != null) {
                currentRecipe = intent.getParcelableExtra("recipePassBack");
                ingredientsIds = dbHandlerNew.getRecipeIngredientIds(currentRecipe.getRecipeId());
                for (int i = 0; i < ingredientsIds.size(); i++) {
                    ingredientToAdd = dbHandlerNew.getIngredient(ingredientsIds.get(i));
                    ingredientsAdded.add(ingredientToAdd);
                }


                recipeName.setText(currentRecipe.getRecipeName());
                String servingsMadeString = String.valueOf(currentRecipe.getRecipeServingsMade());
                recipeServingsMade.setText(servingsMadeString);
                recipeDateDisplay.setText(currentRecipe.getRecipeLastMade());

                //showDate();

                ListViewSizer.setListViewHeightBasedOnChildren(itemList);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent receivedIntent) {
        ingredientsIds = receivedIntent.getIntegerArrayListExtra("ingredientIdsPassBack");
        ingredientsIds.add(receivedIntent.getIntExtra("id",-1));

        ingredientToAdd = dbHandlerNew.getIngredient(ingredientsIds.get(receivedIntent.getIntExtra("id",-1)));
        ingredientsAdded.add(ingredientToAdd);
        isItNew = receivedIntent.getBooleanExtra("isItNew", false);
        currentRecipe = receivedIntent.getParcelableExtra("recipePassBack");
        recipeName.setText(currentRecipe.getRecipeName());
        String servingsMadeString = String.valueOf(currentRecipe.getRecipeServingsMade());
        recipeServingsMade.setText(servingsMadeString);
        recipeDateDisplay.setText(currentRecipe.getRecipeLastMade());

        //showDate();
        ListViewSizer.setListViewHeightBasedOnChildren(itemList);
        System.out.println("it was onAct");


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
            setRecipeDate(arg1, arg2+1, arg3);
        }
    };
    private void showDate(int year, int month, int day) {
        recipeDateDisplay.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        setRecipeDate(year,month,day);
    }
    private void showDate() {
        recipeDateDisplay.setText(currentRecipe.getRecipeLastMade());
    }
    private void setRecipeDate(int year, int month, int day) {
        datePickedString = year + "-" + month + "-" + day;
//        currentRecipe.setRecipeLastMade(datePickedString);
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

                intent.putExtra("recipePass", currentRecipe);
                intent.putIntegerArrayListExtra("ingredientList", ingredientsIds);
                intent.putExtra("isItNew",isItNew);
                startActivityForResult(intent,1);
                //finish();
                break;
            case R.id.recipeCommit:
                //TODO commit recipe
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

                if (ingredientsIds.size() > 0) {
                    currentRecipe.setIngredientIds(ingredientsIds);
                    addIngredients = true;
                }

                if (ingredientsCapacityUsed.size() > 0) {
                    for (int i = 0; i < ingredientsCapacityUsed.size(); i++)
                        currentRecipe.setIngredientInventoryUsed(ingredientsCapacityUsed.get(i));
                } else {
                    currentRecipe.setIngredientInventoryUsed(ingredientsIds);
                }
                if (isItNew) {

                    dbHandlerNew.addRecipe(currentRecipe);
                    currentRecipe.setRecipeId(dbHandlerNew.getLastRecipeId());
                    if(addIngredients) {
                        dbHandlerNew.addIngredientToRecipe(currentRecipe,
                                currentRecipe.getIngredientIds(),
                                currentRecipe.getIngredientInventoryUsed());
                    }
                } else {
                    dbHandlerNew.updateRecipe(currentRecipe);
                    if(addIngredients){
                        dbHandlerNew.deleteIngredientsUsed(currentRecipe.getRecipeId());
                        dbHandlerNew.addIngredientToRecipe(currentRecipe,
                                currentRecipe.getIngredientIds(),
                                currentRecipe.getIngredientInventoryUsed());
                    }

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
