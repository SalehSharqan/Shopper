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
import android.widget.TextView;
import android.widget.Toast;

import io.mapwize.mapwizeuicomponents.ImageProcessing.Classifier;
import io.mapwize.mapwizeuicomponents.ImageProcessing.ClassifierActivity;
import io.mapwize.mapwizeuicomponents.ImageProcessing.Logger;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class HotFragment extends Fragment {
    private Classifier classifier;
    private static final Logger LOGGER = new Logger();
    private static final int GALLERY_INTENT = 2;
    private ImageView pic;
    private TextView text, text2;
    private CardView ADD_SEarch, show;
    public Bitmap bitmapImage = null;
    private long lastProcessingTimeMs;
    ClassifierActivity classifierActivity;
    /** Input image size of the model along x axis. */
    private int imageSizeX;
    /** Input image size of the model along y axis. */
    private int imageSizeY;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_hot, container, false);

        pic = rootview.findViewById(R.id.pic);
        text = rootview.findViewById(R.id.text);
        text2 = rootview.findViewById(R.id.text2);
        ADD_SEarch = rootview.findViewById(R.id.ADD_SEarch);
        show = rootview.findViewById(R.id.show);
        classifierActivity= new ClassifierActivity();

        recreateClassifier(classifierActivity.getDevice(), classifierActivity.getNumThreads());
        ADD_SEarch.setOnClickListener(v -> {
            startGallery();
        });

        show.setOnClickListener(v -> {
            if (classifier == null) {
                Toast.makeText(getActivity(), "no class", Toast.LENGTH_LONG).show();
                LOGGER.e("No classifier on preview!");
                return;
            }
            else {
                Toast.makeText(getActivity(), " class exist", Toast.LENGTH_LONG).show();

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
        Toast.makeText(getActivity(), "showResultsInBottomSheet", Toast.LENGTH_LONG).show();

        if (results != null) {
            Toast.makeText(getActivity(), "if (results != null)", Toast.LENGTH_LONG).show();
            Classifier.Recognition recognition = results.get(0);
            if (recognition != null) {
                Toast.makeText(getActivity(), " if (recognition != null) ", Toast.LENGTH_LONG).show();
                if (recognition.getTitle() != null) text.setText(recognition.getTitle());
                if (recognition.getConfidence() != null)
                {

                    text2.setText(
                            String.format("%.2f" , (100 * recognition.getConfidence())) + "%");
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

}
