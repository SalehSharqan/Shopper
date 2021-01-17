package io.mapwize.mapwizeuicomponents.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.mapwize.mapwizeuicomponents.Adapters.FeedbackAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Feedback;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;


public class RetailerHome extends Fragment {
    RecyclerView  item_review_recycleview, item_review_recycleview1;
    DatabaseReference reff;
    ArrayList<Products> productsList;
    Products_Adapter adapter;
    CardView add;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;
    DatabaseReference reff2, reff3, reff4;;
    public  String userType, currentuser;
    CircleImageView imageView;
    TextView shop, cat;
    public String shopid, productid;
    FeedbackAdapter feedbackAdapter, feedbackAdapter1;
    ArrayList<Feedback> feedbackList, feedbackList1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.retailer_home, container, false);
/*
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));*/
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        imageView = rootview.findViewById(R.id.imageView);
        shop = rootview.findViewById(R.id.shop);
        cat = rootview.findViewById(R.id.cat);
        feedbackList = new ArrayList<Feedback>();
        feedbackList1 = new ArrayList<Feedback>();

        item_review_recycleview = rootview.findViewById(R.id.item_review_recycleview);
        item_review_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        item_review_recycleview.addItemDecoration(new DividerItemDecoration(item_review_recycleview.getContext(), DividerItemDecoration.VERTICAL));

        item_review_recycleview1 = rootview.findViewById(R.id.item_review_recycleview1);
        item_review_recycleview1.setLayoutManager(new LinearLayoutManager(getActivity()));
        item_review_recycleview1.addItemDecoration(new DividerItemDecoration(item_review_recycleview1.getContext(), DividerItemDecoration.VERTICAL));



        reff3 = FirebaseDatabase.getInstance().getReference().child("Shops");
        reff3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops2 = dataSnapshot1.getValue(Shops.class);
                    if(user.getEmail().equals(shops2.getOwnerEmail()))
                    {
                        shop.setText(shops2.getShopName());
                        cat.setText(shops2.getShopCategory());
                        shopid =  shops2.getShopNumber();
                        Picasso.get()
                                .load(shops2.getShoplogo())
                                .into(imageView);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if(user.getEmail().equals(products.getShopOwner()))
                    {
                        productid = products.getId();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });



     /*   productsList = new ArrayList<Products>();
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
            });*/




        FirebaseDatabase.getInstance().getReference().child("Feedback").child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Feedback feedback = dataSnapshot1.getValue(Feedback.class);
                    if (feedback.getShop().equals(shopid)) {
                        feedbackList.add(feedback);
                    }
                }

                feedbackAdapter = new FeedbackAdapter(getActivity(), feedbackList, "Retailer");
                item_review_recycleview.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



        FirebaseDatabase.getInstance().getReference().child("Feedback").child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Feedback feedback = dataSnapshot1.getValue(Feedback.class);
                    if (feedback.getProduct().equals(productid)) {
                        feedbackList1.add(feedback);
                    }
                }

                feedbackAdapter1 = new FeedbackAdapter(getActivity(), feedbackList1, "Retailer");
                item_review_recycleview1.setAdapter(feedbackAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

/*
        reff2.child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Feedback feedback = dataSnapshot1.getValue(Feedback.class);
                    if (feedback.getShop().equals(shopid)) {
                        feedbackList.add(feedback);
                    }
                }

                feedbackAdapter = new FeedbackAdapter(getActivity(), feedbackList, "Customer");
                recyclerView.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
*/


  /*      reff = FirebaseDatabase.getInstance().getReference().child("Products");
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
        });*/


        return rootview;
    }


}