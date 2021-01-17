package io.mapwize.mapwizeuicomponents.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.ImageProcessing.Classifier;
import io.mapwize.mapwizeuicomponents.ImageProcessing.ClassifierActivity;
import io.mapwize.mapwizeuicomponents.ImageProcessing.Logger;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class HotFragment extends Fragment {
    private Classifier classifier;
    private static final Logger LOGGER = new Logger();
    private static final int GALLERY_INTENT = 2;
    private ImageView pic;
    private TextView result;
    private CardView ADD_SEarch, search;
    public Bitmap bitmapImage = null;
    private long lastProcessingTimeMs;
    ClassifierActivity classifierActivity;
    /** Input image size of the model along x axis. */
    private int imageSizeX;
    /** Input image size of the model along y axis. */
    private int imageSizeY;
    private DatabaseReference reff;
    ArrayList<Products> productsList;
    Products_Adapter adapter;
    RecyclerView recyclerView;
    public String Productname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_hot, container, false);

        pic = rootview.findViewById(R.id.pic);
        ADD_SEarch = rootview.findViewById(R.id.ADD_SEarch);
        search = rootview.findViewById(R.id.search);
        result = rootview.findViewById(R.id.result);


        recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        productsList = new ArrayList<Products>();
        reff  = FirebaseDatabase.getInstance().getReference().child("Products");


        classifierActivity= new ClassifierActivity();

        recreateClassifier(classifierActivity.getDevice(), classifierActivity.getNumThreads());
        ADD_SEarch.setOnClickListener(v -> {
            startGallery();
        });

        search.setOnClickListener(v -> {
            if (classifier == null) {
                Toast.makeText(getActivity(), "no class", Toast.LENGTH_LONG).show();
                LOGGER.e("No classifier on preview!");
                return;
            }
            else {
           //     Toast.makeText(getActivity(), " class exist", Toast.LENGTH_LONG).show();

                final long startTime = SystemClock.uptimeMillis();
                final List<Classifier.Recognition> results =
                        classifier.recognizeImage(bitmapImage);
                lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime;
                LOGGER.v("Detect: %s" , results);
                showResultsInBottomSheet(results);

            }
        });



        return rootview;
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");

        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            startActivityForResult(cameraIntent, 1000);
        }
    }


    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data) {

        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();

                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pic.setImageBitmap(bitmapImage);
                Picasso.get().load(returnUri).into(pic);
            }
        }

    }

    @UiThread
    protected void showResultsInBottomSheet(List<Classifier.Recognition> results) {
        //Toast.makeText(getActivity(), "showResultsInBottomSheet", Toast.LENGTH_LONG).show();

        if (results != null) {
        //    Toast.makeText(getActivity(), "if (results != null)", Toast.LENGTH_LONG).show();
            Classifier.Recognition recognition = results.get(0);
            if (recognition != null) {
          //      Toast.makeText(getActivity(), " if (recognition != null) ", Toast.LENGTH_LONG).show();
                recyclerView.setVisibility(View.VISIBLE);
                result.setVisibility(View.VISIBLE);
                if (recognition.getTitle() != null) result.setText("Result of " + recognition.getTitle() + " Search");
                Productname = recognition.getTitle();
                if (recognition.getConfidence() != null)
                {
/*
                    text2.setText(
                            String.format("%.2f" , (100 * recognition.getConfidence())) + "%");*/
show(Productname);
                }

            }



        }

    }


    public void recreateClassifier(Classifier.Device device, int numThreads) {
        if (classifier != null) {
            LOGGER.d("Closing classifier.");
            classifier.close();
            classifier = null;
        }
        try {
            LOGGER.d(
                    "Creating classifier (device=%s, numThreads=%d)", device, numThreads);
            classifier = Classifier.create(getActivity(), device, numThreads);
        } catch (IOException e) {
            LOGGER.e(e, "Failed to create classifier.");
        }

        // Updates the input image size.
        imageSizeX = classifier.getImageSizeX();
        imageSizeY = classifier.getImageSizeY();
    }

    public void show(String Prod)
    {
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getActivity(), Prod, Toast.LENGTH_LONG).show();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    Products products = dataSnapshot1.getValue(Products.class);
                    if(Prod.equals(products.getP_name()))

                        productsList.add(products);
                }
                adapter = new Products_Adapter(getContext(), productsList,"Customer");
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}