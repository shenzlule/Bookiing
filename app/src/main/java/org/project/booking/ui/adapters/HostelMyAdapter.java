package org.project.booking.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking.R;

import org.project.booking.ui.HostelViewer;
import org.project.booking.ui.HostelViewerBooked;
import org.project.booking.ui.models.Hostel;

import java.util.List;

public class HostelMyAdapter extends RecyclerView.Adapter<HostelMyAdapter.HostelViewHolder> {

    private Context context;
    private List<Hostel> hostelList;

    public HostelMyAdapter(Context context, List<Hostel> hostelList) {
        this.context = context;
        this.hostelList = hostelList;
    }

    @NonNull
    @Override
    public HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hostel_card, parent, false);
        return new HostelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelViewHolder holder, int position) {

        Hostel hostel = hostelList.get(position);
        holder.nameText.setText(hostel.getName());
        holder.locationText.setText(hostel.getLocation());
//        holder.ratingBar.setText(hostel.getRating());

        holder.viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HostelViewerBooked.class);
                intent.putExtra("indexx",hostel.getId());
                context.startActivity(intent);
            }
        });

        if(hostel.getId() == 1){
            holder.imageView.setImageResource(R.drawable.campbell); // fallback
        }
        if(hostel.getId() == 2){
            holder.imageView.setImageResource(R.drawable.katonga); // fallback
        }
        if(hostel.getId() == 3){
            holder.imageView.setImageResource(R.drawable.bossa); // fallback
        }
        if(hostel.getId() == 4){
            holder.imageView.setImageResource(R.drawable.a4); // fallback
        }
        if(hostel.getId() == 5){
            holder.imageView.setImageResource(R.drawable.a5); // fallback
        }
        if(hostel.getId() == 6){
            holder.imageView.setImageResource(R.drawable.a6); // fallback
        }
        if(hostel.getId() == 7){
            holder.imageView.setImageResource(R.drawable.a7); // fallback
        }
        if(hostel.getId() == 8){
            holder.imageView.setImageResource(R.drawable.a8); // fallback
        }
        if(hostel.getId() == 9){
            holder.imageView.setImageResource(R.drawable.a11); // fallback
        }
        if(hostel.getId() == 10){
            holder.imageView.setImageResource(R.drawable.a9); // fallback
        }


//       if(hostel.getId() == 1){
//           holder.imageView.setImageResource(R.drawable.hicon1); // fallback
//       }
//        if(hostel.getId() == 2){
//            holder.imageView.setImageResource(R.drawable.hicon2); // fallback
//        }
//        if(hostel.getId() == 3){
//            holder.imageView.setImageResource(R.drawable.hicon3); // fallback
//        }
//        if(hostel.getId() == 4){
//            holder.imageView.setImageResource(R.drawable.hicon4); // fallback
//        }
//        if(hostel.getId() == 5){
//            holder.imageView.setImageResource(R.drawable.hicon3); // fallback
//        }
//        if(hostel.getId() == 6){
//            holder.imageView.setImageResource(R.drawable.hicon2); // fallback
//        }
//        if(hostel.getId() == 7){
//            holder.imageView.setImageResource(R.drawable.hicon1); // fallback
//        }
//        if(hostel.getId() == 8){
//            holder.imageView.setImageResource(R.drawable.hicon4); // fallback
//        }
//        if(hostel.getId() == 9){
//            holder.imageView.setImageResource(R.drawable.hicon2); // fallback
//        }
//        if(hostel.getId() == 10){
//            holder.imageView.setImageResource(R.drawable.hicon1); // fallback
//        }
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public static class HostelViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, contactText, locationText, featuresText,ratingBar;
//        RatingBar ratingBar;
        ImageView imageView;
        public Button viewbutton;


        public HostelViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.title);
//            contactText = itemView.findViewById(R.id.contact);
            locationText = itemView.findViewById(R.id.location);
            featuresText = itemView.findViewById(R.id.features);
//            ratingBar = itemView.findViewById(R.id.rating);
            imageView = itemView.findViewById(R.id.thumbnail);
            viewbutton = itemView.findViewById(R.id.viewbutton);

        }
    }
}
