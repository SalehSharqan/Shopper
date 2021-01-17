package io.mapwize.mapwizeuicomponents.Fragments;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.OffersAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.ReceiptsAdapter;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class Receipts extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference reff;
    ArrayList<Additionals> additionalsArrayList;
    ReceiptsAdapter adapter;
    Additionals additionals;
    public Uri ufff;
    String photoLink;
    ImageView Ads_pic;
    CardView Save_ads, Add_ads;
    final Random rand = new Random();
    private ProgressDialog pD;
     StorageReference filePath;
    private StorageReference mStroage;
    long maxid = 0;

    public String CusUser,CusEmail;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_receipts , container , false);
        mStroage = FirebaseStorage.getInstance().getReference();

        Ads_pic = rootview.findViewById(R.id.Ads_pic);
        Add_ads = rootview.findViewById(R.id.Add_ads);
        Save_ads = rootview.findViewById(R.id.Save_ads);
        additionals = new Additionals();
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        additionalsArrayList = new ArrayList<Additionals>();

        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        CusEmail = user.getEmail();
        reff = FirebaseDatabase.getInstance().getReference().child("Additionals");

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if (users.getUserType().equals("Customer")) {
                        if (users.getC_Email().equals(CusEmail)) {
                            CusUser = users.getC_Username();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });
/*
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals ads = dataSnapshot1.getValue(Additionals.class);
                    if (dataSnapshot.child("Receipts").exists()) {
                        maxid = (dataSnapshot.child("Receipts").getChildrenCount());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        FirebaseDatabase.getInstance().getReference().child("Additionals").child("Receipts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals ads1 = dataSnapshot1.getValue(Additionals.class);
                        if (CusUser.equals(ads1.getUser())) {
                            additionalsArrayList.add(ads1);
                    }

                }
                adapter = new ReceiptsAdapter(getActivity(), additionalsArrayList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();


            }
        });
        Add_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startGallery();
                    Ads_pic.setVisibility(View.VISIBLE);
                    Save_ads.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rand.nextLong();


        rootview.findViewById(R.id.Save_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pD = new ProgressDialog(getActivity());
                pD.setMessage("Saving Data... ");
                pD.show();
                filePath = mStroage.child("Receipts").child(CusUser).child(String.valueOf(rand.nextLong()));
          filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        final Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoLink = uri.toString();
                                additionals.setReceipts(photoLink);
                                additionals.setUser(CusUser);
                                if (task.isSuccessful()) {
                                    reff.child("Receipts").push().setValue(additionals);
                                    Toast.makeText(getActivity() , "The shop image added secuessfully" , Toast.LENGTH_LONG).show();
                                    pD.dismiss();
                                    Ads_pic.setVisibility(View.GONE);
                                    Save_ads.setVisibility(View.GONE);
                                }
                                else{
                                        Toast.makeText(getActivity() , task.getException().toString() , Toast.LENGTH_LONG).show();
                                    }

                                }
                        });
                    }

                });
            }
        });



        return rootview;
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
                Ads_pic.setImageBitmap(bitmapImage);
                Picasso.get().load(returnUri).into(Ads_pic);
                ufff = returnUri;
            }
        }

    }

}
