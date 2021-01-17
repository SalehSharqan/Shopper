package io.mapwize.mapwizeuicomponents.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Adapters.RetailersAdapter;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageRetailers extends AppCompatActivity {
    TextView mall;
    RecyclerView recyclerView;
    RetailersAdapter retailersAdapter;
    ArrayList<Users> usersArrayList;
    Intent intent = getIntent();
    public String mallname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_retailers);

        mall = findViewById(R.id.mall);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageRetailers.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext() , DividerItemDecoration.VERTICAL));
        Bundle bundle = getIntent().getExtras();
        mallname  = bundle.getString("user");

        mall.setText(mallname.trim());
        usersArrayList = new ArrayList<Users>();

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if (users.getUserType().equals("Retailer")) {
                        if (mallname.equals(users.getR_Mall())) {
                            if (users.getR_Status().equals("Pending")) {
                                usersArrayList.add(users);
                            }
                        }
                    }
                }
                retailersAdapter = new RetailersAdapter(ManageRetailers.this, usersArrayList);
                recyclerView.setAdapter(retailersAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManageRetailers.this , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x7 = new Intent(ManageRetailers.this, Login.class);
                startActivity(x7);
                finish();
            }
        });
    }
}




