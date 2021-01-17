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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.NotesAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.ReceiptsAdapter;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class Notes extends Fragment {
    RecyclerView recyclerView;
    CardView Save;
    EditText p_desc;
    Additionals additionals;
    public String CusUser,CusEmail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<Additionals> additionalsArrayList;
    NotesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.notes , container , false);

        Save = rootview.findViewById(R.id.Save);
        p_desc = rootview.findViewById(R.id.p_desc);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        additionals =new Additionals();
        additionalsArrayList = new ArrayList<Additionals>();

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
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!p_desc.getText().toString().equals(""))
                {
                    additionals.setUser(CusUser);
                    additionals.setNote(p_desc.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Additionals").child("Notes").push().setValue(additionals);
                }

            }
        });



        FirebaseDatabase.getInstance().getReference().child("Additionals").child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                    if (CusUser.equals(additionals1.getUser())) {
                        additionalsArrayList.add(additionals1);
                    }

                }
                adapter = new NotesAdapter(getActivity(), additionalsArrayList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });

        return rootview;
    }


}