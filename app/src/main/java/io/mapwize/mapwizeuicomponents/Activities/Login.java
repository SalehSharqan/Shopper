package io.mapwize.mapwizeuicomponents.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;


public class Login extends AppCompatActivity {
    TextInputEditText email, password;
    CardView login;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference reff;
    DatabaseReference reff2;
    Users users;
    public String usertype;
    private ProgressDialog pD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.user_email);
        login = findViewById(R.id.login_btn);
        password = findViewById(R.id.user_password);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pD = new ProgressDialog(Login.this);
                pD.setMessage("Signing In ... ");
                pD.show();


                if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                reff2 = FirebaseDatabase.getInstance().getReference().child("Users");
                                reff2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            users = dataSnapshot1.getValue(Users.class);
                                            if (users.getUserType().equals("Customer"))
                                            {
                                                if(users.getC_Email().equals(email.getText().toString()))
                                                {
                                                    Intent x = new Intent(Login.this, Main.class);
                                                    x.putExtra("user","Cutsomer");
                                                    pD.dismiss();
                                                    startActivity(x);
                                                    finish();
                                                }

                                            }
                                            else
                                            {
                                                if(users.getR_Email().equals(email.getText().toString()))
                                                {
                                                    if(users.getR_Status().equals("Approved"))
                                                    {
                                                        Intent x = new Intent(Login.this, RetailerMain.class);
                                                        x.putExtra("user","Retailer");
                                                        pD.dismiss();
                                                        startActivity(x);
                                                        finish();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(Login.this,"Your account has not been approved by the mall admin", Toast.LENGTH_LONG).show();
                                                        Intent x = new Intent(Login.this, Login.class);
                                                        //x.putExtra("user","Retailer");
                                                        pD.dismiss();
                                                        startActivity(x);
                                                        finish();
                                                    }
                                                }

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(Login.this , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

                                    }
                                });


                            } else {

                                Toast.makeText(Login.this, "Your login information was incorrect, Please check and try again", Toast.LENGTH_LONG).show();
                            }
                        }

                    });

                } else {
                    pD.dismiss();
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


        findViewById(R.id.Forgetpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent x= new Intent(Login.this, UpdatePassoword.class);
                    startActivity(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


/*
      findViewById(R.id.fb_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x= new Intent(Login.this, UpdatePassoword.class);
                startActivity(x);
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
*/

findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent x7 = new Intent(Login.this, AdminLogin.class);
        startActivity(x7);
        finish();
    }
});
    }
}


