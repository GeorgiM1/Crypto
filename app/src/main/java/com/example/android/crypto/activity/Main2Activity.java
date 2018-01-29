package com.example.android.crypto.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.crypto.R;
import com.example.android.crypto.adapter.ItemAdapter;
import com.example.android.crypto.api.RestApi;
import com.example.android.crypto.model.Coin;
import com.example.android.crypto.model.FavouriteCoins;
import com.example.android.crypto.other.OnRowClickListener;
import com.example.android.crypto.other.PreferencesManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    ItemAdapter itemAdapter;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    RestApi api;
    ArrayList<Coin> coins;
    public FavouriteCoins favouriteCoins ;
    int limit ;
     String fiat ;
    private BroadcastReceiver mReciver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        limit = PreferencesManager.getLimit(this);
        fiat = PreferencesManager.getFiat(this);


        favouriteCoins  = PreferencesManager.getCoins(this);






        api = new RestApi(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                refreshRecycleView();

            }
        });



        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Coin>> call = api.getCoins(limit,fiat);
                call.enqueue(new Callback<ArrayList<Coin>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coin>> call, Response<ArrayList<Coin>> response) {
                        if (response.isSuccessful()) {
                            coins = response.body();
                            itemAdapter = new ItemAdapter(Main2Activity.this, coins, new OnRowClickListener() {
                                @Override
                                public void onRowClick(Coin coin, int position) {


                                }
                            });
                            recyclerView.setAdapter(itemAdapter);
                        }else
                        {Toast.makeText(Main2Activity.this, "Response not successful!", Toast.LENGTH_LONG).show();}

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coin>> call, Throwable t) {
                        Toast.makeText(Main2Activity.this, "Something went wrong!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }
    public void  refreshRecycleView (){
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Coin>> call = api.getCoins(limit,fiat);
                call.enqueue(new Callback<ArrayList<Coin>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coin>> call, Response<ArrayList<Coin>> response) {
                        coins = response.body();
                        itemAdapter = new ItemAdapter(Main2Activity.this, coins, new OnRowClickListener() {
                            @Override
                            public void onRowClick(Coin coin, int position) {


                            }
                        });
                        recyclerView.setAdapter(itemAdapter);

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coin>> call, Throwable t) {

                    }
                });
            }
        });

    }


    @Override
    public void onBackPressed() {
        PreferencesManager.addCoins(favouriteCoins, this);
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.FIREBASENOTIFICATION");

        mReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    String msg_for_me = intent.getStringExtra("notification");
                    String notificationBody = intent.getStringExtra("notificationBody");
                    if (msg_for_me != null && !msg_for_me.equals(""))
                        handleFirebaseNotification(msg_for_me, notificationBody);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        registerReceiver(mReciver, intentFilter);
    }

    private void handleFirebaseNotification(String msg_for_me, String notificationBody) {
        Toast.makeText(this, msg_for_me + "-//-" + notificationBody, Toast.LENGTH_LONG).show();
    }
}
