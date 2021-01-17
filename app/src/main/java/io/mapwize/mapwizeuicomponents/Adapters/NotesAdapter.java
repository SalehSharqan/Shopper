package io.mapwize.mapwizeuicomponents.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.mapwize.mapwizeuicomponents.Models.Additionals;
import io.mapwize.mapwizeuicomponents.Models.Ads;
import io.mapwize.mapwizeuicomponents.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Additionals> additionalsArrayList;

    public NotesAdapter(Context c, ArrayList<Additionals> s) {
        context = c;
        additionalsArrayList = s;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.p_desc.setText(additionalsArrayList.get(position).getNote());


    }


    @Override
    public int getItemCount() {
        return additionalsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView p_desc;
        public MyViewHolder(View itemView) {
            super(itemView);

            p_desc = itemView.findViewById(R.id.p_desc);


        }

    }
}
