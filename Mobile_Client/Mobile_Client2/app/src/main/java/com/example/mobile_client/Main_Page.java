package com.example.mobile_client;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

}
