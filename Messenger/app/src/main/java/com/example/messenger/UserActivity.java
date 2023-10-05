package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView mainbotnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mainbotnav = findViewById(R.id.mainbotnav);

        setFragment(new MessagesFragment());

        mainbotnav.setOnItemSelectedListener(new
                                                     NavigationBarView.OnItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                             switch (item.getItemId()) {
                                                                 case R.id.messages:
                                                                     setFragment(new MessagesFragment());
                                                                     return true;
                                                                 case R.id.friends:
                                                                     setFragment(new FriendsFragment());
                                                                     return true;
                                                                 case R.id.friendsRequest:
                                                                     setFragment(new FriendsRequestFragment());
                                                                     return true;
                                                                 case R.id.profile:
                                                                     setFragment(new ProfileFragment());
                                                                     return true;
                                                             }
                                                             return false;
                                                         }
                                                     });

    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, fragment, null).commit();
    }
}