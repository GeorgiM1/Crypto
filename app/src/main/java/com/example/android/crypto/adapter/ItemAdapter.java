package com.example.android.crypto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.crypto.R;
import com.example.android.crypto.activity.DetailsActivity;
import com.example.android.crypto.activity.Main2Activity;
import com.example.android.crypto.activity.MainActivity;
import com.example.android.crypto.model.Coin;
import com.example.android.crypto.model.FavouriteCoins;
import com.example.android.crypto.other.OnRowClickListener;
import com.example.android.crypto.other.PreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pc on 1/21/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Coin> coins;
    OnRowClickListener onRowClickListener;


    public ItemAdapter(Context context, ArrayList<Coin> coins, OnRowClickListener onRowClickListener) {
        this.context = context;
        this.coins = coins;
        this.onRowClickListener = onRowClickListener;
        notifyDataSetChanged();
    }
//    public void setOnRowClickListener(OnRowClickListener onRowClickListener){
//        this.onRowClickListener = onRowClickListener;
//
//    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, final int position) {
        final Coin _coin = coins.get(position);
        holder.currencySymbol.setText(_coin.getSymbol());
        holder.nameOfCoin.setText(_coin.getName());
        if (PreferencesManager.getFiat(context).equals("EUR")){
            holder.priceOfCoin.setText(_coin.getPrice_eur());
        }else {
            holder.priceOfCoin.setText(_coin.getPrice_usd());
        }
        holder.marketCapOfCoin.setText(_coin.getMarket_cap_usd());
        holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("COIN_EXTRA", _coin);
                intent.putExtra("COIN_POS", position);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRowClickListener.onRowClick(_coin, position);
            }
        });
        if (context instanceof Main2Activity) {
            if (((Main2Activity) context).favouriteCoins.hasCoin(_coin.getId())) {
                holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp_full));

            } else
                holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

        }


        holder.addToFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((Main2Activity) context).favouriteCoins == null) {
                    ((Main2Activity) context).favouriteCoins = new FavouriteCoins();
                    holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp_full));
                } else
                    holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

                if (((Main2Activity) context).favouriteCoins.hasCoin(_coin.getId())) {
                    int index = ((Main2Activity) context).favouriteCoins.getPosition(_coin.getId());
                    ((Main2Activity) context).favouriteCoins.favouriteCoins.remove(index);
                    holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));

                } else {
                    ((Main2Activity) context).favouriteCoins.favouriteCoins.add(_coin);
                    holder.addToFavouritesBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp_full));



                }
            }
        });
        if (context instanceof MainActivity) {
            holder.addToFavouritesBtn.setVisibility(View.INVISIBLE);
        } else holder.addToFavouritesBtn.setVisibility(View.VISIBLE);
        Picasso.with(context).load("https://files.coinmarketcap.com/static/img/coins/64x64/" + _coin.getId() + ".png").centerCrop().fit().into(holder.coinImg);


    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coin_name)
        TextView nameOfCoin;
        @BindView(R.id.coin_price)
        TextView priceOfCoin;
        @BindView(R.id.coin_market_cap)
        TextView marketCapOfCoin;
        @BindView(R.id.coin_img)
        ImageView coinImg;
        @BindView(R.id.symbol_of_currency)
        TextView currencySymbol;
        @BindView(R.id.add_to_favourites)
        ImageView addToFavouritesBtn;
        @BindView(R.id.details_btn)
        Button detailsBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}