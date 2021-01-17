package io.mapwize.mapwizeuicomponents.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Activities.Main;
import io.mapwize.mapwizeuicomponents.Adapters.ImageAdapter;
import io.mapwize.mapwizeuicomponents.Listeners.DepthPageTransformer;
import io.mapwize.mapwizeuicomponents.Listeners.IFirebaseLoadDone;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements IFirebaseLoadDone {
    private TextView mall;
    private DatabaseReference reff;
    int page_position = 1;
    Timer timer;
    String Emaill;
    private ViewPager images_slider;
    private LinearLayout pages_dots;
    private TextView[] dots;
    CircleImageView ChangeMall;
    String s;
    ImageAdapter imageAdapter;
    DatabaseReference adsRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    int SizeOfArray = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);


        images_slider = rootview.findViewById(R.id.slid);
        pages_dots = rootview.findViewById(R.id.image_page_dots);
        ChangeMall = rootview.findViewById(R.id.change);
        mall = rootview.findViewById(R.id.Mall);

        ChangeMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_fragment_container, new MallsFragment(), "MallsFragment")
                            .addToBackStack("MallsFragment")
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        s = "East Coast Mall";
        Bundle b = this.getArguments();
        if (b != null) {
            s = b.getString("mall");
            mall.setText(s);
            ((Main) getActivity()).setmallname(s);
            if (s.equals("Berjaya Megamall")) {
                ChangeMall.setImageResource(R.drawable.megalogo);
            } else if (s.equals("Kuantan City Mall")) {
                ChangeMall.setImageResource(R.drawable.kuantanlogo);
            } else {
                ChangeMall.setImageResource(R.drawable.eastlogo);
            }
        }

        adsRef = FirebaseDatabase.getInstance().getReference().child("Images").child("Offers").child(s);
        iFirebaseLoadDone = this;

        timer = new Timer();
        loadAds();
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
