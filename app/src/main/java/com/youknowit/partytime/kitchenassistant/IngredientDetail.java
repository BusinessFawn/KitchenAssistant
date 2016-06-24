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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IngredientDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Ingredient thisIngredient = new Ingredient();
    private Spinner measurementSpinner;
    int updatedIngredientType;
    Button commitMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        measurementSpinner = (Spinner) findViewById(R.id.ingredientSpinner);
        measurementSpinner.setOnItemSelectedListener(this);
        List<String> measurementList = new ArrayList<>();
        /*TODO
        Add additional selections for capacity types
         */
        measurementList.add("Percent");
        measurementList.add("Ounces");
        measurementList.add("Grams");
        measurementList.add("Liters");
        measurementList.add("Unmeasured");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, measurementList
        );

        dataAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        measurementSpinner.setAdapter(dataAdapter);

        DBHandlerNew dbHandlerNew = new DBHandlerNew(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        TextView ingredientTitle = (TextView) findViewById(R.id.ingredientTitle);
        thisIngredient = dbHandlerNew.getIngredient(id);
        ingredientTitle.setText(thisIngredient.getIngredientName());

        addListenerOnSpinnerItemSelection();
        //addListenerOnButton();
    }

    public void addListenerOnSpinnerItemSelection() {
        measurementSpinner.setOnItemSelectedListener(this);
    }


    public void onItemSelected(AdapterView<?> spinner, View view, int position,
                               long id) {
        int currentType = thisIngredient.getIngredientType();
        System.out.println(currentType);
        updatedIngredientType = position;
        Toast.makeText(spinner.getContext(),
                "Selected: " + spinner.getItemAtPosition(currentType).toString(),
                Toast.LENGTH_LONG).show();
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
}

