package edu.usc.cs404.catchup;

/**
 * Created by eshitamathur on 5/4/17.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class FoodBankSingleton {

    private List<FoodBank> mFoodBanks;

    private static FoodBankSingleton sFoodBanks;
    private Context mAppContext;

    //TODO do not instantiate list or load data
    private FoodBankSingleton(Context appContext) {
        mAppContext = appContext;
        mFoodBanks = new ArrayList<FoodBank>();

    }

    public static FoodBankSingleton get(Context c) {
        if (sFoodBanks == null) {
            sFoodBanks = new FoodBankSingleton(c.getApplicationContext());
        }
        return sFoodBanks;
    }

    //TODO Read all items --change to return List
    public List<FoodBank> getFoodBanks() {
        return mFoodBanks;
    }

    //TODO Read
    public FoodBank getFoodBank(int index) {
        return mFoodBanks.get(index);
    }

    //TODO Create
    public void addFoodBank(FoodBank cs) {
        mFoodBanks.add(cs);
    }

    //TODO Delete
    public void removeFoodBank(int index) {
        if (index >= 0 && index < mFoodBanks.size())
            mFoodBanks.remove(index);
    }

    //TODO Update
    public void updateFoodBank(int index, FoodBank cs) {
        if (index >= 0 && index < mFoodBanks.size())
            mFoodBanks.set(index, cs);
    }


}
