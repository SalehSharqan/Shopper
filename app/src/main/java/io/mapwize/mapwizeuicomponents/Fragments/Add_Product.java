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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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
import java.util.Random;

import io.mapwize.mapwizeuicomponents.Activities.Login;
import io.mapwize.mapwizeuicomponents.Activities.Register;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class Add_Product extends Fragment {
    EditText p_desc, p_id, P_name, p_price, discount;
    AutoCompleteTextView p_cat;
    CheckBox disCheck;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    Products products;
    String s;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String Emaill;
    public String retailer_shop, mall_name, retname;
    CircleImageView pic;
    String photoLink;
    long maxid = 0;
    public Uri ufff;
    private StorageReference mStroage;
    private ProgressDialog pD;
    Ads ads;

    private String[] Categories = {"Fashion", "Mobiles & Electronics", "Home & Living", "Daily Needs", "Sports", "Baby & Kids", "Books", "Others"};




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.add_product, container, false);
        mStroage = FirebaseStorage.getInstance().getReference();

        ads = new Ads();
        p_price = rootview.findViewById(R.id.p_price);
        P_name = rootview.findViewById(R.id.prod_name);
        p_id = rootview.findViewById(R.id.p_id);
        p_desc = rootview.findViewById(R.id.p_desc);
        disCheck = rootview.findViewById(R.id.disCheck);
        p_cat = rootview.findViewById(R.id.p_cat);

        discount = rootview.findViewById(R.id.discount);
        pic =  rootview.findViewById(R.id.pic);
        reff = FirebaseDatabase.getInstance().getReference();
        products = new Products();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        Emaill = user.getEmail();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, Categories);
        p_cat.setThreshold(1);
        p_cat.setAdapter(adapter2);


        rootview.findViewById(R.id.pic).setOnClickListener(new View.OnClickListener() {
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
                        retailer_shop = shops.getShopName();
                        mall_name = shops.getMallName();
                        //Toast.makeText(getActivity(), retailer_shop, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ads ads = dataSnapshot1.getValue(Ads.class);
                        if (dataSnapshot.child("Products").child(p_id.getText().toString().trim()).exists()) {
                            maxid = (dataSnapshot.child("Products").child(p_id.getText().toString().trim()).getChildrenCount());
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });
        final Random rand = new Random();
        rand.nextLong();




        rootview.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pD = new ProgressDialog(getActivity());
                pD.setMessage("Saving Data... ");
                pD.show();

                final StorageReference filePath = mStroage.child("ProductPhoto").child(p_id.getText().toString()).child(String.valueOf(rand.nextLong()));;
                if (!p_desc.getText().toString().equals("") && !p_id.getText().toString().equals("") &&
                        !P_name.getText().toString().equals("") && !p_price.getText().toString().equals("")  && !p_cat.getText().toString().equals("")) {

                    filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    photoLink = uri.toString();

                                    products.setId(p_id.getText().toString().trim());
                                    products.setP_name(P_name.getText().toString().trim());
                                    products.setP_price(p_price.getText().toString().trim());
                                    products.setP_cat(p_cat.getText().toString().trim());
                                    products.setP_desc(p_desc.getText().toString().trim());
                                    products.setP_shop(retailer_shop);
                                    products.setP_photo(photoLink);
                                    products.setShopOwner(Emaill);
                                    products.setP_mall(mall_name);
                                    String key = reff.child("Products").push().getKey();
                                    products.setKey(key);

                                    ads.setMall(mall_name);
                                    ads.setPhoto(photoLink);
                                    ads.setOwner(Emaill);
                                    reff.child("Images").child("Products").child(p_id.getText().toString().trim()).child(String.valueOf(maxid + 1)).setValue(ads);

                                    if(disCheck.isChecked())
                                    {
                                        //discount.setVisibility(View.VISIBLE); //should
                                        double dis = Double.parseDouble(discount.getText().toString().trim());
                                        products.setDiscount(Double.toString(dis));
                                        double OrigPrice = Double.parseDouble(p_price.getText().toString().trim());
                                        double afterDis = OrigPrice - (OrigPrice * dis/100);
                                        products.setDisPrice(Double.toString(afterDis));
                                    }
                                    else
                                    {
                                        products.setDisPrice(Double.toString(0));
                                        products.setDiscount(Double.toString(0));


                                    }

                                    if (task.isSuccessful()) {
                                        reff.child("Products").child(key).setValue(products);
                                        Toast.makeText(getActivity(), "The product added successfully", Toast.LENGTH_LONG).show();
                                        pD.dismiss();
                                        getFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.main_ret_container, new RetailerHome(), "RetailerHome")
                                                .addToBackStack("RetailerHome")
                                                .commit();
                                    } else {
                                        Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });

                }
                else {
                    Toast.makeText(getContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
            }
        });

/*
        rootview.findViewById(R.id.Addimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bundle b = new Bundle();
                    b.putString("source", "ProductsADD");
                    b.putString("product", p_id.getText().toString().trim());

                    AddImages fragB = new AddImages();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
*/



        rootview.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getFragmentManager() != null) {
                        RetailerHome fragB = new RetailerHome();
                        getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return rootview;
    }
    private void startGallery () {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }


    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data) {
        //super method removed
        super.onActivityResult(requestCode , resultCode , data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , returnUri);
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
