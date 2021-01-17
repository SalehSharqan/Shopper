package io.mapwize.mapwizeuicomponents.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.FavoritesAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class Favorites extends Fragment {

    ImageView eye, eye1;
    RecyclerView item_review_recycleview, item_review_recycleview1;
    DatabaseReference reff,reff2;
    ArrayList<Additionals> additionalsList;
    FavoritesAdapter adapter;
boolean flag = true, flag1 =true;
    ArrayList<Additionals> additionalsList1;
    FavoritesAdapter adapter1;

    public String CusUser,CusEmail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.favorites , container , false);

        eye = rootview.findViewById(R.id.eye);
        eye1 = rootview.findViewById(R.id.eye1);
        item_review_recycleview = rootview.findViewById(R.id.item_review_recycleview);
        item_review_recycleview1 = rootview.findViewById(R.id.item_review_recycleview1);
        item_review_recycleview = rootview.findViewById(R.id.item_review_recycleview);
        item_review_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        item_review_recycleview.addItemDecoration(new DividerItemDecoration(item_review_recycleview.getContext(), DividerItemDecoration.VERTICAL));
        item_review_recycleview1 = rootview.findViewById(R.id.item_review_recycleview1);
        item_review_recycleview1.setLayoutManager(new LinearLayoutManager(getActivity()));
        item_review_recycleview1.addItemDecoration(new DividerItemDecoration(item_review_recycleview1.getContext(), DividerItemDecoration.VERTICAL));
        reff2 = FirebaseDatabase.getInstance().getReference();

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag =true)
                {
                    item_review_recycleview.setVisibility(View.GONE);
                    flag=false;
                }
                else
                {
                    item_review_recycleview.setVisibility(View.VISIBLE);
                    flag=true;
                }
            }
        });


        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag1=true)
                {
                    item_review_recycleview1.setVisibility(View.GONE);
                    flag1=false;
                }
                else
                {
                    item_review_recycleview1.setVisibility(View.VISIBLE);
                    flag1=true;
                }
            }
        });

        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        CusEmail = user.getEmail();
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Customer")) {
                        if (users.getC_Email().equals(CusEmail)) {
                            CusUser = users.getC_Username();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });

        additionalsList = new ArrayList<Additionals>();
        additionalsList1 = new ArrayList<Additionals>();



        reff2.child("Additionals").child("ProductsFavorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                    if (CusUser.equals(additionals1.getUser())) {
                        if (additionals1.getFav().equals("yes")) {
                            additionalsList.add(additionals1);
                        }
                    }
                    adapter = new FavoritesAdapter(getActivity(), additionalsList,"product");
                    item_review_recycleview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }


        });


        reff2.child("Additionals").child("ShopFavorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals additionals2 = dataSnapshot1.getValue(Additionals.class);
                    if (CusUser.equals(additionals2.getUser())) {
                        if (additionals2.getFav().equals("yes")) {
                            additionalsList1.add(additionals2);
                        }
                    }
                    adapter1 = new FavoritesAdapter(getActivity(), additionalsList1,"shop");
                    item_review_recycleview1.setAdapter(adapter1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }


        });



        return rootview;
    }


}