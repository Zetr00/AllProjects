package com.example.deliver.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliver.R;
import com.example.deliver.database.Message;
import com.example.deliver.database.Rating;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    List<Rating> ratings;

    public RatingAdapter(List<Rating> ratings) {
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {
        holder.Bind(ratings.get(position));
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void Bind(Rating rate){
            TextView sender = itemView.findViewById(R.id.sender_name),
                    context = itemView.findViewById(R.id.message_context);
            sender.setText(rate.getFrom());
            context.setText(rate.getRate()+" из 5\n"+rate.getMessage());
        }
    }
}
