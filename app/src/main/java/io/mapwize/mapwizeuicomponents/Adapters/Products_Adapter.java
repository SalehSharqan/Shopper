package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.mapwize.mapwizeuicomponents.Fragments.ModifyProduct;
import io.mapwize.mapwizeuicomponents.Fragments.ProductInfo;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Products> products;
    private ArrayList<Products> productsFull;



    private String productid, shop;
    private String usertype;
    public Products_Adapter(Context c, ArrayList<Products> s, String us) {
        context = c;
        products = s;
        usertype = us;
        productsFull = new ArrayList<>(s);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.products_card, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.product_name.setText(products.get(position).getP_name());
        holder.product_price.setText(products.get(position).getDisPrice());
        Picasso.get().load(products.get(position).getP_photo()).into(holder.imageproduct);
        holder.imageproduct.setScaleType(ImageView.ScaleType.FIT_XY);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                try {
                    productid=products.get(position).getId();
                    shop = products.get(position).getP_shop();
                    Bundle bundle = new Bundle();
                    bundle.putString("productid", productid);
                    bundle.putString("shop", shop);
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
        ImageView imageproduct,fav;

        public MyViewHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            imageproduct = itemView.findViewById(R.id.imageproduct);
            fav = itemView.findViewById(R.id.fav);
            cardView = itemView.findViewById(R.id.card_view);
        }

    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Products> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Products item : productsFull) {
                    if (item.getP_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}