package com.example.mobile_client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;





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
                if(TextUtils.isEmpty(Username.getText().toString()) || TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(Login_Page.this, "Please enter the username and password", Toast.LENGTH_LONG).show();

                }
                else {
                    postForm(Username.getText().toString(), Password.getText().toString());
                    //Toast.makeText(Login_Page.this, "Login to main Page", Toast.LENGTH_LONG).show();
                }



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





    private void postForm(String username, String password) {
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://35.178.35.227:8080/Paramount/login")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //Log.e("Check:", "onResponse: " + response.body().string());
                    System.out.println(String.valueOf(response.code()));
                   // String reusult1 = response.body().string();

                    try {
                        JSONObject my = new JSONObject(response.body().string());
                        System.out.println(my.getString("status"));
                        System.out.println(my.getString("info"));

                        //Toast.makeText(Login_Page.this,my.getString("status"),Toast.LENGTH_SHORT).show();
                        String status1 = "success";
                        String status2 = "";
                        String rStatus1 = my.getString("status");
                        String rStatus2 = my.getString("info");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(rStatus1.equals(status1) && rStatus2.equals(status2))
                                {
                                    Intent intent = new Intent(Login_Page.this, Main_Page.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(Login_Page.this, "The username or password is incorrect!", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

//                        if(my.getString("status").equals(status1) && my.getString("info").equals(status2))
//                        {
//                            Intent intent = new Intent(Login_Page.this, Main_Page.class);
//                            startActivity(intent);
//                        }






                    } catch (JSONException e) {
                        // Toast.makeText(Login_Page.this, "Login fail", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }



                }



            }
        });
    }



























    //check username and password
    private void validate(String userName, String userPassword)
    {
        if(Username.getText().toString().equals("admin") &&
                Password.getText().toString().equals("admin"))
        {

            // jump to Main Page
            Intent intent = new Intent(Login_Page.this, Main_Page.class);
            startActivity(intent);

        }
        else
        {
            counter--;

            // show how many time left
            Incorrect.setText("No of attempts remaining: " + String.valueOf(counter));
            // disable to login
            if(counter == 0)
            {
                Login.setEnabled(false);
            }
        }
    }
    // jump to sign up page
    private void sign_up()
    {
        Intent intent = new Intent(Login_Page.this, SignUp_Page.class);
        startActivity(intent);

    }
    // jump to forgot password page
    private void forgot_password()
    {
        Intent intent = new Intent(Login_Page.this, Rest_Password_Page.class);
        startActivity(intent);
    }

    private void shortToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}


//    AlertDialog alertDialog = new AlertDialog.Builder(Login_Page.this)
//            .setTitle("Incorrect")
//            .setMessage("Email or Password is incorrect!").create();
//                        alertDialog.show();