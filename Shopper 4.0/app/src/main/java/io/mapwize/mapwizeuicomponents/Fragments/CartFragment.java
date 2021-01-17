package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.mapwize.mapwizeuicomponents.R;

import androidx.fragment.app.Fragment;

/**
 * Created by Hosam Azzam on 30/01/2018.
 */

public class CartFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cart, container, false);


        return rootview;


    }


}