package com.example.android.crypto.model;

import java.util.ArrayList;

/**
 * Created by pc on 1/21/2018.
 */

public class FavouriteCoins {
    public ArrayList<Coin> favouriteCoins = new ArrayList<Coin>();

    public boolean hasCoin(String id) {
        for (int i = 0; i < favouriteCoins.size(); i++) {
            if (id.equals(favouriteCoins.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    public int getPosition(String id) {

        for (int i = 0; i < favouriteCoins.size(); i++) {
            if (id.equals(favouriteCoins.get(i).getId())) {
                return i;
            }
        }
        return 0;
    }
}
