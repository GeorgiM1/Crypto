package com.example.android.crypto.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.crypto.R;
import com.example.android.crypto.adapter.ItemAdapter;
import com.example.android.crypto.api.RestApi;
import com.example.android.crypto.model.Coin;
import com.example.android.crypto.model.FavouriteCoins;
import com.example.android.crypto.other.OnRowClickListener;
import com.example.android.crypto.other.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    FavouriteCoins favouriteCoins;
    ItemAdapter itemAdapter;
    @BindView(R.id.favourites_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_favourites)
    SwipeRefreshLayout swipeRefreshLayout;
    RestApi api;
    private BroadcastReceiver mReciver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                startActivityForResult(intent, 1000);
            }
        });



        favouriteCoins = PreferencesManager.getCoins(this);
        itemAdapter = new ItemAdapter(this, favouriteCoins.favouriteCoins, new OnRowClickListener() {
            @Override
            public void onRowClick(final Coin coin, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to remove this coin from your favourites?");
                builder.setTitle("Delete coin");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        favouriteCoins.favouriteCoins.remove(position);
                        PreferencesManager.addCoins(favouriteCoins, MainActivity.this);
                      itemAdapter.notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();



            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK){
           favouriteCoins = PreferencesManager.getCoins(this);

           itemAdapter = new ItemAdapter(this, favouriteCoins.favouriteCoins, new OnRowClickListener() {
               @Override
               public void onRowClick(final Coin coin, final int position) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                   builder.setMessage("Do you want to remove this coin from your favourites?");
                   builder.setTitle("Delete coin");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           favouriteCoins.favouriteCoins.remove(position);
                           PreferencesManager.addCoins(favouriteCoins, MainActivity.this);
                           itemAdapter.notifyDataSetChanged();



                       }
                   });
                   builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   builder.create().show();

               }
           });
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           recyclerView.setAdapter(itemAdapter);
           PreferencesManager.addCoins(favouriteCoins, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
         Intent intent = new Intent(this, SettingsActivity.class);
         startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
