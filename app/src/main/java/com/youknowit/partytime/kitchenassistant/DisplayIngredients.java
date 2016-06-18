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

public class DisplayIngredients extends AppCompatActivity //implements AdapterView.OnItemClickListener
 {

     Ingredient separateIngredients = new Ingredient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBHandlerNew intentHandler = new DBHandlerNew(this);
        //TextView textView = (TextView) findViewById(R.id.singleItem);
        String allIngredients = "";
        ListView itemList = (ListView) findViewById(R.id.ingredientList);
        //itemList.setOnItemClickListener();


        Intent intent = getIntent();
        String i = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        ArrayList<Ingredient> ingredients = new ArrayList<>(intentHandler.getLikeIngredientName(i));

        ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<Ingredient>(
                this,
                android.R.layout.simple_list_item_1,
                ingredients);
        itemList.setAdapter(arrayAdapter);
    }

    public void onItemClick(AdapterView<?> l, View v, int id, long other) {
     Intent intent = new Intent();
        intent.setClass(this, IngredientDetail.class);
        id = separateIngredients.getIngredientId();
        System.out.println(id);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
