package com.example.deliver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliver.DeliverOrderMenu;
import com.example.deliver.R;
import com.example.deliver.database.Order;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    List<Order> orders;
    int type;
    String idUser;

    public OrdersAdapter(List<Order> orders, int type, String idUser) {
        this.orders = orders;
        this.type = type;
        this.idUser = idUser;
    }

    Context applicationContext;

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        applicationContext = parent.getContext().getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        holder.Bind(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void Bind(Order order){
            TextView name = itemView.findViewById(R.id.orderer_name_view),
                    addresses = itemView.findViewById(R.id.adresses_boxes_view),
                    status = itemView.findViewById(R.id.status_item);
            if(type == 0){
                name.setText(order.getDeliverName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(applicationContext, DeliverOrderMenu.class);
                        intent.putExtra("id", idUser);
                        intent.putExtra("type", type);
                        intent.putExtra("idOrder", order.getId());
                        itemView.getContext().startActivity(intent);
                    }
                });
            }
            else if(type == 1) {
                name.setText(order.getOrdererName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(applicationContext, DeliverOrderMenu.class);
                        intent.putExtra("id", idUser);
                        intent.putExtra("type", type);
                        intent.putExtra("idOrder", order.getId());
                        itemView.getContext().startActivity(intent);
                    }
                });
            } else {
                BindMulti(order);
            }
            addresses.setText(order.getAddressFrom()+ " - "+ order.getAddressTo());
            status.setText(order.getStatus());
        }
        private void BindMulti(Order order){
            TextView name = itemView.findViewById(R.id.orderer_name_view);

            if(order.getDeliverID().equals(idUser)){
                name.setText(order.getOrdererName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(applicationContext, DeliverOrderMenu.class);
                        intent.putExtra("id", idUser);
                        intent.putExtra("type", type);
                        intent.putExtra("idOrder", order.getId());
                        itemView.getContext().startActivity(intent);
                    }
                });
            } else {
                name.setText(order.getDeliverName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(applicationContext, DeliverOrderMenu.class);
                        intent.putExtra("id", idUser);
                        intent.putExtra("type", type);
                        intent.putExtra("idOrder", order.getId());
                        itemView.getContext().startActivity(intent);
                    }
                });
            }

        }
    }
}
