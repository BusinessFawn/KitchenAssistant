package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientDetail extends AppCompatActivity {

    private Ingredient thisIngredient = new Ingredient();
    private Spinner measurementSpinner;
    Button commitMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        measurementSpinner = (Spinner) findViewById(R.id.ingredientSpinner);
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
                this, android.R.layout.simple_spinner_item,measurementList
        );

        dataAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        /*addListenerOnSpinnerItemSelection();
        addListenOnButton();

        public void addListenerOnSpinnerItemSelection() {
            //measurementSpinner.setOnItemSelectedListener(new);
    }*/




        DBHandlerNew dbHandlerNew = new DBHandlerNew(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        TextView ingredientTitle = (TextView) findViewById(R.id.ingredientTitle);
        thisIngredient = dbHandlerNew.getIngredient(id);
        ingredientTitle.setText(thisIngredient.getIngredientName());

    }

}
