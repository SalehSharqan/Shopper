package io.mapwize.mapwizeuicomponents.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Adapters.ImageAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Listeners.DepthPageTransformer;
import io.mapwize.mapwizeuicomponents.Listeners.IFirebaseLoadDone;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class ShopInfo extends Fragment implements IFirebaseLoadDone {
    public String shopid, shopname;
    Button comments, products, aboutShop;
    TextView textshop, shop;
    GridLayout grid;
    RecyclerView recyclerView;
    DatabaseReference reff;
    ArrayList<Products> productsList;
    Products_Adapter adapter;
    DatabaseReference imageRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    int page_position = 1;
    Timer timer;
    private ViewPager images_slider;
    private LinearLayout pages_dots;
    private TextView[] dots;
    ImageAdapter imageAdapter;
    int SizeOfArray = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.shop_info, container, false);
        Bundle b = this.getArguments();
        shopid = b.getString("shopid");
        shopname = b.getString("shopname");
        Toast.makeText(getActivity(), shopname + " + " + shopid, Toast.LENGTH_SHORT).show();

        images_slider = rootview.findViewById(R.id.slid);
        pages_dots = rootview.findViewById(R.id.image_page_dots);
        comments = rootview.findViewById(R.id.comments);
        aboutShop = rootview.findViewById(R.id.aboutShop);
        products = rootview.findViewById(R.id.products);
        textshop = rootview.findViewById(R.id.textshop);
//        shop = rootview.findViewById(R.id.shop);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        textshop.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productsList = new ArrayList<Products>();
        reff = FirebaseDatabase.getInstance().getReference().child("Products");
//        shop.setText(shopid);


        aboutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                textshop.setVisibility(View.VISIBLE);
                textshop.setText("Shop Description from Firebase");
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textshop.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Products products = dataSnapshot1.getValue(Products.class);
                            if (products.getP_shop().equals(shopname)) {//fix later to id not shopname..
                                productsList.add(products);
                            }
                        }

                        adapter = new Products_Adapter(getActivity(), productsList, "Customer");
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



  /*
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        shop_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             try {
                 Bundle bundle = new Bundle();
                 bundle.putString("shopname", shopname);
                 ProductsList fragB = new ProductsList();
                 fragB.setArguments(bundle);
                 getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });*/


        imageRef = FirebaseDatabase.getInstance().getReference().child("Images").child("Shops").child(shopid);
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


}
