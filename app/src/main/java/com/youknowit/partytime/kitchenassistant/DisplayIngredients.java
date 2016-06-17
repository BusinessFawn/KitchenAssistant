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

    private ListView itemList;
     private String[] ingredientNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DBHandlerNew intentHandler = new DBHandlerNew(this);
        //TextView textView = (TextView) findViewById(R.id.singleItem);
        String allIngredients = "";
        itemList = (ListView) findViewById(R.id.ingredientList);
        //itemList.setOnItemClickListener(this);


        Intent intent = getIntent();
        //int i = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));
        String i = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        List<Ingredient> ingredients = new ArrayList<>(intentHandler.getLikeIngredientName(i));
        for (int j = 0; j < ingredients.size(); j++) {
        }
        //for (Ingredient ingredient : ingredients) {
        //    allIngredients = allIngredients + "Id: " + ingredient.getIngredientId() + " ,Name: " + ingredient.getIngredientName() + " ,Capacity: " + ingredient.getIngredientCapacity() + "\n";
        //}
        //textView.setText(allIngredients);

        ArrayAdapter<Ingredient> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.activity_list_item,
                ingredients);
        itemList.setAdapter(arrayAdapter);
        //Ingredient ingredient = new Ingredient();
        //ingredient = intentHandler.getIngredient(i);
        //textView.setText("Name: " + ingredient.getIngredientName() + " capacity: " + ingredient.getIngredientCapacity());
    }

    //public void onItemClick(AdapterView<?> l, View v, int id, long other) {
     //Intent intent = new Intent();
     //   intent.setClass(this, IngredientDetail.class);
     //   intent.putExtra("id", id);
     //   startActivity(intent);
    //}

}
