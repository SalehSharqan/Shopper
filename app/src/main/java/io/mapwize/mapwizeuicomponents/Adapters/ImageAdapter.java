package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter {
    Context context;
    List<Ads> imageUrls;
    TextView tt;
    ImageView im_slider;
    LayoutInflater layoutInflater;


    public ImageAdapter(Context context, List<Ads> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View view =  layoutInflater.inflate(R.layout.slider_home_layout,container,false);
        im_slider = view.findViewById(R.id.im_slider);
        Picasso.get()
                .load(imageUrls.get(position).getPhoto())
                .into(im_slider);
        im_slider.animate();

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        ((ViewPager)container).removeView((View)object);
    }
}