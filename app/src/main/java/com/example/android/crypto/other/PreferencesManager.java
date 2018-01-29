package com.example.android.crypto.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.crypto.model.FavouriteCoins;
import com.google.gson.Gson;

/**
 * Created by pc on 1/21/2018.
 */

public class PreferencesManager {

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences("MySharedPreferencesFile", Activity.MODE_PRIVATE);
    }
    public static void addCoins(FavouriteCoins coin, Context c){
        Gson gson = new Gson();
        String mapStrnig = gson.toJson(coin);
        getPreferences(c).edit().putString("coin", mapStrnig).apply();
    }
    public static FavouriteCoins getCoins(Context context){
        return  new Gson().fromJson(getPreferences(context).getString("coin", ""), FavouriteCoins.class);
    }

    public static String  getFiat (Context fiat){
        return getPreferences(fiat).getString("fiat", "");
    }
    public static void  setFiat (String fiat,Context c){
        getPreferences(c).edit().putString("fiat", fiat).apply();

    }
    public static void setLimit (int limit, Context c){
        getPreferences(c).edit().putInt("limit", limit).apply();
    }
    public static int getLimit(Context limit){
        return getPreferences(limit).getInt("limit", 0);
    }

}
