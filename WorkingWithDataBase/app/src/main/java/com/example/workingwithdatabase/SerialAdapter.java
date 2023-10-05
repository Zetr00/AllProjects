package com.example.workingwithdatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Serial> serialList;

    public SerialAdapter(Context context, ArrayList<Serial> serialList){
        this.context = context;
        this.serialList = serialList;
    }

    @NonNull
    @Override
    public SerialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_serial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerialAdapter.ViewHolder holder, int position){
        Serial serial = serialList.get(position);
        holder.serialName.setText("Название: " + serial.getSerial_Name());
        holder.serialDetail.setText("Описание: " + serial.getSerial_Detail());
        holder.serialFIO.setText("Студент: " + serial.getStudent_FIO());
        holder.serialScore.setText("Оценка сериала: " + serial.getStudent_Score());

        holder.bind(serialList.get(position));
    }

    @Override
    public int getItemCount(){
        return serialList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView serialName;
        TextView serialDetail;
        TextView serialFIO;
        TextView serialScore;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            serialName = itemView.findViewById(R.id.name);
            serialDetail = itemView.findViewById(R.id.detail);
            serialFIO = itemView.findViewById(R.id.fio);
            serialScore = itemView.findViewById(R.id.score);
        }

        public void bind(Serial serial){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, infoAbout.class);
                    intent.putExtra("SERIAL", serial);
                    context.startActivity(intent);
                }
            });
        }
    }
}