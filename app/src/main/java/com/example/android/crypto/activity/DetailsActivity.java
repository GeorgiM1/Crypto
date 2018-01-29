package com.example.android.crypto.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.crypto.R;
import com.example.android.crypto.model.Coin;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.details_24h_change)
    TextView change24h;
    @BindView(R.id.details_available_supply_get)
    TextView availableSupply;
    @BindView(R.id.details_price_BTC_get)
    TextView btcPrice;
    @BindView(R.id.details_price_USD_get)
    TextView usdPrice;
    @BindView(R.id.details_total_supply_get)
    TextView totalSupply;
    @BindView(R.id.details_coin_img)
    ImageView coinImg;
    @BindView(R.id.details_coin_symbol)
    TextView symbolOfCoin;
    Coin coin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        coin =(Coin) getIntent().getSerializableExtra("COIN_EXTRA");
        if (coin.getPercent_change_24h() > 0){
            change24h.setText(coin.getPercent_change_24h() + "%");
            change24h.setTextColor(getResources().getColor(R.color.Green));
        }else{
            change24h.setText(coin.getPercent_change_24h() + "%");
        change24h.setTextColor(getResources().getColor(R.color.Red));}

        availableSupply.setText(coin.getAvailable_supply());
        btcPrice.setText(coin.getPrice_btc());
        usdPrice.setText(coin.getPrice_usd());
        totalSupply.setText(coin.getTotal_supply());
        symbolOfCoin.setText(coin.getSymbol());


        Picasso.with(this).load("https://files.coinmarketcap.com/static/img/coins/64x64/"+coin.getId()+ ".png").centerCrop().fit().into(coinImg);
    }
}
