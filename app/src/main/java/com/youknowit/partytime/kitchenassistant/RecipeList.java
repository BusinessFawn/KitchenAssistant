package com.youknowit.partytime.kitchenassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    Recipe singleRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        DBHandlerNew intentHandler = new DBHandlerNew(this);
        recipeArrayList = intentHandler.getRecipes();
        ListView itemList = (ListView) findViewById(R.id.lv_recipe_list);
        itemList.setOnItemClickListener(this);

        ArrayAdapter<Recipe> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recipeArrayList);
        itemList.setAdapter(arrayAdapter);
    }
    public void onItemClick(AdapterView<?> l, View v, int id, long position) {
        Intent intent = new Intent();
        intent.setClass(this, RecipeDetail.class);
        singleRecipe = recipeArrayList.get(id);
        intent.putExtra("recipePassBack",singleRecipe);
        startActivity(intent);
    }
}
