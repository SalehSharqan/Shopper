package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    EditText email, username, password, name, shop_num,
    ret_shop, ret_mall, ret_password, ret_user, ret_email, ret_name, ret_cat;
    DatabaseReference reff;
    DatabaseReference reff2;
    private FirebaseAuth firebaseAuth;
/*    Customer customer;
    Retailer retailer;*/
    Users users;
    Shops shops;
    LinearLayout cus, ret, reg_with;
    TextView txt;
    CircleImageView pic;
    private static final int GALLERY_INTENT = 2;
    private StorageReference mStroage;
    private DatabaseReference mDatareff;
    String photoLink;
    public Uri ufff;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cus = findViewById(R.id.cus);
        ret = findViewById(R.id.ret);
        reg_with = findViewById(R.id.reg_with);
        email = findViewById(R.id.user_email);
        txt = findViewById(R.id.txt);
        password = findViewById(R.id.user_password);
        username = findViewById(R.id.user_name);
        name = findViewById(R.id.name);
        pic = findViewById(R.id.pic);
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
 /*       customer = new Customer();
        retailer = new Retailer();*/
        users = new Users();
        shops = new Shops();


        Intent intent = getIntent();
        user = intent.getStringExtra("regType");
            if (user.equals("Customer")) {
                cus.setVisibility(View.VISIBLE);
                ret.setVisibility(View.GONE);
            } else {
                ret.setVisibility(View.VISIBLE);
                cus.setVisibility(View.GONE);
                reg_with.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
            }


        findViewById(R.id.add_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        users.setC_Username(password.getText().toString().trim());
                                        users.setC_Email(email.getText().toString().trim());
                                        users.setC_Photo(photoLink);
                                        users.setUserType(user);


                                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
//                                                    reff.child("Customer").push().setValue(customer);
                                                    reff.child("Users").push().setValue(users);
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
          findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final StorageReference filePath = mStroage.child("ProfilePhoto").child(ret_email.getText().toString()).child(String.valueOf(rand.nextLong()));
                    if (!ret_email.getText().toString().equals("") && !ret_password.getText().toString().equals("") &&
                            !ret_name.getText().toString().equals("") && !ret_user.getText().toString().equals("") &&
                            !ret_mall.getText().toString().equals("") && !ret_shop.getText().toString().equals("") &&
                            !shop_num.getText().toString().equals("")  && !ret_cat.getText().toString().equals("")) {

                        filePath.putFile(ufff).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        photoLink = uri.toString();

                                /*        retailer.setRet_email(ret_email.getText().toString().trim());
                                        retailer.setRet_name(ret_name.getText().toString().trim());
                                        retailer.setRet_user(ret_user.getText().toString().trim());
                                        retailer.setRet_password(ret_password.getText().toString().trim());
                                        retailer.setRet_mall(ret_mall.getText().toString().trim());
                                        retailer.setRet_cat(ret_cat.getText().toString().trim());
                                        retailer.setRet_shop(ret_shop.getText().toString().trim());
                                        retailer.setShop_num(shop_num.getText().toString().trim());
                                        retailer.setShop_photo(photoLink);*/
                                        users.setR_Email(ret_email.getText().toString().trim());
                                        users.setR_Name(ret_name.getText().toString().trim());
                                        users.setR_Username(ret_user.getText().toString().trim());
                                        users.setR_Password(ret_password.getText().toString().trim());
                                        users.setR_Status("Pending");
                                        users.setR_Photo(photoLink);
                                        users.setUserType(user);
                                        shops.setOwnerEmail(ret_email.getText().toString().trim());
                                        shops.setMallName(ret_mall.getText().toString().trim());
                                        shops.setShopCategory(ret_cat.getText().toString().trim());
                                        shops.setShopName(ret_shop.getText().toString().trim());
                                        shops.setShopNumber(shop_num.getText().toString().trim());


                                        firebaseAuth.createUserWithEmailAndPassword(ret_email.getText().toString().trim(), ret_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    reff.child("Users").push().setValue(users);
                                                    String mall = ret_mall.getText().toString().trim();
                                                    reff2.child("Shops").push().setValue(shops);
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


        findViewById(R.id.fb_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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




    }

    private void startGallery () {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(Register.this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(Register.this.getContentResolver(), returnUri);
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

