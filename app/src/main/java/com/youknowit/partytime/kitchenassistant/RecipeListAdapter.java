package com.youknowit.partytime.kitchenassistant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by johnkonderla on 10/30/16.
 */

public class RecipeListAdapter<I> extends ArrayAdapter<Ingredient> {
    public RecipeListAdapter(Activity context, ArrayList<Ingredient> ingredientList) {
        super(context, 0, ingredientList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.business_fawn_ingredient_list, parent, false
            );
        }
        Ingredient currentIngredient = getItem(position);
        TextView ingredientName = (TextView) listItemView.findViewById(R.id.list_item_ingredient_name);
        ingredientName.setText(currentIngredient.getIngredientName());
        TextView ingredientCapacity = (TextView) listItemView.findViewById(R.id.list_item_ingredient_type);
        ingredientCapacity.setText(Integer.toString(currentIngredient.getIngredientType()));
        EditText ingredientUsed = (EditText) listItemView.findViewById(R.id.list_item_ingredient_used);
        ingredientUsed.setText(Integer.toString(0));

        return listItemView;
    }
}
