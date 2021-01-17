package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reff;
    public String userType;
    Users users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();


    }


    public void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user != null)
                {
                   reff = FirebaseDatabase.getInstance().getReference().child("Users");
                        reff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    users = dataSnapshot1.getValue(Users.class);
                                    if(users.getUserType().equals("Customer"))
                                    {
                                        Intent intent = new Intent(SplashActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else
                                    {
                                        Intent intent = new Intent(SplashActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
    }
}
