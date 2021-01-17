package io.mapwize.mapwizeuicomponents.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import io.mapwize.mapwizeuicomponents.Activities.Login;
import io.mapwize.mapwizeuicomponents.Activities.ManageRetailers;
import io.mapwize.mapwizeuicomponents.Fragments.Favorites;
import io.mapwize.mapwizeuicomponents.Fragments.ModifyProduct;
import io.mapwize.mapwizeuicomponents.Fragments.ProductInfo;
import io.mapwize.mapwizeuicomponents.Fragments.ShopInfo;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class RetailersAdapter extends RecyclerView.Adapter<RetailersAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Users> RetList;
    public String RetEmail, RetShop, RetshopID;
    private ProgressDialog pD;

    public RetailersAdapter(Context c, ArrayList<Users> s) {
        context = c;
        RetList = s;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.retailers_card, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        RetEmail= RetList.get(position).getR_Email();

        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops2 = dataSnapshot1.getValue(Shops.class);
                    if(RetEmail.equals(shops2.getOwnerEmail()))
                    {
                        RetShop = shops2.getShopName();
                        RetshopID = shops2.getShopNumber();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.product_name.setText(RetShop);
        holder.product_price.setText(RetshopID);
        holder.retname.setText(RetList.get(position).getR_Name());
        Picasso.get().load(RetList.get(position).getR_Photo()).into(holder.imageproduct);
        holder.imageproduct.setScaleType(ImageView.ScaleType.FIT_XY);


        holder.rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context , "The retailer has been rejected" , Toast.LENGTH_SHORT).show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(RetList.get(position).getKey()).child("r_Status");
                databaseReference.setValue("Rejected");
                Intent x = new Intent(context, Login.class);
                context.startActivity(x);


            }
        });

        holder.app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context , "The retailer has been approved" , Toast.LENGTH_SHORT).show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(RetList.get(position).getKey()).child("r_Status");
                databaseReference.setValue("Approved");
                Intent x = new Intent(context, Login.class);
                //x.putExtra("user","Retailer");
                context.startActivity(x);

            }
        });


    }

    @Override
    public int getItemCount() {
        return RetList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, retname, product_price;
        CardView cardView;
        ImageView imageproduct,rej, app;

        public MyViewHolder(View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            retname = itemView.findViewById(R.id.retname);
            imageproduct = itemView.findViewById(R.id.imageproduct);
            rej = itemView.findViewById(R.id.rej);
            app = itemView.findViewById(R.id.app);
            cardView = itemView.findViewById(R.id.card_view);
        }

    }




}