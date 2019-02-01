package com.example.mobile_client;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfilePage extends AppCompatActivity {

    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private Button edit_sumbit;
    //private Button go_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        old_password = (EditText)findViewById(R.id.oldPassword);
        new_password = (EditText)findViewById(R.id.newPassword);
        confirm_password = (EditText)findViewById(R.id.confirmPassword);
        edit_sumbit = (Button)findViewById(R.id.e_sumbit);
        //go_Back = (Button)findViewById(R.id.e_back);

        edit_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();

            }
        });


    }
    private void goToAccount()
    {
        //cannot jump to account page. only go to the main page (home page)
        Intent intent = new Intent(EditProfilePage.this, Main_Page.class);

        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
//        if(item.getItemId() == android.R.id.home)
//        {
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

            default:
                //no error
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
