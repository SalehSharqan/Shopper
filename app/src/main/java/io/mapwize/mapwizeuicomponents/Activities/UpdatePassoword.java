package io.mapwize.mapwizeuicomponents.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UpdatePassoword extends AppCompatActivity {
    CardView send;
    EditText email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_passoword);

        send = findViewById(R.id.send);
        email = findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = email.getText().toString().trim();
                if (TextUtils.isEmpty(useremail))
                {
                    Toast.makeText(UpdatePassoword.this, "Please fill up the box", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    auth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(UpdatePassoword.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdatePassoword.this, Login.class ));

                            }
                            else
                            {
                                Toast.makeText(UpdatePassoword.this, "Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            }
        });
        findViewById(R.id.sing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(UpdatePassoword.this, Login.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
