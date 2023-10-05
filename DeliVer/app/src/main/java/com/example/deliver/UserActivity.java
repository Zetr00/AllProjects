package com.example.deliver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliver.Adapters.OrdersAdapter;
import com.example.deliver.database.Message;
import com.example.deliver.database.Order;
import com.example.deliver.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private String id;
    private int role = 0;
    private int clickCount = 0;
    Menu menu;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users = db.collection("User");
    CollectionReference orders;
    User currentUser;


    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        layout = findViewById(R.id.create_order_menu);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String navRole = "";
                ConstraintLayout profile = findViewById(R.id.profile_menu);
                profile.setVisibility(View.INVISIBLE);
                if(item.getItemId() == R.id.my_orders_items){navRole = "User";}
                else if (item.getItemId() == R.id.request_orders_items) {navRole = "Del";}
                else if (item.getItemId() == R.id.history_orders_items) {navRole = "History";}
                else if (item.getItemId() == R.id.profile_items) {
                    navRole = "History";
                    OpenProfileStatistic();
                    profile.setVisibility(View.VISIBLE);
                }
                LoadRoleOrders(navRole);
                return true;
            }
        });
        id = getIntent().getStringExtra("id");
        LoadRoleOrders("User");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.create_order_menu_item){
            layout.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private void LoadRoleOrders(String curRole){
        users.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User getUser = task.getResult().toObject(User.class);
                    currentUser = getUser;
                    if(curRole.equals("Del")){
                        if(role == 1){
                            clickCount++;
                            if(clickCount > 2){
                                clickCount = 0;
                                ReloadRecycler();
                                return;
                            }
                            ReloadRecForOrderer();
                        }
                        else{
                            role = 1;
                            clickCount = 0;
                            ReloadRecycler();
                        }
                    }
                    else if(curRole.equals("User")) {
                        role = 0;
                        clickCount = 0;
                        ReloadRecycler();
                    }
                    else {
                        role = 2;
                        clickCount = 0;
                        ReloadRecycler();
                    }
                }
            }
        });
    }

    private void ReloadRecycler(){
        RecyclerView rec = findViewById(R.id.order_recycler);
        if(role == 1){
            List<String> strings = new ArrayList<>();
            strings.add(id);
            strings.add("-");
            db.collection("orders").whereIn("deliverID",strings).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Order> getOrders = task.getResult().toObjects(Order.class);
                                for(int i = 0; i < getOrders.size(); i++){
                                    getOrders.get(i).setId(task.getResult().getDocuments().get(i).getId());
                                }
                                List<Order> checkGetOrders = getOrders;
                                for (int i = 0; i < getOrders.size(); i++){
                                    if(getOrders.get(i).getOrdererID().equals(id))
                                        checkGetOrders.remove(getOrders.get(i));
                                }
                                rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                rec.setHasFixedSize(true);

                                OrdersAdapter adapter = new OrdersAdapter(checkGetOrders, role, id);
                                rec.setAdapter(adapter);
                            }
                        }
                    });
        }
        else if (role == 0){
            db.collection("orders").whereEqualTo("ordererID",id).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Order> getOrders = task.getResult().toObjects(Order.class);
                                for(int i = 0; i < getOrders.size(); i++){
                                    getOrders.get(i).setId(task.getResult().getDocuments().get(i).getId());
                                }

                                rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                rec.setHasFixedSize(true);

                                OrdersAdapter adapter = new OrdersAdapter(getOrders, role, id);
                                rec.setAdapter(adapter);
                            }
                        }
                    });
        }
        else {
            db.collection("orders").whereEqualTo("ordererID", id).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Order> getOrders = task.getResult().toObjects(Order.class);
                                for (int i = 0; i < getOrders.size(); i++) {getOrders.get(i).setId(task.getResult().getDocuments().get(i).getId());}
                                List<String> strings = new ArrayList<>();
                                strings.add(id);
                                db.collection("orders").whereIn("deliverID",strings).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    List<Order> getOrders1 = task.getResult().toObjects(Order.class);
                                                    for(int i = 0; i < getOrders1.size(); i++){getOrders1.get(i).setId(task.getResult().getDocuments().get(i).getId());}
                                                    getOrders.addAll(getOrders1);
                                                    rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                                    rec.setHasFixedSize(true);
                                                    OrdersAdapter adapter = new OrdersAdapter(getOrders, role, id);
                                                    rec.setAdapter(adapter);
                                                    rec.setFocusable(getOrders.size()-1);
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }
    }

    private void ReloadRecForOrderer(){
        RecyclerView rec = findViewById(R.id.order_recycler);

        List<String> strings = new ArrayList<>();
        strings.add(id);
        strings.add("-");
        db.collection("orders").whereIn("deliverID",strings).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Order> getOrders = task.getResult().toObjects(Order.class);
                            for(int i = 0; i < getOrders.size(); i++){
                                getOrders.get(i).setId(task.getResult().getDocuments().get(i).getId());
                            }
                            List<Order> checkGetOrders = getOrders;
                            for (int i = 0; i < getOrders.size(); i++){
                                if(getOrders.get(i).getOrdererID().equals(id))
                                    checkGetOrders.remove(getOrders.get(i));
                            }

                            List<Order> fullCheckList = checkGetOrders;
                            if(clickCount == 1){
                                Toast.makeText(UserActivity.this, "Активные", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < checkGetOrders.size(); i++){
                                    if(checkGetOrders.get(i).getStatus().equals("Ищем курьера") ||
                                            checkGetOrders.get(i).getStatus().equals("Принят"))
                                        checkGetOrders.remove(checkGetOrders.get(i));
                                }
                            }if(clickCount == 2){
                                Toast.makeText(UserActivity.this, "Завершённые", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < checkGetOrders.size(); i++){
                                    if(!checkGetOrders.get(i).getStatus().equals("Принят"))
                                        checkGetOrders.remove(checkGetOrders.get(i));
                                }
                            }
                            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rec.setHasFixedSize(true);

                            OrdersAdapter adapter = new OrdersAdapter(checkGetOrders, role, id);
                            rec.setAdapter(adapter);
                        }
                    }
                });
    }
    public void PlaceOrderClick(View view) {
        EditText from = findViewById(R.id.address_from_view),
                to = findViewById(R.id.address_to_view),
                desc = findViewById(R.id.description_view),
                cost = findViewById(R.id.cost_view);
        Order order = new Order("",
                currentUser.getName(),
                id,
                "-",
                "-",
                from.getText().toString(),
                to.getText().toString(),
                desc.getText().toString(),
                "Ищем курьера",
                (int)Double.parseDouble(cost.getText().toString()));
        db.collection("orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Message message = new Message("","System","Объявление создано", 1);
                    db.collection("orders").document(task.getResult().getId())
                            .collection("messages")
                            .add(message);
                }
            }
        });
        Toast.makeText(this, "Заказ на доставку добавлен", Toast.LENGTH_SHORT).show();
        layout.setVisibility(View.INVISIBLE);
        from.setText("");
        to.setText("");
        desc.setText("");
        cost.setText("");
    }

    public void CloseMenuClick(View view) {
        layout.setVisibility(View.INVISIBLE);
    }

    boolean delOpened = false;
    boolean ordOpened = false;
    private void OpenProfileStatistic(){
        TextView name = findViewById(R.id.main_profile_name),
                deliver = findViewById(R.id.deliver_statistic),
                deliverButt = findViewById(R.id.deliverer_button),
                orders = findViewById(R.id.orders_statistic),
                ordersButt  = findViewById(R.id.orders_button);
        name.setText(currentUser.getName());
        deliverButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delOpened){
                    delOpened = false;
                    ConstraintLayout lay = findViewById(R.id.constraintLayout);
                    lay.setVisibility(View.GONE);
                }
                else{
                    delOpened = true;
                    ConstraintLayout lay = findViewById(R.id.constraintLayout);
                    lay.setVisibility(View.VISIBLE);
                }
            }
        });
        ordersButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ordOpened){
                    ordOpened = false;
                    ConstraintLayout lay = findViewById(R.id.constraintLayout1);
                    lay.setVisibility(View.GONE);
                }
                else{
                    ordOpened = true;
                    ConstraintLayout lay = findViewById(R.id.constraintLayout1);
                    lay.setVisibility(View.VISIBLE);
                }
            }
        });
        List<String> strings = new ArrayList<>();
        strings.add(id);
        db.collection("orders").whereIn("deliverID",strings).
                whereEqualTo("status", "Принят")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int moneyGained = 0;
                            for (Order order:
                                 task.getResult().toObjects(Order.class)) {
                                moneyGained += order.getCost();
                            }
                            deliver.setText("Выполнено заказов: "+task.getResult().toObjects(Order.class).size()+"\nВсего получено денег: "+moneyGained);
                        }
                    }
                });
        db.collection("orders").whereEqualTo("ordererID",id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int moneyGained = 0;
                            for (Order order:
                                    task.getResult().toObjects(Order.class)) {
                                moneyGained += order.getCost();
                            }
                            orders.setText("Заказов выставлено: "+task.getResult().toObjects(Order.class).size()+"\nВсего потрачено денег: "+moneyGained);
                        }
                    }
                });
    }
}