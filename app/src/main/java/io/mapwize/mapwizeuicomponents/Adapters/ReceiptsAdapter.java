package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Additionals> additionalsArrayList;
    public static String shop_name;



    public ReceiptsAdapter(Context c, ArrayList<Additionals> s) {
        context = c;
        additionalsArrayList = s;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.ads_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Picasso.get().load(additionalsArrayList.get(position).getReceipts()).into(holder.shop_image);
        holder.shop_image.setScaleType(ImageView.ScaleType.FIT_XY);


    }


    @Override
    public int getItemCount() {
        return additionalsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView shop_image;
        public MyViewHolder(View itemView) {
            super(itemView);

            shop_image = itemView.findViewById(R.id.shop_image);


        }

    }
}
