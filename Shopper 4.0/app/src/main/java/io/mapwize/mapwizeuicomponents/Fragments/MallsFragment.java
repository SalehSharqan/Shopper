package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

public class MallsFragment extends Fragment {
private ImageView image1, image2, image3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_malls, container, false);

        image1 = rootview.findViewById(R.id.image1);
        image2 = rootview.findViewById(R.id.image2);
        image3 = rootview.findViewById(R.id.image3);

        image1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    Bundle b = new Bundle();
                    b.putString("mall", "Kuantan City Mall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {

                    Bundle b = new Bundle();
                    b.putString("mall", "East Coast Mall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {

                   Bundle b = new Bundle();
                    b.putString("mall", "Berjaya Megamall");
                    HomeFragment fragB = new HomeFragment();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragB).commit();

                  /*  Intent x = new Intent(getActivity(), Main.class);
                    x.putExtra("mall", "Berjaya Megamall");
                    startActivity(x);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootview;


    }


}