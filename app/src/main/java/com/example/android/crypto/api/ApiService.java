package com.example.android.crypto.api;

import com.example.android.crypto.model.Coin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pc on 1/21/2018.
 */

public interface ApiService {
    @GET("ticker/")
    Call<ArrayList<Coin>> getCoins (@Query("limit") int limit, @Query("convert") String convert);


}
