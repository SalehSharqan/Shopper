package io.mapwize.mapwizeuicomponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tryie extends AppCompatActivity {

    Button tryb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryie);

        tryb = findViewById(R.id.tryb);

        tryb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(tryie.this, MapView.class);
                startActivity(x);
            }
        });
    }
}
