package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.R;


public class Login extends AppCompatActivity {
    EditText email, password;
    CardView login;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reff;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.user_email);
        login = findViewById(R.id.login_btn);
        password = findViewById(R.id.user_password);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3  = findViewById(R.id.radioButton3);
        radio_g=findViewById(R.id.usergrp);


        firebaseAuth = FirebaseAuth.getInstance();
       // reff = FirebaseDatabase.getInstance().getReference().child("Customer").child("1");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                int selectedId = radio_g.getCheckedRadioButtonId();
                                RadioButton userbtn = findViewById(selectedId);

                                String user = userbtn.getText().toString();
                                if (user.equals("Customer")) {
                                    Intent x = new Intent(Login.this, Main.class);
                                    x.putExtra("user","Cutsomer");
                                    startActivity(x);
                                    finish();
                                }
                                else if (user.equals("Retailer"))
                                {
                                    Intent x = new Intent(Login.this, RetailerMain.class);
                                    x.putExtra("user","Retailer");
                                    startActivity(x);
                                    finish();
                                }
                                else if (user.equals("Admin"))
                                {
                                    Toast.makeText(Login.this, "wait", Toast.LENGTH_LONG).show();
                                }

                            } else {

                                Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                } else {
                    Toast.makeText(Login.this, "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.no_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Login.this, PreRegister.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

      findViewById(R.id.fb_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       findViewById(R.id.gplus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

      findViewById(R.id.twt_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
