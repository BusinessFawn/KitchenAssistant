package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IngredientDetail extends AppCompatActivity {

    private Ingredient thisIngredient = new Ingredient();
    Button commitDecrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DBHandlerNew dbHandlerNew = new DBHandlerNew(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        TextView ingredientTitle = (TextView) findViewById(R.id.ingredientTitle);
        thisIngredient = dbHandlerNew.getIngredient(id);
        ingredientTitle.setText(thisIngredient.getIngredientName());

    }

}
