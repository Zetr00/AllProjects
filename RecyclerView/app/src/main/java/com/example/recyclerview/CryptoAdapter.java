package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {
    private ArrayList<Crypto> cryptos;

    public CryptoAdapter (ArrayList<Crypto> crypto){
        this.cryptos = crypto;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.bind(cryptos.get(position));
    }

    @Override
    public int getItemCount() {
        return cryptos.size();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder{

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
            });
        }

        public void bind(Crypto crypto){
            ImageView Iv = itemView.findViewById(R.id.photo);
            TextView Tv = itemView.findViewById(R.id.description);
            TextView Tv2 = itemView.findViewById(R.id.name);
            Iv.setImageResource(crypto.getPhoto());
            Tv2.setText(crypto.getName());
            Tv.setText(crypto.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, Full_Description.class);
                    intent.putExtra("sc", crypto);
                    context.startActivity(intent);
                }
            });
        }
    }
}
