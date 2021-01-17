package io.mapwize.mapwizeuicomponents.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class retProfile extends Fragment {
    EditText email, username, password, name, shop_num,
            ret_shop, ret_mall, ret_password, ret_user, ret_email, ret_name, ret_cat;
    CardView Save, Cancel, updatepass;
    LinearLayout cus, ret;
    DatabaseReference reff;
    String useremail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Boolean isret= false;
    public String s;
    public String key, key2, key3;
    Users users2;
    Shops shops;
    CircleImageView pic;
    public String name1, username1, pic1, email1;
    String photoLink;
    public Uri ufff;
    CardView changepic;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.ret_profile, container, false);

        cus = rootview.findViewById(R.id.cus);
        email = rootview.findViewById(R.id.user_email);
        username = rootview.findViewById(R.id.user_name);
        name = rootview.findViewById(R.id.name50);
        pic = rootview.findViewById(R.id.pic50);
        Save = rootview.findViewById(R.id.Save);
        Cancel = rootview.findViewById(R.id.Cancel);
        reff = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth1.getCurrentUser();
        useremail = user.getEmail();
        Bundle b = this.getArguments();
        s = b.getString("type");
        users2 = new Users();
        shops = new Shops();
        changepic = rootview.findViewById(R.id.changepic);


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Retailer"))
                    {
                        if (users.getR_Email().equals(useremail)) {
                            email1 = users.getR_Email().trim();
                            username1 = users.getR_Username().trim();
                            name1 = users.getR_Name().trim();
                            pic1 = users.getR_Photo();
                            key = users.getKey().trim();
                            showall50(email1, name1, pic1, username1, key);

                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });


        rootview.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                retCart fragB = new retCart();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.main_ret_container , fragB).commit();
            }
        });

        return rootview;
    }


    public void showall50(String email1, String name1, String pic1, String username1, String key)
    {

        email.setText(email1);
        username.setText(username1);
        name.setText(name1);
        Picasso.get()
                .load(pic1)
                .into(pic);

        changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGallery();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(key);
                databaseReference.child("r_Email").setValue(email.getText().toString());
                databaseReference.child("r_Username").setValue(username.getText().toString());
                databaseReference.child("r_Name").setValue(name.getText().toString());

                Toast.makeText(getActivity(), "The data modified successfully", Toast.LENGTH_LONG).show();
                Bundle b = new Bundle();
                retCart fragB = new retCart();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.main_ret_container , fragB).commit();

            }
        });
    }


    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pic.setImageBitmap(bitmapImage);
                Picasso.get().load(returnUri).into(pic);
                ufff = returnUri;
            }
        }

    }
}