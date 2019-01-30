package com.example.mobile_client;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfilePage extends AppCompatActivity {

    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private Button edit_sumbit;
    private Button go_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);





        old_password = (EditText)findViewById(R.id.oldPassword);
        new_password = (EditText)findViewById(R.id.newPassword);
        confirm_password = (EditText)findViewById(R.id.confirmPassword);
        edit_sumbit = (Button)findViewById(R.id.e_sumbit);
        go_Back = (Button)findViewById(R.id.e_back);

        edit_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAccount();

            }
        });
        go_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goBack();

            }
        });

    }
    private void goToAccount()
    {
        //cannot jump to account page. only go to the main page (home page)
        Intent intent = new Intent(EditProfilePage.this, Main_Page.class);

        startActivity(intent);

    }
    private void goBack()
    {
//        Intent intent = new Intent(getActivity(),EditProfilePage.class);
//        getActivity().startActivity(intent);
    }
}
