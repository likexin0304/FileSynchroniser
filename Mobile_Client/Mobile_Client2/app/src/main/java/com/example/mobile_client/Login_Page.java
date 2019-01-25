package com.example.mobile_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Page extends AppCompatActivity {

    // create variables for our XML layout
    private EditText Username;
    private EditText Password;
    private TextView Incorrect;
    private Button Login;
    private TextView Signup;
    private TextView Forgot;

    private int counter = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigned our variables with the respective XML layout ID
        //XML: Username, Password, tvincorrect, btnLogin, tvSignup


        Username = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        Incorrect = (TextView)findViewById(R.id.tvincorrect);
        Login = (Button)findViewById(R.id.btnLogin);
        Signup =(TextView)findViewById(R.id.tvSignup);
        Forgot = (TextView)findViewById(R.id.tv_forgot);

        Incorrect.setText("No of attempts remaining: 5");
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Username.getText().toString(),Password.getText().toString());
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sign_up();
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_password();
            }
        });
    }


    //check username and password
    private void validate(String userName, String userPassword)
    {
//        if((userName == "admin") && (userPassword == "1234"))
//        {
            // jump to Main Page
            Intent intent = new Intent(Login_Page.this, Main_Page.class);
            startActivity(intent);

//        }
//        else
//        {
//            counter--;
//
//            // show how many time left
//            Incorrect.setText("No of attempts remaining: " + String.valueOf(counter));
//            // disable to login
//            if(counter == 0)
//            {
//                Login.setEnabled(false);
//            }
//        }
    }
    private void sign_up()
    {
        Intent intent = new Intent(Login_Page.this, SignUp_Page.class);
        startActivity(intent);

    }
    private void forgot_password()
    {
        Intent intent = new Intent(Login_Page.this, Reset_password_Page.class);
        startActivity(intent);
    }
}
