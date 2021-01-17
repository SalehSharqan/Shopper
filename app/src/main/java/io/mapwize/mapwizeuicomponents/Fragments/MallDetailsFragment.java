package io.mapwize.mapwizeuicomponents.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import java.util.Timer;

import androidx.fragment.app.Fragment;

public class MallDetailsFragment extends Fragment {
    TextView Mall;
    private TextView[] dots;
    Timer timer;
    ImageView img, grab, uber, map;

    CardView shops;
    TextView aboutmall,timing,rates, about, facilities;
    LinearLayout lin;
    Bundle bundle = new Bundle();


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String useremail, mallname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_mall_details, container, false);
/*
        Bundle b = this.getArguments();
        mall = b.getString("mallname");*/
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        useremail = user.getEmail();

        img = rootview.findViewById(R.id.imageView);
        Mall = rootview.findViewById(R.id.Mall);
       // text = rootview.findViewById(R.id.text);
        timing = rootview.findViewById(R.id.timing);
        rates = rootview.findViewById(R.id.rates);
        aboutmall = rootview.findViewById(R.id.aboutmall);
       // directions = rootview.findViewById(R.id.directions);
        grab = rootview.findViewById(R.id.grab);
        uber = rootview.findViewById(R.id.uber);
        map = rootview.findViewById(R.id.map);
        facilities = rootview.findViewById(R.id.facilities);
        about = rootview.findViewById(R.id.about);
        shops = rootview.findViewById(R.id.shops);
       // text.setVisibility(View.VISIBLE);

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
                         //   Toast.makeText(getActivity() , mallname + " + " + useremail , Toast.LENGTH_SHORT).show();
                            showmall(mallname);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });





        return rootview;
    }


    @SuppressLint("SetTextI18n")
    public void showmall(String mallname2)
    {
       // Toast.makeText(getActivity(), mallname2, Toast.LENGTH_SHORT).show();
        Mall.setText(mallname2);
        if (mallname2.equals("East Coast Mall")) {
            about.setText("About East Coast Mall");
            aboutmall.setText("East Coast Mall is the preferred destination mall for tourists and locals alike with an established mix of domestic and international retailers, from fashion, entertainment, digital to local delicacies and international gourmets which located in the heart of Kuantan’s City Centre in Pahang, a rapidly growing and modern city with rich history and beautiful sights.\n");
            img.setImageResource(R.drawable.ecm2);
            timing.setText("Everyday:" +
                    "Opening times\n" +
                    "10:00 AM - 10:00 PM");
            rates.setText("All day (12.00am to 11.59pm)\n" +
                    "\n" +
                    "First 3 Hours: RM2.00\n" +
                    "\n" +
                    "Subsequent 1 Hour or Part thereof: RM1.00\n" +
                    "\n" +
                    "Subject to maximum/per day:RM6.00\n" +
                    "\n" +
                    "Overnight Parking:RM20.00\n" +
                    "\n" +
                    "Lost Ticket (Including parking fee):RM22.00\n" +
                    "\n" +
                    "Grace Period 15 minutes");
            facilities.setText("Inquiries\n" +
                    "Directions\n" +
                    "Toilets & Child Facilities\n" +
                    "Lost Property\n" +
                    "ATM's\n" +
                    "Wheelchairs & Baby Strollers \n" +
                    "Storage of Shopping Items and Umbrellas\n" +
                    "Gift Wrapping\n");

        } else if (mallname2.equals("Kuantan City Mall")) {
            about.setText("About Kuantan City Mall");

            aboutmall.setText("Kuantan City Mall is a brand new shopping centre strategically located in the new commercial hub of Kuantan, comprising all the shopping, dining, play and entertainment experience that you can spend quality time with family. Truly an excitement place in all ways that you don’t want to miss!\n" +
                    "\n" +
                    "Kuantan City Mall is a 7 storey huge shopping centre with 1,300 car parking lots, ample for those who prefer to drive to this fun-filled mall, and approximately 200 retail shops with 468,000 square feet rentable area. It is situated within the commercial area that is fully developed with office buildings, hotels and shop lots along Jalan Putra Square 6, Putra Square. This is the latest shopping centre at east coast Malaysia that offers great experiences beyond shopping for you and your family!");

            img.setImageResource(R.drawable.kauntan);
            timing.setText("Everyday:" +
                    "Opening times\n" +
                    "10:00 AM - 10:00 PM");
            rates.setText("All day (12.00am to 11.59pm)\n" +
                    "\n" +
                    "First 3 Hours: RM2.00\n" +
                    "\n" +
                    "Subsequent 1 Hour or Part thereof: RM1.00\n" +
                    "\n" +
                    "Subject to maximum/per day:RM6.00\n" +
                    "\n" +
                    "Overnight Parking:RM20.00\n" +
                    "\n" +
                    "Lost Ticket (Including parking fee):RM22.00\n" +
                    "\n" +
                    "Grace Period 15 minutes");
            facilities.setText("Inquiries\n" +
                    "Directions\n" +
                    "Toilets & Child Facilities\n" +
                    "Lost Property\n" +
                    "ATM's\n" +
                    "Wheelchairs & Baby Strollers \n" +
                    "Storage of Shopping Items and Umbrellas\n" +
                    "Gift Wrapping\n");
        } else {
            about.setText("About Berjaya Megamall");
            img.setImageResource(R.drawable.mega);
            aboutmall.setText("Berjaya Megamall is located at Jalan Tun Ismail, 25000 Kuantan. It is the 2nd most popular shopping mall in Kuantan after East Coast Mall. Berjaya Megamall is made up of 5 floors: Ground Floor, Level 1, Level 2, Level 3, and a basement floor. Supermarkets, restaurants, electronics, books, stationery, home deco, jewellery, clothing and many others are all available at Berjaya Megamall. ");
            timing.setText("Everyday:" +
                    "Opening times\n" +
                    "10:00 AM - 10:00 PM");
            rates.setText("All day (12.00am to 11.59pm)\n" +
                    "\n" +
                    "First 3 Hours: RM2.00\n" +
                    "\n" +
                    "Subsequent 1 Hour or Part thereof: RM1.00\n" +
                    "\n" +
                    "Subject to maximum/per day:RM6.00\n" +
                    "\n" +
                    "Overnight Parking:RM20.00\n" +
                    "\n" +
                    "Lost Ticket (Including parking fee):RM22.00\n" +
                    "\n" +
                    "Grace Period 15 minutes");
            facilities.setText("Inquiries\n" +
                    "Directions\n" +
                    "Toilets & Child Facilities\n" +
                    "Lost Property\n" +
                    "ATM's\n" +
                    "Wheelchairs & Baby Strollers \n" +
                    "Storage of Shopping Items and Umbrellas\n" +
                    "Gift Wrapping\n");
        }


        grab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mallname2.equals("East Coast Mall"))
                {
                }
                else if (mallname2.equals("Kuantan City Mall"))
                {

                }
                else
                {

                }
            }
        });


        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mallname2.equals("East Coast Mall"))
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/search/East+Coast+Mall/@3.81829,103.3241519,17z/data=!3m1!4b1"));
                    startActivity(intent);
                }
                else if (mallname2.equals("Kuantan City Mall"))
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/search/Kuantan+City+Mall/@3.8176632,103.3255103,17z/data=!3m1!4b1"));
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/place/Berjaya+Megamall/@3.8152359,103.3278537,17z/data=!3m1!4b1!4m5!3m4!1s0x31c8ba91018dcb43:0x43f245aaf843ec6!8m2!3d3.8152359!4d103.3300424"));
                    startActivity(intent);
                }
            }
        });

        shops.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                bundle.putString("mallname" , mallname2);
                ShopsList fragC = new ShopsList();
                fragC.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container , fragC).commit();

            }
        });


    }

}
