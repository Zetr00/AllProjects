package com.example.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliver.Adapters.MessageAdapter;
import com.example.deliver.Adapters.OrdersAdapter;
import com.example.deliver.database.Message;
import com.example.deliver.database.Order;
import com.example.deliver.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

public class DeliverOrderMenu extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("User");
    CollectionReference orders = db.collection("orders");
    CollectionReference messages;

    String isClosedBy;
    RecyclerView rec;
    String idUser, idOrder;
    Order curOrder;
    User curUser;
    Menu menu;
    int messagesCount = 1;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_order_menu);
        idUser = getIntent().getStringExtra("id");
        idOrder = getIntent().getStringExtra("idOrder");
        type = getIntent().getIntExtra("type", 0);
        //Toast.makeText(this, idOrder, Toast.LENGTH_SHORT).show();
        messages = db.collection("orders").document(idOrder).collection("messages");

        rec = findViewById(R.id.message_rec);
        ReloadRecycler();
        users.document(idUser).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            User user = task.getResult().toObject(User.class);
                            curUser = user;
                            curUser.setId(task.getResult().getId());
                            if(type == 1){
                                getMenuInflater().inflate(R.menu.chat_menu, menu);
                            }
                            else {
                                getMenuInflater().inflate(R.menu.user_del_menu, menu);
                            }
                            orders.document(idOrder).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                Order order = task.getResult().toObject(Order.class);
                                                curOrder = order;
                                                curOrder.setId(task.getResult().getId());

                                                UpdateUI();

                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cancel_item){
            if(curOrder.getStatus().equals("Ищем курьера")){
                finish();
                orders.document(idOrder).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(DeliverOrderMenu.this, "Заказ удалён", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(this, "Курьер уже принял заказ", Toast.LENGTH_SHORT).show();
            }
        } else if(item.getItemId() == R.id.accept_item){
            if(curOrder.getStatus().equals("У дверей")){
                curOrder.setStatus("Принят");
                orders.document(idOrder).set(curOrder);
                UpdateUI();
                Toast.makeText(this, "Заказ принят, оплата будет снята с вашей карты", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Нельзя принять этот заказ", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.change_status_item) {
            checkIsClosed();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isClosedBy.equals("-") || isClosedBy.equals(idUser)){
                        switch (curOrder.getStatus()){
                            case "Ищем курьера":
                                curOrder.setStatus("В пути");
                                curOrder.setDeliverID(idUser);
                                curOrder.setDeliverName(curUser.getName());
                                break;
                            case "В пути":
                                curOrder.setStatus("Забрал заказ");
                                break;
                            case "Забрал заказ":
                                curOrder.setStatus("Идёт к вам");
                                break;
                            case "Идёт к вам":
                                curOrder.setStatus("У дверей");
                                break;
                        }
                        orders.document(idOrder).set(curOrder);
                        UpdateUI();
                    }
                }
            }, 1500);
        }
        return true;
    }


    private void UpdateUI(){
        TextView name = findViewById(R.id.fullinfo_orderer_name);
        TextView address = findViewById(R.id.fullinfo_adresses);
        TextView status = findViewById(R.id.fullinfo_status);
        TextView desc = findViewById(R.id.fullinfo_desc);
        if(type == 1){
            name.setText(curOrder.getOrdererName());
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeliverOrderMenu.this, Profile.class);
                    intent.putExtra("userID", idUser);
                    intent.putExtra("profileID", curOrder.getOrdererID());
                    startActivity(intent);
                }
            });

        } else {
            name.setText(curOrder.getDeliverName());
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeliverOrderMenu.this, Profile.class);
                    intent.putExtra("userID",idUser);
                    intent.putExtra("profileID", curOrder.getDeliverID());
                    startActivity(intent);
                }
            });

        }
        address.setText(curOrder.getAddressFrom()+" - "+curOrder.getAddressTo());
        status.setText(curOrder.getStatus());
        desc.setText(curOrder.getCost()+" рублей");
    }
    private void checkIsClosed(){
        orders.document(idOrder).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    Order order = task.getResult().toObject(Order.class);
                    if(order.getDeliverID().equals("-")) isClosedBy = "-";
                    else isClosedBy = order.getDeliverID();
                }
            }
        });
    }
    public void ReloadRecycler(){
        messages.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Message> messages = task.getResult().toObjects(Message.class);
                            messagesCount = messages.size();
                            messages.sort(new Comparator<Message>() {
                                @Override
                                public int compare(Message o1, Message o2) {
                                    return o1.getNumber() > o2.getNumber() ? -1: (o1.getNumber() < o2.getNumber())? 1 : 0;
                                }
                            });
                            Collections.reverse(messages);
                            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rec.setHasFixedSize(true);

                            MessageAdapter adapter = new MessageAdapter(messages);
                            rec.setAdapter(adapter);
                        }
                    }
                });
    }

    public void SendMessage(View view) {
        EditText message = findViewById(R.id.editText);
        String textMessage = message.getText().toString();
        if(textMessage.matches("")){
            Toast.makeText(this, "Введите сообщение", Toast.LENGTH_SHORT).show();
            return;
        }
        message.setText("");
        Message sendMessage = new Message("", curUser.getName(),textMessage, messagesCount+1);
        orders.document(idOrder).collection("messages").add(sendMessage).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                ReloadRecycler();
            }
        });
    }
}