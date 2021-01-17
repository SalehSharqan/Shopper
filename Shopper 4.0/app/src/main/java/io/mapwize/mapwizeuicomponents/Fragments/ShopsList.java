package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Adapters.Shops_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ShopsList extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reff;
    ArrayList<Shops> shopsList;
    Shops_Adapter adapter;
    String  mall;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_shops, container, false);

        Bundle b = this.getArguments();
        mall=b.getString("mallname");

        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        shopsList = new ArrayList<Shops>();
        reff = FirebaseDatabase.getInstance().getReference().child("Shops");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                        Shops shops = dataSnapshot1.getValue(Shops.class);
                        if(shops.getMallName().equals(mall))
                        {
                            shopsList.add(shops);
                        }

                }

                    if (shopsList.isEmpty()) {
                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }else {
                        adapter = new Shops_Adapter(getActivity(), shopsList);
                        recyclerView.setAdapter(adapter);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return rootview;
    }
}