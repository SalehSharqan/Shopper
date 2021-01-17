package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
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

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.mapwize.mapwizeuicomponents.Adapters.FeedbackAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.ImageAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Listeners.IFirebaseLoadDone;
import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Feedback;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

//public class ShopInfo extends Fragment implements IFirebaseLoadDone
public class ShopInfo extends Fragment {
    public String shopid, shopname;
    CardView products;
    TextView textshop, shop;
    GridLayout grid;
    RecyclerView recyclerView, recyclerView2;
    DatabaseReference reff;
    ArrayList<Products> productsList;
    ArrayList<Feedback> feedbackList;
    Products_Adapter adapter;
    FeedbackAdapter feedbackAdapter;
    DatabaseReference imageRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    int page_position = 1;
    Timer timer;
    // private ViewPager images_slider;
    //  private LinearLayout pages_dots;
    // private TextView[] dots;
    ImageAdapter imageAdapter;
    int SizeOfArray = 0;
    private RatingBar ratingBar;
    private CardView btnSend;
    private EditText content;
    private TextView about, result, shopcat, shopname1;
    DatabaseReference reff2, reff3;
    Feedback feedback;
    Additionals additionals;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String CusEmail;
    public String CusUser, name, photo;
    ImageView img;
    TextView shop2;
    ImageView rePIC, fav;
    public String photo1, name1, cate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.shop_info , container , false);

        Bundle b = this.getArguments();
        shopid = b.getString("shopid");
        shopname = b.getString("shopname");
       // Toast.makeText(getActivity() , shopname + " + " + shopid , Toast.LENGTH_SHORT).show();


        img = rootview.findViewById(R.id.imageView);
        shop2 = rootview.findViewById(R.id.shop);
        shopname1 = rootview.findViewById(R.id.shopname);
        shopcat = rootview.findViewById(R.id.shopcat);
        products = rootview.findViewById(R.id.products);
        textshop = rootview.findViewById(R.id.textshop);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        rePIC = rootview.findViewById(R.id.rePIC);
        about = rootview.findViewById(R.id.about);
        fav = rootview.findViewById(R.id.fav);
        textshop.setVisibility(View.VISIBLE);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext() , DividerItemDecoration.VERTICAL));
        productsList = new ArrayList<Products>();
        feedbackList = new ArrayList<Feedback>();
        reff = FirebaseDatabase.getInstance().getReference().child("Products");
        btnSend = rootview.findViewById(R.id.btnSend);
        ratingBar = rootview.findViewById(R.id.ratingBar);
        content = rootview.findViewById(R.id.content);
      //  result = rootview.findViewById(R.id.result);
        reff2 = FirebaseDatabase.getInstance().getReference().child("Feedback");
        reff3 = FirebaseDatabase.getInstance().getReference();
        feedback = new Feedback();
        additionals = new Additionals();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        CusEmail = user.getEmail();
        shop2.setText(shopname);
        about.setText("About " + shopname);


        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops3 = dataSnapshot1.getValue(Shops.class);
                    if (shopid.equals(shops3.getShopNumber())) {
                        Picasso.get()
                                .load(shops3.getShoplogo())
                                .into(img);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);


                        Picasso.get()
                                .load(shops3.getShoplogo())
                                .into(rePIC);
                        rePIC.setScaleType(ImageView.ScaleType.FIT_XY);

                        shopname1.setText(shops3.getShopName());
                        shopcat.setText(shops3.getShopCategory());
                        name1 = shops3.getShopName();
                        photo1 = shops3.getShoplogo();
                        cate = shops3.getShopCategory();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();

                    Users users = dataSnapshot1.getValue(Users.class);
                    if (users.getUserType().equals("Customer")) {
                        if (users.getC_Email().equals(CusEmail)) {
                          //  Toast.makeText(getActivity() , "2" , Toast.LENGTH_SHORT).show();
                            CusUser = users.getC_Username();
                            name = users.getC_Name();
                            photo = users.getC_Photo();

                         //   Toast.makeText(getActivity() , CusEmail , Toast.LENGTH_SHORT).show();
                          //  Toast.makeText(getActivity() , CusUser , Toast.LENGTH_SHORT).show();

                        }
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
                Log.d("rating" , String.valueOf(ratingBar.getRating()));
                Log.d("isi komentar" , String.valueOf(content.getText()));
             //   result.setText("Rating : " + String.valueOf(ratingBar.getRating()) + "\nComment: " + String.valueOf(content.getText()));
                String rating = String.valueOf(ratingBar.getRating());
                if (!content.getText().equals("")) {
                    feedback.setRating(rating.toString().trim());
                    feedback.setComment(content.getText().toString().trim());
                    feedback.setShop(shopid.toString().trim());
                    feedback.setName(name);
                    feedback.setPhoto(photo);
                    reff2.child("Shops").child(CusUser).setValue(feedback);
                    Toast.makeText(getActivity() , "The Rate and comment added successfully" , Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext() , "Please fill all boxes" , Toast.LENGTH_LONG).show();
                }

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops5 = dataSnapshot1.getValue(Shops.class);
                    if (shopid.equals(shops5.getShopNumber())) {
                   //     Toast.makeText(getActivity() , "1" , Toast.LENGTH_SHORT).show();
                        textshop.setText(shops5.getDesc());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }


        });


        reff3.child("Additionals").child("ShopFavorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(CusUser + shopid)) {
                    additionals.setUser(CusUser);
                    additionals.setFav("no");
                    additionals.setShop(shopid);
                    additionals.setType("Shop");
                    additionals.setCate(cate);
                    additionals.setName(name1);
                    additionals.setPhoto(photo1);
                    reff3.child("Additionals").child("ShopFavorites").child(CusUser + shopid).setValue(additionals);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();

            }
        });


        reff3.child("Additionals").child("ShopFavorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                    if (shopname.equals(additionals1.getShop())) {
                        if (additionals1.getFav().equals("no")) {
                            fav.setImageResource(R.drawable.h);
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
                reff3.child("Additionals").child("ShopFavorites").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Additionals additionals1 = dataSnapshot1.getValue(Additionals.class);
                            if (shopid.equals(additionals1.getShop())) {
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


        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("shopname2" , shopname);
                ProductsList fragB = new ProductsList();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container , fragB).commit();
            }
        });


        reff2.child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Feedback feedback = dataSnapshot1.getValue(Feedback.class);
                    if (feedback.getShop().equals(shopid)) {
                        feedbackList.add(feedback);
                    }
                }

                feedbackAdapter = new FeedbackAdapter(getActivity() , feedbackList , "Customer");
                recyclerView.setAdapter(feedbackAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity() , "Opsss.... Something is wrong" , Toast.LENGTH_SHORT).show();
            }
        });


        imageRef = FirebaseDatabase.getInstance().getReference().child("Images").child("Shops").child(shopid);
        //iFirebaseLoadDone = this;

        timer = new Timer();
        // loadImage();
        //scheduleSlider();

        //images_slider.setPageTransformer(true, new DepthPageTransformer());
        return rootview;
    }


/*


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
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

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
        pages_dots.setPadding(0, 0, 0, 20);
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#9f9f9f")); // un selected
            pages_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#2f383a")); // selected
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

*/

}
