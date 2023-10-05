package com.example.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Messages> messages;
    private static Context context;

    MessageAdapter(Context context, List<Messages> messages){
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    @NotNull
    public MessageAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position){
        Messages message = messages.get(position);
        holder.NickName.setText(message.getNickName());
        holder.textMessage.setText(message.getMessage());
    }

    public int getItemCount() {return messages.size();}
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView NickName;
        final TextView textMessage;
        ViewHolder(View view){
            super(view);
            NickName = view.findViewById(R.id.NameNick);
            textMessage = view.findViewById(R.id.textMessage);
        }
    }
}
