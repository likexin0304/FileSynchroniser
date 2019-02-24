package com.example.mobile_client;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import okhttp3.Call;
import okhttp3.Callback;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main__page);


        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);


        final HomeFragment homeFragment = new HomeFragment();
        final AddFragment addFragment = new AddFragment();
        final AccountFragment accountFragment = new AccountFragment();


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.home)
                {
                    setFragment(homeFragment);
                    return true;
                }
                else if (id == R.id.add)
                {
                    setFragment(addFragment);
                    return true;
                }
                else if (id == R.id.account)
                {
                    setFragment(accountFragment);
                    return true;
                }
                return false;
            }

        });
        navigationView.setSelectedItemId(R.id.home);

    }



    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
