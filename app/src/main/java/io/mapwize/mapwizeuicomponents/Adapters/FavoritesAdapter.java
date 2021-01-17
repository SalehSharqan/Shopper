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

import io.mapwize.mapwizeuicomponents.Fragments.Favorites;
import io.mapwize.mapwizeuicomponents.Fragments.ModifyProduct;
import io.mapwize.mapwizeuicomponents.Fragments.ProductInfo;
import io.mapwize.mapwizeuicomponents.Fragments.ShopInfo;
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


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>{

    private Context context;

    private ArrayList<Additionals> additionalsArrayList;



    public String CusUser,CusEmail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private String productid, shop;
    private String usertype;
    public FavoritesAdapter(Context c, ArrayList<Additionals> s, String us) {
        context = c;
        additionalsArrayList = s;
        usertype = us;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_card, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        if (usertype.equals("product")) {
            holder.product_name.setText(additionalsArrayList.get(position).getName());
            holder.product_price.setText(additionalsArrayList.get(position).getCate());
            Picasso.get().load(additionalsArrayList.get(position).getPhoto()).into(holder.imageproduct);
            holder.imageproduct.setScaleType(ImageView.ScaleType.FIT_XY);
            productid = additionalsArrayList.get(position).getProducid();
            firebaseAuth1 = FirebaseAuth.getInstance();
            user = firebaseAuth1.getCurrentUser();
            CusEmail = user.getEmail();
            FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Users users = dataSnapshot1.getValue(Users.class);
                        if (users.getUserType().equals("Customer")) {
                            if (users.getC_Email().equals(CusEmail)) {
                                CusUser = users.getC_Username();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

                }
            });


            holder.fav.setVisibility(View.VISIBLE);
            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context , "The Item has been removed" , Toast.LENGTH_SHORT).show();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Additionals").child("ProductsFavorites").child(CusUser + productid);
                    databaseReference.removeValue();
                }
            });


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                    try {
                        productid = additionalsArrayList.get(position).getProducid();
                        shop = additionalsArrayList.get(position).getShop();
                        Bundle bundle = new Bundle();
                        bundle.putString("productid" , productid);
                        bundle.putString("shop" , shop);
                        ProductInfo fragB = new ProductInfo();
                        fragB.setArguments(bundle);
                        manager.beginTransaction().replace(R.id.main_fragment_container , fragB).commit();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        else
        {
            holder.product_name.setText(additionalsArrayList.get(position).getName());
            holder.product_price.setText(additionalsArrayList.get(position).getCate());
            Picasso.get().load(additionalsArrayList.get(position).getPhoto()).into(holder.imageproduct);
            holder.imageproduct.setScaleType(ImageView.ScaleType.FIT_XY);

            shop = additionalsArrayList.get(position).getShop();

            firebaseAuth1 = FirebaseAuth.getInstance();
            user = firebaseAuth1.getCurrentUser();
            CusEmail = user.getEmail();
            FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Users users = dataSnapshot1.getValue(Users.class);
                        if (users.getUserType().equals("Customer")) {
                            if (users.getC_Email().equals(CusEmail)) {
                                CusUser = users.getC_Username();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


            holder.fav.setVisibility(View.VISIBLE);
            holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context , "The Item has been removed" , Toast.LENGTH_SHORT).show();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Additionals").child("ShopFavorites").child(CusUser + shop);
                    databaseReference.removeValue();
                }
            });



            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("shopid", shop);
                        bundle.putString("shopname", additionalsArrayList.get(position).getName());
                        ShopInfo fragB = new ShopInfo();
                        fragB.setArguments(bundle);
                        manager.beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return additionalsArrayList.size();
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




}