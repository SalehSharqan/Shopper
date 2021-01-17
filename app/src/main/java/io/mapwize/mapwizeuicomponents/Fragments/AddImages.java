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
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class AddImages extends Fragment {

    ImageView Ads_pic;
    public Uri ufff;
    DatabaseReference reff;
    String photoLink;
    Ads ads;
    String Emaill;
    public String retailer_mall, shopid, productID;
    private StorageReference mStroage;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    long maxid = 0;
    public String s,p,r;
    final Random rand = new Random();
    private ProgressDialog pD;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.add_images, container, false);
        Ads_pic = rootview.findViewById(R.id.Ads_pic);
        ads = new Ads();
        mStroage = FirebaseStorage.getInstance().getReference();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        Emaill = user.getEmail();
        Bundle b = this.getArguments();
        reff = FirebaseDatabase.getInstance().getReference();
        s = b.getString("source");
        p = b.getString("product");

        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();


        rootview.findViewById(R.id.Add_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGallery();
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops = dataSnapshot1.getValue(Shops.class);
                    if (Emaill.equals(shops.getOwnerEmail())) {
                        retailer_mall = shops.getMallName();
                        shopid = shops.getShopNumber();
                        //key =shops.getKey().trim() ""add key (when the shop is added)""
                       // Toast.makeText(getActivity(), retailer_mall, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });
        if (s.equals("Products")) {
            FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Products products = dataSnapshot1.getValue(Products.class);
                        if (Emaill.equals(products.getShopOwner())) {
                            if (p.equals(products.getId()))
                            {
                                productID = products.getId();}
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

                }
            });

        }



        FirebaseDatabase.getInstance().getReference().child("Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ads ads = dataSnapshot1.getValue(Ads.class);
                    if (s.equals("Offers")) {
                        if (dataSnapshot.child("Offers").child(retailer_mall).exists()) {
                            maxid = (dataSnapshot.child("Offers").child(retailer_mall).getChildrenCount());
                        }

                    } else if (s.equals("Products")) {
                        if (dataSnapshot.child("Products").child(productID).exists()) {
                            maxid = (dataSnapshot.child("Products").child(productID).getChildrenCount());
                        }
                    } else {
                        if (dataSnapshot.child("Shops").child(shopid).exists()) {
                            maxid = (dataSnapshot.child("Shops").child(shopid).getChildrenCount());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });


        rand.nextLong();


        rootview.findViewById(R.id.Save_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pD = new ProgressDialog(getActivity());
                pD.setMessage("Saving Data... ");
                pD.show();
                savePathstorage().putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        final Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoLink = uri.toString();
                                ads.setMall(retailer_mall);
                                ads.setPhoto(photoLink);
                                ads.setOwner(Emaill);
                                if (task.isSuccessful()) {
                                    savePath();
                                    pD.dismiss();
                                    getFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.main_ret_container, new RetailerHome(), "RetailerHome")
                                            .addToBackStack("RetailerHome")
                                            .commit();}
                                 else {
                                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }

                });
            }
        });

        rootview.findViewById(R.id.Cancel_ads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getFragmentManager() != null) {
                        RetailerHome fragB = new RetailerHome();
                        getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public void savePath() {
        if (s.equals("Offers")) {
            reff.child("Images").child("Offers").child(retailer_mall).child(String.valueOf(maxid + 1)).setValue(ads);
            Toast.makeText(getActivity(), "The offer image added secuessfully", Toast.LENGTH_LONG).show();}

     else if (s.equals("Products")) {
            reff.child("Images").child("Products").child(productID).child(String.valueOf(maxid + 1)).setValue(ads);
        Toast.makeText(getActivity(), "The product image added secuessfully", Toast.LENGTH_LONG).show();


    }else {
            reff.child("Images").child("Shops").child(shopid).child(String.valueOf(maxid + 1)).setValue(ads);
            Toast.makeText(getActivity(), "The shop image added secuessfully", Toast.LENGTH_LONG).show();

        }
    }

    public StorageReference savePathstorage() {
        final StorageReference filePath;
        if (s.equals("Offers")) {
            filePath = mStroage.child("OffersPhotos").child(retailer_mall).child(String.valueOf(rand.nextLong()));
        } else if (s.equals("Products") || s.equals("ProductsADD")) {
            filePath = mStroage.child("ProductsPhotos").child(p).child(String.valueOf(rand.nextLong()));
        } else {
            filePath = mStroage.child("ShopPhotos").child(shopid).child(String.valueOf(rand.nextLong()));

        }
        return filePath;
    }

}

