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
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class CartFragment extends Fragment {
    String useremail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CircleImageView pic;
    TextView name,username, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cart , container , false);


        pic = rootview.findViewById(R.id.pic);
        name = rootview.findViewById(R.id.name);
        username = rootview.findViewById(R.id.username);
        email= rootview.findViewById(R.id.email);

        useremail = user.getEmail();


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Customer"))
                    {
                        if (users.getC_Email().equals(useremail)) {

                            email.setText(users.getC_Email().trim());
                            username.setText(users.getC_Username().trim());
                            name.setText(users.getC_Name().trim());
                            Picasso.get()
                                    .load(users.getC_Photo())
                                    .into(pic);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });


        rootview.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                UpdatePass fragB = new UpdatePass();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container , fragB).commit();
            }
        });

        rootview.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                ProfileFragment fragB = new ProfileFragment();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container , fragB).commit();
            }
        });
        return rootview;

    }


}