package io.mapwize.mapwizeuicomponents.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.Activities.Main;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

public class MallsFragment extends Fragment {

    CardView kcm,ecm, bmm;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String useremail, key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_malls, container, false);

        ((Main)getActivity()).hidenav();
        kcm = rootview.findViewById(R.id.kcm);
        ecm = rootview.findViewById(R.id.ecm);
        bmm = rootview.findViewById(R.id.bmm);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        useremail = user.getEmail();
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Customer"))
                    {
                        if (users.getC_Email().equals(useremail)) {
                            key = users.getKey();
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        kcm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                  /*  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("mallba", "Kuantan City Mall"); //InputString: from the EditText
                    editor.apply();*/
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("c_mall");
                    databaseReference.setValue("Kuantan City Mall");
                    Bundle b = new Bundle();
                    b.putString("mall", "Kuantnan City Mall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ecm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
               /*     SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("mallba", "East Coast Mall"); //InputString: from the EditText
                    editor.apply();
*/
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("c_mall");
                    databaseReference.setValue("East Coast Mall");
                    Bundle b = new Bundle();
                    b.putString("mall", "East Coast Mall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        bmm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
/*
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("mallba", "Berjaya Megamall"); //InputString: from the EditText
                    editor.apply();*/

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key).child("c_mall");
                    databaseReference.setValue("Berjaya Megamall");
                    Bundle b = new Bundle();
                    b.putString("mall", "Berjaya Megamall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);  
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootview;


    }


}