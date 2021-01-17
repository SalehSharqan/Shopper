package io.mapwize.mapwizeuicomponents.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class ProductsByCategory extends Fragment {
        RecyclerView recyclerView;
        DatabaseReference reff;
        ArrayList<Products> productsList;
        Products_Adapter adapter;
        String cat ,mall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.product_by_category , container , false);
        Bundle b = this.getArguments();
        cat =b.getString("cat");
        mall =b.getString("mall");
        //Toast.makeText(getActivity(), cat, Toast.LENGTH_SHORT).show();
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
      /*  recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));*/

        productsList = new ArrayList<Products>();


        reff  = FirebaseDatabase.getInstance().getReference().child("Products");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if(products.getP_cat().equals(cat) && products.getP_mall().equals(mall)){
                        productsList.add(products);
                    }
                }
                adapter = new Products_Adapter(getActivity(), productsList,"Customer");
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return rootview;

    }


}