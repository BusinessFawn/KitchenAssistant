package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IngredientDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Ingredient thisIngredient = new Ingredient();
    private EditText currentAmount;
    Integer capacity;
    private Spinner measurementSpinner;
    int updatedIngredientType;
    boolean firstSelectionMade = false;
    private String currentAmountString;
    DBHandlerNew dbHandlerNew = new DBHandlerNew(this);
    Button decrementInventory;
    Button incrementInventory;
    Button commitMeasurement;
    Button commitCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inflate and fill spinner
        measurementSpinner = (Spinner) findViewById(R.id.ingredientSpinner);
        measurementSpinner.setOnItemSelectedListener(this);
        List<String> measurementList = new ArrayList<>();
        //TODO Add additional selections for capacity types

        measurementList.add("Percent");
        measurementList.add("Ounces");
        measurementList.add("Grams");
        measurementList.add("Liters");
        measurementList.add("Unmeasured");

        //pass list of capacity types to spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, measurementList
        );
        //set spinner layout type
        dataAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        //assign the spinner to the adapter
        measurementSpinner.setAdapter(dataAdapter);

        //receive intent from ingredient list view
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        //set the ingredient for this screen
        thisIngredient = dbHandlerNew.getIngredient(id);
        //set title
        TextView ingredientTitle = (TextView) findViewById(R.id.ingredientTitle);
        ingredientTitle.setText(thisIngredient.getIngredientName());

        //set current quantity of ingredient on screen
        currentAmount = (EditText) findViewById(R.id.currentAmount);
        capacity = thisIngredient.getIngredientCapacity();
        currentAmountString = capacity.toString();
        currentAmount.setText(currentAmountString);

        //set selection for spinner
        updatedIngredientType = thisIngredient.getIngredientType();

        //add listner for spinner selection
        addListenerOnSpinnerItemSelection();

        //add decrement/increment/commit buttons
        decrementInventory = (Button) findViewById(R.id.decrementIngredient);
        incrementInventory = (Button) findViewById(R.id.incrementIngredient);
        commitMeasurement = (Button) findViewById(R.id.commitIngredientChange);
        commitCancel = (Button) findViewById(R.id.commitIngredientCancel);
    }

    public void addListenerOnSpinnerItemSelection() {
        measurementSpinner.setOnItemSelectedListener(this);
    }


    public void onItemSelected(AdapterView<?> spinner, View view, int position,
                               long id) {
        int currentType = thisIngredient.getIngredientType();
        if (!firstSelectionMade)
        {
            measurementSpinner.setSelection(updatedIngredientType);
            firstSelectionMade = true;

        }
        else {
            measurementSpinner.setSelection(position);
            updatedIngredientType = position;

        }
/*        Toast.makeText(spinner.getContext(),
                "Selected: " + spinner.getItemAtPosition(updatedIngredientType).toString(),
                Toast.LENGTH_LONG).show();

        System.out.println(updatedIngredientType);*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    /*public void addListenerOnButton() {
        measurementSpinner = (Spinner) findViewById(R.id.ingredientSpinner);
        commitMeasurement = (Button) findViewById(R.id.commitIngredientChange);
        commitMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IngredientDetail.this,
                        "Selected: " + String.valueOf(measurementSpinner.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
            }
        });
    }*/

    public void ingredientDetailEdit(View view) {
        switch (view.getId()) {
            case R.id.decrementIngredient:
                capacity--;
                currentAmountString = capacity.toString();
                currentAmount.setText(currentAmountString);
                break;
            case R.id.incrementIngredient:
                capacity++;
                currentAmountString = capacity.toString();
                currentAmount.setText(currentAmountString);
                break;
            case R.id.commitIngredientChange:
                int changedAmount = Integer.parseInt(currentAmount.getText().toString());
                thisIngredient.setIngredientCapacity(changedAmount);
                thisIngredient.setIngredientType(updatedIngredientType);
                dbHandlerNew.updateIngredient(thisIngredient);
                break;
            case R.id.commitIngredientCancel:
                currentAmount.setText(Integer.toString(thisIngredient.getIngredientCapacity()));
                measurementSpinner.setSelection(thisIngredient.getIngredientType());
                break;


        }
    }
}

