package io.mapwize.mapwizeuicomponents.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    TextInputEditText email, username, password, name, shop_num,
    ret_shop, ret_password, ret_user, ret_email, ret_name, desc;
    DatabaseReference reff;
    DatabaseReference reff2;
    private FirebaseAuth firebaseAuth;
    AutoCompleteTextView ret_mall, ret_cat;
    Users users;
    Shops shops;
    LinearLayout cus, ret, malin;
    TextView txt;
    CircleImageView pic, logo;
    private static final int GALLERY_INTENT = 2;
    private StorageReference mStroage;
    private DatabaseReference mDatareff;
    String photoLink;
   public String key2;
    public Uri ufff;
    String user;
    boolean usert = true;
    private ProgressDialog pD;
    private String[] Malls = {"East Coast Mall","Berjaya Megamall", "Kuantan City Mall"};
    private String[] Categories = {"Fashion", "Mobiles & Electronics", "Home & Living", "Daily Needs", "Sports", "Baby & Kids", "Books", "Others"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cus = findViewById(R.id.cus);
        ret = findViewById(R.id.ret);
        malin = findViewById(R.id.malin);
     //   reg_with = findViewById(R.id.reg_with);
        email = findViewById(R.id.user_email);
       // txt = findViewById(R.id.txt);
        password = findViewById(R.id.user_password);
        username = findViewById(R.id.user_name);
        name = findViewById(R.id.name);
        pic = findViewById(R.id.pic);
        logo = findViewById(R.id.logo);
        desc = findViewById(R.id.desc);

        ret_user = findViewById(R.id.ret_user);
        ret_email = findViewById(R.id.ret_email);
        ret_name =findViewById(R.id.ret_name);
        ret_cat =findViewById(R.id.ret_cat);
        ret_password =findViewById(R.id.ret_password);
        ret_shop = findViewById(R.id.ret_shop);
        ret_mall = findViewById(R.id.ret_mall);
        shop_num = findViewById(R.id.shop_num);
        mStroage = FirebaseStorage.getInstance().getReference();
        reff = FirebaseDatabase.getInstance().getReference();
        reff2 = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        users = new Users();
        shops = new Shops();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Malls);
        ret_mall.setThreshold(1);
        ret_mall.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, Categories);
        ret_cat.setThreshold(1);
        ret_cat.setAdapter(adapter2);



        Intent intent = getIntent();
        user = intent.getStringExtra("regType");
            if (user.equals("Customer")) {
                cus.setVisibility(View.VISIBLE);
                ret.setVisibility(View.GONE);
                malin.setVisibility(View.GONE);
            } else {
                ret.setVisibility(View.VISIBLE);
                cus.setVisibility(View.GONE);
                malin.setVisibility(View.GONE);
               // reg_with.setVisibility(View.GONE);
//                txt.setVisibility(View.GONE);
            }


        findViewById(R.id.pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usert = true;
                startGallery();
            }
        });

        findViewById(R.id.logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usert = false;
                startGallery();
            }
        });

        final Random rand = new Random();
        rand.nextLong();

        if (user.equals("Customer")) {
          findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final StorageReference filePath = mStroage.child("ProfilePhoto").child(email.getText().toString()).child(String.valueOf(rand.nextLong()));;
                    if (!email.getText().toString().equals("") && !password.getText().toString().equals("") &&
                            !name.getText().toString().equals("") && !username.getText().toString().equals("")) {

                        filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        photoLink = uri.toString();


                                    /*    customer.setName(username.getText().toString().trim());
                                        customer.setName(name.getText().toString().trim());
                                        customer.setPassword(password.getText().toString().trim());
                                        customer.setEmail(email.getText().toString().trim());
                                        customer.setPhoto(photoLink);*/
                                        users.setC_Username(username.getText().toString().trim());
                                        users.setC_Name(name.getText().toString().trim());
                                        users.setC_Password(password.getText().toString().trim());
                                        users.setC_Email(email.getText().toString().trim());
                                        users.setC_Photo(photoLink);
                                        users.setUserType(user);
                                            users.setC_mall("East Coast Mall");
                                        String key = reff.child("Users").push().getKey();
                                        users.setKey(key);


                                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
//                                                    reff.child("Customer").push().setValue(customer);
                                                    reff.child("Users").child(key).setValue(users);
                                                    Toast.makeText(Register.this, "Your Account has been created Successfully", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Register.this, Login.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Please fill all boxes", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
          findViewById(R.id.register_btnnext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final StorageReference filePath = mStroage.child("ProfilePhoto").child(ret_email.getText().toString()).child(String.valueOf(rand.nextLong()));
                    if (!ret_email.getText().toString().equals("") && !ret_password.getText().toString().equals("") &&
                            !ret_name.getText().toString().equals("") && !ret_user.getText().toString().equals("")) {
                        pD = new ProgressDialog(Register.this);
                        pD.setMessage("Signing Up ... ");
                        pD.show();

                        filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        photoLink = uri.toString();
                                        users.setR_Email(ret_email.getText().toString().trim());
                                        users.setR_Name(ret_name.getText().toString().trim());
                                        users.setR_Username(ret_user.getText().toString().trim());
                                        users.setR_Password(ret_password.getText().toString().trim());
                                        users.setR_Status("Pending");
                                        users.setR_Photo(photoLink);
                                        users.setUserType(user);
                                        key2 = reff.child("Users").push().getKey();
                                        users.setKey(key2);
                                        users.setR_Mall("empty");
                                 /*       shops.setOwnerEmail(ret_email.getText().toString().trim());
                                        shops.setMallName(ret_mall.getText().toString().trim());
                                        shops.setShopCategory(ret_cat.getText().toString().trim());
                                        shops.setShopName(ret_shop.getText().toString().trim());
                                        shops.setShopNumber(shop_num.getText().toString().trim());*/

                                        firebaseAuth.createUserWithEmailAndPassword(ret_email.getText().toString().trim() , ret_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                    reff.child("Users").child(key2).setValue(users);
                                                    String mall = ret_mall.getText().toString().trim();
                                                   // reff2.child("Shops").child(key2).setValue(shops);
                                                    /*Toast.makeText(Register.this, "Your Account has been created Successfully", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Register.this, Login.class);
                                                    startActivity(intent);*/
                                                Toast.makeText(Register.this, "Your account has been created successfully, please enter your shop information", Toast.LENGTH_LONG).show();

                                                ret.setVisibility(View.GONE);
                                                    malin.setVisibility(View.VISIBLE);
                                                    pic.setVisibility(View.GONE);
                                                pD.dismiss();

                                    }
                                });
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Please fill all boxes", Toast.LENGTH_LONG).show();
                    }
                }
            });

            findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pD = new ProgressDialog(Register.this);
                    pD.setMessage("Saving Data ... ");
                    pD.show();
                    try {
                        final StorageReference filePath = mStroage.child("ShopsPhotos").child(shop_num.getText().toString()).child(String.valueOf(rand.nextLong()));
                        if (!ret_mall.getText().toString().equals("") && !ret_shop.getText().toString().equals("") &&
                                !shop_num.getText().toString().equals("") && !ret_cat.getText().toString().equals("") && !desc.getText().toString().equals("")) {


                            filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            photoLink = uri.toString();

                                        shops.setOwnerEmail(ret_email.getText().toString().trim());
                                        shops.setMallName(ret_mall.getText().toString().trim());
                                        shops.setShopCategory(ret_cat.getText().toString().trim());
                                        shops.setShopName(ret_shop.getText().toString().trim());
                                        shops.setShopNumber(shop_num.getText().toString().trim());
                                        shops.setDesc(desc.getText().toString().trim());
                                        shops.setShoplogo(photoLink);

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(key2).child("r_Mall");
                                            databaseReference.setValue(ret_mall.getText().toString().trim());
                                            String key3 = reff.child("Shops").push().getKey();
                                            shops.setKey(key3);


                                                    if (task.isSuccessful()) {
                                                        reff2.child("Shops").child(key3).setValue(shops);

                                                    Toast.makeText(Register.this, "Your shop has been saved successfully", Toast.LENGTH_LONG).show();
                                                        pD.dismiss();
                                                        Intent intent = new Intent(Register.this, Login.class);
                                                    startActivity(intent);

                                                    } else {
                                                        Toast.makeText(Register.this , task.getException().toString() , Toast.LENGTH_LONG).show();
                                                    }

                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(Register.this , "Please fill all boxes" , Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                });
            findViewById(R.id.pre).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ret.setVisibility(View.VISIBLE);
                        malin.setVisibility(View.GONE);
                        pic.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });



        }




        findViewById(R.id.have_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getFragmentManager() != null) {
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });





    }

    private void startGallery () {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(Register.this.getPackageManager()) != null) {
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
                    bitmapImage = MediaStore.Images.Media.getBitmap(Register.this.getContentResolver() , returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pic.setImageBitmap(bitmapImage);
                if (usert == true) {
                    Picasso.get().load(returnUri).into(pic);
                }
                else {
                    Picasso.get().load(returnUri).into(logo);
                }
                    ufff = returnUri;
            }
        }

    }

    }



