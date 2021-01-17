package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ReProductsList extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference reff;
    ArrayList<Products> productsList;
    Products_Adapter adapter;
    CardView add;;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;
    DatabaseReference reff2;
    public  String userType, currentuser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.re_products_list, container, false);

        add = rootview.findViewById(R.id.add);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        productsList = new ArrayList<Products>();
        reff2 = FirebaseDatabase.getInstance().getReference().child("Products");
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Products products = dataSnapshot1.getValue(Products.class);
                    if(products.getShopOwner().equals(user.getEmail()))
                    {
                        currentuser = products.getShopOwner();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reff = FirebaseDatabase.getInstance().getReference().child("Products");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    Products products = dataSnapshot1.getValue(Products.class);

                    if(currentuser != null && currentuser.equals(user.getEmail()))
                    {
                        productsList.add(products);
                    }
                }

                adapter = new Products_Adapter(getActivity(), productsList, "Retailer");
                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Product fragB = new Add_Product();
                getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();

            }
        });
        return rootview;
    }
}