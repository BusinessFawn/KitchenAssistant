package com.youknowit.partytime.kitchenassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class RecipeDetail extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.youknowit.partytime.kitchenassistant.MESSAGE";
    RecipeDBHandler recipeDBHandler = new RecipeDBHandler(this);
    Recipe currentRecipe = new Recipe();
    Button commitRecipe;
    EditText recipeName;
    EditText recipeLastMade;
    EditText recipeServingsMade;
    private String datePickedString;
    private DatePicker ingredientDatePicker; //is this needed?
    private Calendar recipeCalendar;
    private TextView recipeDateDisplay;
    private int year, month, day;
    //TODO add ingredient selection/paring
    //TODO make layout more functional
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //inflate datepicker, calendar, and date selected textview

        recipeDateDisplay = (TextView) findViewById(R.id.datePickingDisplay);
        recipeCalendar = Calendar.getInstance();
        year = recipeCalendar.get(Calendar.YEAR);
        month = recipeCalendar.get(Calendar.MONTH);
        day = recipeCalendar.get(Calendar.DAY_OF_MONTH);
        if (currentRecipe.getRecipeLastMade().equals("") || currentRecipe.getRecipeLastMade() == null) {
            showDate(year, month + 1, day);
        }else {
            showDate();
        }

    }

    //inflate datepicker, calendar, and date selected textview


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
        currentRecipe.setRecipeLastMade(datePickedString);
    }
}
