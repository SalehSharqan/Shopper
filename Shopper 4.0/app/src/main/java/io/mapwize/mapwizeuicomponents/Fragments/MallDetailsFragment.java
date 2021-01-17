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

import io.mapwize.mapwizeuicomponents.R;

import java.util.Timer;

import androidx.fragment.app.Fragment;

public class MallDetailsFragment extends Fragment {
    TextView Mall, text;
    private TextView[] dots;
    Timer timer;
    ImageView img, grab, uber, map;
    String mall;
    Button aboutmall,timing,facilities,rates, directions;
    LinearLayout lin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_mall_details, container, false);

        Bundle b = this.getArguments();
        mall = b.getString("mallname");
        Toast.makeText(getActivity(), mall, Toast.LENGTH_SHORT).show();
        img = rootview.findViewById(R.id.imageView);
        Mall = rootview.findViewById(R.id.Mall);
        text = rootview.findViewById(R.id.text);
        timing = rootview.findViewById(R.id.timing);
        rates = rootview.findViewById(R.id.rates);
        facilities = rootview.findViewById(R.id.facilities);
        aboutmall = rootview.findViewById(R.id.aboutmall);
        directions = rootview.findViewById(R.id.directions);
        grab = rootview.findViewById(R.id.grab);
        uber = rootview.findViewById(R.id.uber);
        map = rootview.findViewById(R.id.map);
        lin = rootview.findViewById(R.id.lin);


        Mall.setText(mall);

        if (mall.equals("East Coast Mall"))
        {
            img.setImageResource(R.drawable.east);
        }
        else if (mall.equals("Kuantan City Mall"))
        {
            img.setImageResource(R.drawable.kauntan);
        }
        else
        {
            img.setImageResource(R.drawable.mega);
        }


        aboutmall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (mall.equals("East Coast Mall"))
                {
                    text.setText("East Coast Mall is the preferred destination mall for tourists and locals alike with an established mix of domestic and international retailers, from fashion, entertainment, digital to local delicacies and international gourmets which located in the heart of Kuantan’s City Centre in Pahang, a rapidly growing and modern city with rich history and beautiful sights.\n");
                }
                else if (mall.equals("Kuantan City Mall"))
                {
                    text.setText("Kuantan City Mall is a brand new shopping centre strategically located in the new commercial hub of Kuantan, comprising all the shopping, dining, play and entertainment experience that you can spend quality time with family. Truly an excitement place in all ways that you don’t want to miss!\n" +
                            "\n" +
                            "Kuantan City Mall is a 7 storey huge shopping centre with 1,300 car parking lots, ample for those who prefer to drive to this fun-filled mall, and approximately 200 retail shops with 468,000 square feet rentable area. It is situated within the commercial area that is fully developed with office buildings, hotels and shop lots along Jalan Putra Square 6, Putra Square. This is the latest shopping centre at east coast Malaysia that offers great experiences beyond shopping for you and your family!");

                }
                else
                {
                    text.setText("Berjaya Megamall is located at Jalan Tun Ismail, 25000 Kuantan. It is the 2nd most popular shopping mall in Kuantan after East Coast Mall. Berjaya Megamall is made up of 5 floors: Ground Floor, Level 1, Level 2, Level 3, and a basement floor. Supermarkets, restaurants, electronics, books, stationery, home deco, jewellery, clothing and many others are all available at Berjaya Megamall. ");

                }
            }
        });

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setVisibility(View.GONE);
                lin.setVisibility(View.VISIBLE);
            }
        });
        timing.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (mall.equals("East Coast Mall"))
                {
                    text.setText("Everyday:" +
                            "Opening times\n" +
                            "10:00 AM - 10:00 PM");
                }
                else if (mall.equals("Kuantan City Mall"))
                {
                    text.setText("Everyday:" +
                            "Opening times\n" +
                            "10:00 AM - 10:00 PM");

                }
                else
                {
                    text.setText("Everyday:" +
                            "Opening times\n" +
                            "10:00 AM - 10:00 PM");
                }

            }
        });

        facilities.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (mall.equals("East Coast Mall"))
                {
                    text.setText("LATER");
                }
                else if (mall.equals("Kuantan City Mall"))
                {
                    text.setText("LATER");

                }
                else
                {
                    text.setText("- ATM\n" +
                            "- Surau\n" +
                            "- Money Changer\n" +
                            "- Wheelchair\n" +
                            "- Car Wash\n" +
                            "- Bowling Alley");

                }
            }
        });

        rates.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (mall.equals("East Coast Mall"))
                {
                    text.setText("All day (12.00am to 11.59pm)\n" +
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
                }
                else if (mall.equals("Kuantan City Mall"))
                {
                    text.setText("LATER");

                }
                else
                {
                    text.setText("LATER");

                }
            }
        });

        grab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mall.equals("East Coast Mall"))
                {
                }
                else if (mall.equals("Kuantan City Mall"))
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
                if (mall.equals("East Coast Mall"))
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/East+Coast+Mall/@3.81829,103.3241519,17z/data=!3m1!4b1"));
                    startActivity(intent);
                }
                else if (mall.equals("Kuantan City Mall"))
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


        return rootview;
    }

}
