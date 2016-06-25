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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayIngredients extends AppCompatActivity implements AdapterView.OnItemClickListener
 {

     Ingredient separateIngredients = new Ingredient();
     ArrayList<Ingredient> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBHandlerNew intentHandler = new DBHandlerNew(this);
        ListView itemList = (ListView) findViewById(R.id.ingredientList);
        itemList.setOnItemClickListener(this);


        Intent intent = getIntent();
        String s = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        ArrayList<Ingredient> tempIngredients = new ArrayList<>(intentHandler.getLikeIngredientName(s));

        for (int i = 0; i < tempIngredients.size(); i++) {
            ingredients.add(i,tempIngredients.get(i));
        }

        ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ingredients);
        itemList.setAdapter(arrayAdapter);
    }
    public void onItemClick(AdapterView<?> l, View v, int id, long position) {
     Intent intent = new Intent();
        intent.setClass(this, IngredientDetail.class);
        separateIngredients = ingredients.get(id);
        int passingID = separateIngredients.getIngredientId();
        intent.putExtra("id", passingID);
        startActivity(intent);
    }

}
