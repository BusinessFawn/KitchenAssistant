package com.youknowit.partytime.kitchenassistant;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pyrobones07 on 5/23/16.
 */
public class Ingredient implements Parcelable {
    private int ingredientId;
    private String ingredientName;
    private int ingredientCapacity;
    private int ingredientType;
    private String ingredientTypeString;
    private String ingredientExpiration;
    public Ingredient()
    {
    }
    public Ingredient(int ingredientId,String ingredientName, int ingredientCapacity, int ingredientType, String ingredientExpiration)
    {
        this.ingredientId=ingredientId;
        this.ingredientName=ingredientName;
        this.ingredientCapacity=ingredientCapacity;
        this.ingredientType=ingredientType;
        setIngredientTypeString(ingredientType);
        this.ingredientExpiration = ingredientExpiration;
    }
    @Override
    public String toString() {
        return ingredientName + " with " + ingredientCapacity + " " + ingredientTypeString;
    }
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientCapacity(int ingredientCapacity) {
        this.ingredientCapacity = ingredientCapacity;
    }
    public void setIngredientType(int ingredientType) {
        this.ingredientType=ingredientType;
        setIngredientTypeString(ingredientType);

    }

    public void setIngredientTypeString(int ingredientType) {
        if (ingredientType == 0) {
            ingredientTypeString = "Percent";
        }
        else if (ingredientType == 1) {
            ingredientTypeString = "Ounces";
        }
        else if (ingredientType == 2) {
            ingredientTypeString = "Grams";
        }
        else if (ingredientType == 3) {
            ingredientTypeString = "Liters";
        }
        else {
            ingredientTypeString = "Other";
        }
    }

    public void setIngredientExpiration(String ingredientExpiration) {
        this.ingredientExpiration = ingredientExpiration;
    }

    public int getIngredientId() {
        return ingredientId;
    }
    public int getIngredientCapacity() {
        return ingredientCapacity;
    }
    public String getIngredientName() {
        return ingredientName;
    }
    public int getIngredientType() {
        return ingredientType;
    }
    public String getIngredientTypeString() {
        return ingredientTypeString;
    }
    public String getIngredientExpiration() {
        return ingredientExpiration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ingredientId);
        dest.writeString(this.ingredientName);
        dest.writeInt(this.ingredientCapacity);
        dest.writeInt(this.ingredientType);
        dest.writeString(this.ingredientTypeString);
        dest.writeString(this.ingredientExpiration);
    }

    protected Ingredient(Parcel in) {
        this.ingredientId = in.readInt();
        this.ingredientName = in.readString();
        this.ingredientCapacity = in.readInt();
        this.ingredientType = in.readInt();
        this.ingredientTypeString = in.readString();
        this.ingredientExpiration = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
