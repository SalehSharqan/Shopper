package io.mapwize.mapwizeuicomponents.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AdminLogin extends AppCompatActivity {
    TextInputEditText email, password;
    private ProgressDialog pD;
    CardView Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        Login = findViewById(R.id.login);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pD = new ProgressDialog(AdminLogin.this);
                pD.setMessage("Signing In ... ");
                pD.show();
                if (!email.getText().toString().equals("") && !password.getText().toString().equals(""))
                {
                if (email.getText().toString().equals("ecm@admin.com")) {
                    if (password.getText().toString().equals("12345678")) {
                        Intent x = new Intent(AdminLogin.this , ManageRetailers.class);
                        x.putExtra("user" , "East Coast Mall");
                        pD.dismiss();
                        startActivity(x);
                        finish();
                    }
                } else if (email.getText().toString().equals("kcm@admin.com")) {
                    if (password.getText().toString().equals("12345678")) {
                        Intent x = new Intent(AdminLogin.this , ManageRetailers.class);
                        x.putExtra("user" , "Kuantan City Mall");
                        pD.dismiss();
                        startActivity(x);
                        finish();
                    }
                } else
                {
                    if (password.getText().toString().equals("12345678"))
                    {
                        Intent x = new Intent(AdminLogin.this, ManageRetailers.class);
                        x.putExtra("user","Berjaya Megamall");
                        pD.dismiss();
                        startActivity(x);
                        finish();
                    }
                }
            }
                else
                {
                    Toast.makeText(AdminLogin.this, "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
                }
        });


        findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x7 = new Intent(AdminLogin.this, Login.class);
                startActivity(x7);
                finish();
            }
        });
    }
}
