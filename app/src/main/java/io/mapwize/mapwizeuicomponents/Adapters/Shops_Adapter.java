package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.mapwize.mapwizeuicomponents.Fragments.ShopInfo;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class Shops_Adapter extends RecyclerView.Adapter<Shops_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Shops> shops;
    public static String shopid, shopname;



    public Shops_Adapter(Context c, ArrayList<Shops> s) {
        context = c;
        shops = s;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shops_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.shop_name.setText(shops.get(position).getShopName());
       holder.shops_cat.setText(shops.get(position).getShopCategory());
       Picasso.get().load(shops.get(position).getShoplogo()).into(holder.shop_image);
       holder.shop_image.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                try {
                   shopid= shops.get(position).getShopNumber();
                   shopname= shops.get(position).getShopName();
                    Bundle bundle = new Bundle();
                    bundle.putString("shopid", shopid);
                    bundle.putString("shopname", shopname);
                    ShopInfo fragB = new ShopInfo();
                    fragB.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return shops.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shop_name, shops_cat;
        CardView cardView;
        ImageView shop_image;
        public MyViewHolder(View itemView) {
            super(itemView);

            shop_name = itemView.findViewById(R.id.shop_name);
            shops_cat = itemView.findViewById(R.id.shop_cat);
            cardView = itemView.findViewById(R.id.card_view);
            shop_image = itemView.findViewById(R.id.shop_image);

        }

    }
}
