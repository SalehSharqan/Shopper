package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.mapwize.mapwizeuicomponents.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PreRegister extends AppCompatActivity {
    CardView Ret_btn, cus_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preregister);

        cus_btn = findViewById(R.id.cus_btn);
        Ret_btn = findViewById(R.id.Ret_btn);


        cus_btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(PreRegister.this, Register.class);
                        intent.putExtra("regType", "Customer");
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        });



        cus_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(PreRegister.this, Register.class);
                    intent.putExtra("regType", "Customer");
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Ret_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(PreRegister.this, Register.class);
                    intent.putExtra("regType", "Retailer");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

}

