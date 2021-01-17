package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.mapwize.mapwizeuicomponents.Fragments.ModifyProduct;
import io.mapwize.mapwizeuicomponents.Fragments.ProductInfo;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Products> products;
    String productid;
    String usertype;
    public Products_Adapter(Context c, ArrayList<Products> s, String us) {
        context = c;
        products = s;
        usertype = us;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.products_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.product_name.setText(products.get(position).getP_name());
        holder.product_price.setText(products.get(position).getP_price());
     /*   Picasso.get().load(products.get(position).getP_photo.into(holder.imageproduct);
        holder.imageproduct.setScaleType(ImageView.ScaleType.FIT_XY);*/


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                try {
                    productid=products.get(position).getId();
                    Bundle bundle = new Bundle();
                    bundle.putString("productid", productid);
                    if(usertype.equals("Retailer"))
                    {
                        ModifyProduct fragB = new ModifyProduct();
                        fragB.setArguments(bundle);
                        manager.beginTransaction().replace(R.id.main_ret_container, fragB).commit();

                    }
                    else
                    {
                        ProductInfo fragB = new ProductInfo();
                        fragB.setArguments(bundle);
                        manager.beginTransaction().replace(R.id.main_fragment_container, fragB).commit();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, desc, product_price;
        CardView cardView;
        ImageView imageproduct;

        public MyViewHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            imageproduct = itemView.findViewById(R.id.imageproduct);
            cardView = itemView.findViewById(R.id.card_view);
        }

    }
}
