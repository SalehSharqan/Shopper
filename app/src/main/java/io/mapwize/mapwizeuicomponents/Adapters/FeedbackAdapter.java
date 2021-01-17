package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;

import io.mapwize.mapwizeuicomponents.Fragments.ShopInfo;
import io.mapwize.mapwizeuicomponents.Models.Feedback;
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Context context;
    ArrayList<Feedback> feedbacks;
    public static String shopid, shopname;
    String type;



    public FeedbackAdapter(Context c, ArrayList<Feedback> s, String t) {
        context = c;
        feedbacks = s;
        type= t;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.feedback_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       holder.name.setText(feedbacks.get(position).getName());
        holder.comments.setText(feedbacks.get(position).getComment());
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setRating(Float.parseFloat(feedbacks.get(position).getRating()));


         Picasso.get().load(feedbacks.get(position).getPhoto()).into(holder.image);
          holder.image.setScaleType(ImageView.ScaleType.FIT_XY);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                try {
                    if(type.equals("Shop")){
                   /* Bundle bundle = new Bundle();
                    bundle.putString("shopid", shopid);
                    bundle.putString("shopname", shopname);
                    ShopInfo fragB = new ShopInfo();
                    fragB.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.main_fragment_container, fragB).commit();*/
                    }
                    else if (type.equals("Mall"))
                    {

                    }
                    else
                    {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, comments;
        CardView cardView;
        RatingBar ratingBar;
        CircleImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            comments = itemView.findViewById(R.id.Comment);
            cardView = itemView.findViewById(R.id.card_view);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            image = itemView.findViewById(R.id.image);

        }

    }
}
