package io.mapwize.mapwizeuicomponents.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.FeedbackAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.ImageAdapter;
import io.mapwize.mapwizeuicomponents.Listeners.DepthPageTransformer;
import io.mapwize.mapwizeuicomponents.Listeners.IFirebaseLoadDone;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Feedback;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ProductInfo extends Fragment implements IFirebaseLoadDone {
    private ViewPager images_slider;
    private TextView[] dots;
    int page_position = 1;
    Timer timer;
    private LinearLayout pages_dots, linpro;
    private CardView rate, wish, comment, pro_about, btnSend;
    String productid,shopname;
    ImageAdapter imageAdapter;
    DatabaseReference proRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    int SizeOfArray = 0;
    DatabaseReference imageRef, reff2, reff3;
    TextView viewTitle, Price, viewPrice, gstLabel, item_size, viewUser, viewShop, content;
    DatabaseReference reff;
    ImageView retailerProfilePic, fav;
    private RatingBar ratingBar;
    Feedback feedback;
    Additionals additionals;
    ArrayList<Feedback> feedbackList;
    public String CusUser, CusEmail, name, photo, mall_name;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerView;
    FeedbackAdapter feedbackAdapter;
    Boolean flag=false;
    public String photo1,name1, cate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.product_info, container, false);

        Bundle b = this.getArguments();
        productid = b.getString("productid");
        shopname = b.getString("shop");
       // Toast.makeText(getActivity(), productid, Toast.LENGTH_SHORT).show();


        images_slider = rootview.findViewById(R.id.slid);
        pages_dots = rootview.findViewById(R.id.image_page_dots);
        viewTitle = rootview.findViewById(R.id.viewTitle);
        Price = rootview.findViewById(R.id.viewDiscountedPrice);
        gstLabel = rootview.findViewById(R.id.gstLabel);
        viewPrice = rootview.findViewById(R.id.viewPrice);
        item_size = rootview.findViewById(R.id.item_size);
        retailerProfilePic = rootview.findViewById(R.id.retailerProfilePic);
        viewShop = rootview.findViewById(R.id.viewShop);
        viewUser = rootview.findViewById(R.id.viewUser);
        btnSend = rootview.findViewById(R.id.btnSend);
        ratingBar = (RatingBar) rootview.findViewById(R.id.ratingBar);
        content = (EditText) rootview.findViewById(R.id.content);
        fav = rootview.findViewById(R.id.fav);
        additionals = new Additionals();
        reff3 = FirebaseDatabase.getInstance().getReference();
        feedbackList = new ArrayList<Feedback>();
        feedback = new Feedback();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        CusEmail = user.getEmail();
        reff2 = FirebaseDatabase.getInstance().getReference().child("Feedback");
        recyclerView = rootview.findViewById(R.id.item_review_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));



        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Customer")) {
                        if (users.getC_Email().equals(CusEmail)) {
                            CusUser = users.getC_Username();
                            name = users.getC_Name();
                            photo = users.getC_Photo();
                       //     Toast.makeText(getActivity(), CusEmail, Toast.LENGTH_SHORT).show();

                         //   Toast.makeText(getActivity(), CusUser, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });


        reff2.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Feedback feedback = dataSnapshot1.getValue(Feedback.class);
                    if (feedback.getProduct().equals(productid)) {
                        feedbackList.add(feedback);

                    }
                }

                feedbackAdapter = new FeedbackAdapter(getActivity(), feedbackList, "Customer");
                recyclerView.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if(productid.equals(products.getId())) {
                       viewTitle.setText(products.getP_name());
                       Price.setText("RM" + products.getDisPrice());
                       viewPrice.setText("RM" + products.getP_price());
                        viewPrice.setPaintFlags(viewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        gstLabel.setText("-" + products.getDiscount());
                        item_size.setText(products.getP_desc());
                        name1 = products.getP_name();
                        photo1 = products.getP_photo();
                        cate = products.getP_cat();
                        mall_name = products.getP_mall();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops = dataSnapshot1.getValue(Shops.class);
                    if(shopname.equals(shops.getShopName())) {
                        Picasso.get()
                                .load(shops.getShoplogo())
                                .into(retailerProfilePic);
                        viewShop.setText(shops.getShopName());
                        viewUser.setText(shops.getShopCategory());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("rating",String.valueOf(ratingBar.getRating()));
                Log.d("isi komentar", String.valueOf(content.getText()));
                // result.setText("Rating : "+ String.valueOf(ratingBar.getRating())+"\nComment: " +String.valueOf(content.getText()));
                String rating = String.valueOf(ratingBar.getRating());
                if (!content.getText().equals("")){
                    feedback.setRating(rating.toString().trim());
                    feedback.setComment(content.getText().toString().trim());
                    feedback.setProduct(productid);
                    feedback.setName(name);
                    feedback.setPhoto(photo);
                    reff2.child("Products").child(CusUser).setValue(feedback);
                    Toast.makeText(getActivity(), "The Rate and comment added successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }

            }
        });


        reff3.child("Additionals").child("ProductsFavorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(CusUser+productid)) {
                    additionals.setUser(CusUser);
                    additionals.setFav("no");
                    additionals.setProducid(productid);
                    additionals.setType("Product");
                    additionals.setCate(cate);
                    additionals.setName(name1);
                    additionals.setPhoto(photo1);
                    additionals.setShop(shopname);
                    additionals.setP_mall(mall_name);
                    reff3.child("Additionals").child("ProductsFavorites").child(CusUser+productid).setValue(additionals);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        reff3.child("Additionals").child("ProductsFavorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                    if (productid.equals(additionals1.getProducid())) {
                        if (additionals1.getFav().equals("no")) {
                            fav.setImageResource(R.drawable.h3);
                        } else {
                            fav.setImageResource(R.drawable.h1);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff3.child("Additionals").child("ProductsFavorites").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                            if (productid.equals(additionals1.getProducid())) {
                                if (additionals1.getFav().equals("no")) {
                                    fav.setImageResource(R.drawable.h1);
                                    DatabaseReference numMesasReference = dataSnapshot1.getRef().child("fav");
                                    numMesasReference.setValue("yes");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
                    }


                });

            }
        });


        imageRef = FirebaseDatabase.getInstance().getReference().child("Images").child("Products").child(productid);
        iFirebaseLoadDone = this;

        timer = new Timer();
        loadImage();
        scheduleSlider();

        images_slider.setPageTransformer(true, new DepthPageTransformer());



        return rootview;
    }


    @Override
    public void onFirebaseLoadSuccess(List<Ads> AdsList) {
        imageAdapter = new ImageAdapter(getActivity(), AdsList);
        images_slider.setAdapter(imageAdapter);
        SizeOfArray = AdsList.size();
        images_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    @Override
    public void OnFirebaseLoadFail(String message) {
        Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

    }


    private void loadImage() {
        addBottomDots(0);
        imageRef.addValueEventListener(new ValueEventListener() {
            List<Ads> AdsList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot adsSnapshot : dataSnapshot.getChildren()) {
                    AdsList.add(adsSnapshot.getValue(Ads.class));
                    iFirebaseLoadDone.onFirebaseLoadSuccess(AdsList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.OnFirebaseLoadFail(databaseError.getMessage());
            }
        });
    }


    public void scheduleSlider() {

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == SizeOfArray) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                images_slider.setCurrentItem(page_position, true);
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 4000);
    }



    public void addBottomDots(int currentPage) {
        dots = new TextView[SizeOfArray];

        pages_dots.removeAllViews();
        pages_dots.setPadding(0 , 0 , 0 , 20);
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9135;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#9f9f9f")); // un selected
            pages_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#31176a")); // selected
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }


}
