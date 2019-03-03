package com.example.mobile_client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;





public class Login_Page extends AppCompatActivity {

    // create variables for our XML layout
    private EditText Username;
    private EditText Password;
    private TextView Incorrect;
    private Button Login;
    private TextView Signup;
    private TextView Forgot;


    private int counter = 5;


   // public List<File_icon> fileList = new ArrayList<File_icon>();


// sharedPreferences for save user information.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigned our variables with the respective XML layout ID
        //XML: Username, Password, tvincorrect, btnLogin, tvSignup

        //initial all variables from UI
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.btnLogin);
        Signup = (TextView) findViewById(R.id.tvSignup);
        Forgot = (TextView) findViewById(R.id.tv_forgot);






        // login button function
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Username.getText().toString()) || TextUtils.isEmpty(Password.getText().toString())) {
                    Toast.makeText(Login_Page.this, "Please enter the username and password", Toast.LENGTH_LONG).show();

                } else {
                    postForm(Username.getText().toString(), Password.getText().toString());
                    //getFileList(Username.getText().toString());
                    //Toast.makeText(Login_Page.this, "Login to main Page", Toast.LENGTH_LONG).show();
                }


            }
        });

        // signup button function
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up();
            }
        });

        // forgot password function
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot_password();
            }
        });
    }


    // okhttp send request to server for login function
    private void postForm(String username, String password) {
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://teamparamount.cn:8080/Paramount/login")
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
                                if (rStatus1.equals(status1) && rStatus2.equals(status2)) {

                                    String username = Username.getText().toString();
                                    String password = Password.getText().toString();
                                    //在 MySharedPreferences类里定义好存取方法后，就可以调用了
                                    //这里将数据保存进去  注意：(name 我是定义了有返回值的，试试看)
                                    Boolean bool = MySharedPreferences.setuserName(username, Login_Page.this);
                                    MySharedPreferences.setPswd(password, Login_Page.this);


                                    //看看保存成功没
                                    if (bool)
                                        Toast.makeText(Login_Page.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(Login_Page.this, "Error！", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login_Page.this, Main_Page.class);
                                    startActivity(intent);
                                } else {
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


    // jump to sign up page
    private void sign_up() {
        Intent intent = new Intent(Login_Page.this, SignUp_Page.class);
        startActivity(intent);

    }

    // jump to forgot password page
    private void forgot_password() {
        Intent intent = new Intent(Login_Page.this, Rest_Password_Page.class);
        startActivity(intent);
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

//    AlertDialog alertDialog = new AlertDialog.Builder(Login_Page.this)
//            .setTitle("Incorrect")
//            .setMessage("Email or Password is incorrect!").create();
//                        alertDialog.show();