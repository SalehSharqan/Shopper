package io.mapwize.mapwizeuicomponents.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Activities.Main;
import io.mapwize.mapwizeuicomponents.Activities.MySuggestionProvider;
import io.mapwize.mapwizeuicomponents.Activities.searchable;
import io.mapwize.mapwizeuicomponents.Adapters.ImageAdapter;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Listeners.DepthPageTransformer;
import io.mapwize.mapwizeuicomponents.Listeners.IFirebaseLoadDone;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements IFirebaseLoadDone {
   //private TextView mall;
    private DatabaseReference reff;
    int page_position = 1;
    Timer timer;
    String Emaill;
    private ViewPager images_slider;
    private LinearLayout pages_dots;
    private TextView[] dots;
    //CircleImageView ChangeMall;
    ImageAdapter imageAdapter;
    DatabaseReference adsRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    int SizeOfArray = 0;
    CircleImageView change, change2, change3, change4, change5, change6, change7, change8;
    RecyclerView recyclerView;
    DatabaseReference reff2;
    ArrayList<Products> productsList;
    Products_Adapter adapter;
    SearchView searchView;
    Bundle bundle = new Bundle();
    Button picssearch;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String useremail, mallname;
    TextView tname, tname2, tname3, tname4, tprice, tprice2, tprice3, tprice4;
    ImageView tpic, tpic2, tpic3, tpic4;
  public  int num = 1;
  CardView c, c4, c2, c3;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home , container , false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        useremail = user.getEmail();
        images_slider = rootview.findViewById(R.id.slid);
        pages_dots = rootview.findViewById(R.id.image_page_dots);
        View includedLayout = rootview.findViewById(R.id.catlayout);
        View includedLayout1 = rootview.findViewById(R.id.playout);

        ((Main)getActivity()).shownav();
        picssearch = rootview.findViewById(R.id.picsearch);
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if(users.getUserType().equals("Customer"))
                    {
                        if (users.getC_Email().equals(useremail)) {
                            mallname = users.getC_mall();
                            therest(mallname);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        change = includedLayout.findViewById(R.id.change);
        change2 = includedLayout.findViewById(R.id.change2);
        change3 = includedLayout.findViewById(R.id.change3);
        change4 = includedLayout.findViewById(R.id.change4);
        change5 = includedLayout.findViewById(R.id.change5);
        change6 = includedLayout.findViewById(R.id.change6);
        change7 = includedLayout.findViewById(R.id.change7);
        change8 = includedLayout.findViewById(R.id.change8);
        change8 = includedLayout.findViewById(R.id.change8);

        tpic = includedLayout1.findViewById(R.id.tpic);
        tpic2 = includedLayout1.findViewById(R.id.tpic2);
        tpic3 = includedLayout1.findViewById(R.id.tpic3);
        tpic4 = includedLayout1.findViewById(R.id.tpic4);

        tname = includedLayout1.findViewById(R.id.tname);
        tname2 = includedLayout1.findViewById(R.id.tname2);
        tname3 = includedLayout1.findViewById(R.id.tname3);
        tname4 = includedLayout1.findViewById(R.id.tname4);

        tprice = includedLayout1.findViewById(R.id.tprice);
        tprice2 = includedLayout1.findViewById(R.id.tprice2);
        tprice3 = includedLayout1.findViewById(R.id.tprice3);
        tprice4 = includedLayout1.findViewById(R.id.tprice4);

        c = includedLayout1.findViewById(R.id.c);
        c2 = includedLayout1.findViewById(R.id.c2);
        c3 = includedLayout1.findViewById(R.id.c3);
        c4 = includedLayout1.findViewById(R.id.c4);



        searchView = rootview.findViewById(R.id.searchView);
        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        productsList = new ArrayList<Products>();
        reff  = FirebaseDatabase.getInstance().getReference().child("Products");

        return rootview;

    }

    public void therest(String mallname)
    {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if (products.getP_mall().equals(mallname)) {
                        productsList.add(products);
                    }
                }
                adapter = new Products_Adapter(getContext(), productsList,"Customer");
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Products products = dataSnapshot1.getValue(Products.class);
                    if (products.getP_mall().equals(mallname)) {
                        if (num == 1)
                        {
                            Picasso.get()
                                    .load(products.getP_photo())
                                    .into(tpic);
                            tname.setText(products.getP_name().trim());;
                            tprice.setText("RM " + products.getDisPrice().trim());
                            c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("productid", products.getId().trim());
                                    bundle.putString("shop", products.getP_shop().trim());
                                    ProductInfo fragB = new ProductInfo();
                                    fragB.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                                }
                            });

                        }
                        else if (num==2)
                        {
                            Picasso.get()
                                    .load(products.getP_photo())
                                    .into(tpic2);
                            tname2.setText(products.getP_name().trim());;
                            tprice2.setText("RM " + products.getDisPrice().trim());
                            c2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("productid", products.getId().trim());
                                    bundle.putString("shop", products.getP_shop().trim());
                                    ProductInfo fragB = new ProductInfo();
                                    fragB.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                                }
                            });
                        }
                        else if (num==3)
                        {
                            Picasso.get()
                                    .load(products.getP_photo())
                                    .into(tpic3);
                            tname3.setText(products.getP_name().trim());;
                            tprice3.setText("RM " + products.getDisPrice().trim());
                            c3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("productid", products.getId().trim());
                                    bundle.putString("shop", products.getP_shop().trim());
                                    ProductInfo fragB = new ProductInfo();
                                    fragB.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                                }
                            });
                        }
                        else if (num==4)
                        {
                            Picasso.get()
                                    .load(products.getP_photo())
                                    .into(tpic4);
                            tname4.setText(products.getP_name().trim());;
                            tprice4.setText("RM " + products.getDisPrice().trim());
                            c4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("productid", products.getId().trim());
                                    bundle.putString("shop", products.getP_shop().trim());
                                    ProductInfo fragB = new ProductInfo();
                                    fragB.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                                }
                            });
                        }
                        num++;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setVisibility(View.VISIBLE);
                if(productsList.contains(query)){
                    recyclerView.setVisibility(View.GONE);

                    //adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(getContext(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                if (newText.equals(""))
                {
                    recyclerView.setVisibility(View.GONE);
                }

                return false;
            }
        });



        picssearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotFragment fragB = new HotFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
            }
        });


        adsRef = FirebaseDatabase.getInstance().getReference().child("Images").child("Offers").child(mallname);
        iFirebaseLoadDone = this;

        timer = new Timer();
        loadAds();
        scheduleSlider();
        images_slider.setPageTransformer(true , new DepthPageTransformer());

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Fashion");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Mobiles & Electronics");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Home & Living");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Daily Needs");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Sports");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Baby & Kids");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Books");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });
        change8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("cat", "Others");
                bundle.putString("mall", mallname);
                ProductsByCategory fragB = new ProductsByCategory();
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB, "ProductsByCategory").commit();
            }
        });

    }

    @Override
    public void onFirebaseLoadSuccess(List<Ads> AdsList) {
        imageAdapter = new ImageAdapter(getActivity() , AdsList);
        images_slider.setAdapter(imageAdapter);
        SizeOfArray = AdsList.size();
        images_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position , float positionOffset , int positionOffsetPixels) {

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


    private void loadAds() {
        addBottomDots(0);

        adsRef.addValueEventListener(new ValueEventListener() {
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
                images_slider.setCurrentItem(page_position , true);
            }
        };

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        } , 500 , 4000);
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