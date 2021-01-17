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
import io.mapwize.mapwizeuicomponents.Adapters.OffersAdapter;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OffersList extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference reff;
    ArrayList<Ads> adsList;
    OffersAdapter adapter;
    CardView add;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String retailer_mall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.offers_list, container, false);

        add = rootview.findViewById(R.id.add);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops = dataSnapshot1.getValue(Shops.class);
                    if (user.getEmail().equals(shops.getOwnerEmail())) {
                        retailer_mall = shops.getMallName();
                        show(retailer_mall);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bundle b = new Bundle();
                    b.putString("source", "Offers");
                    AddImages fragB = new AddImages();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootview;
    }

    public void show(String retailer_mall) {
      //  Toast.makeText(getActivity(), retailer_mall, Toast.LENGTH_SHORT).show();
        adsList = new ArrayList<Ads>();
        reff = FirebaseDatabase.getInstance().getReference().child("Images").child("Offers").child(retailer_mall);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ads ads = dataSnapshot1.getValue(Ads.class);
                    if (ads.getOwner().equals(user.getEmail())) {
                        adsList.add(ads);
                    }

                }
                adapter = new OffersAdapter(getActivity(), adsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();


            }
        });


    }
}
