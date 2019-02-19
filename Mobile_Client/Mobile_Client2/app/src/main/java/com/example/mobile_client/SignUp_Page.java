package com.example.mobile_client;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp_Page extends AppCompatActivity {


    // create variables for our XML layout
    private EditText Username;
    private EditText Password;
    private EditText Repassword;
    private EditText question1;
    private EditText question2;
    private Button Submit;
    private TextView Signup;
    private TextView HaveAccount;

    private String TAG = "SignUp_Page";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initial all variables from UI
        Username = (EditText)findViewById(R.id.Sign_Username);
        Password = (EditText)findViewById(R.id.Sign_password);
        Repassword = (EditText)findViewById(R.id.Sign_repassword);
        question1 = (EditText)findViewById(R.id.Sign_question1);
        question2 = (EditText)findViewById(R.id.Sign_question2);
        Submit = (Button)findViewById(R.id.Sign_button);
        //HaveAccount = (TextView)findViewById(R.id.tv_haveAccount);


        // sign up function
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // check the input
              if(TextUtils.isEmpty(Username.getText().toString()) || TextUtils.isEmpty(Password.getText().toString()) ||
              TextUtils.isEmpty(question1.getText().toString()) ||TextUtils.isEmpty(question2.getText().toString()))
              {
                  Toast.makeText(SignUp_Page.this, "Please fill all blanks", Toast.LENGTH_LONG).show();
              }
              else {

                    // connect to server and sign up
                  OkHttpClient okHttpClient = new OkHttpClient();
                  //requestbody
                  FormBody formBody = new FormBody.Builder()
                          .add("username", Username.getText().toString())
                          .add("password", Password.getText().toString())
                          .add("answer1", question1.getText().toString())
                          .add("answer2", question2.getText().toString())
                          .build();


                  Request request = new Request.Builder()
                          .url("http://35.178.35.227:8080/Paramount/signup")
                          .post(formBody)
                          .build();
                  Call call = okHttpClient.newCall(request);

                  call.enqueue(new Callback() {
                      @Override
                      public void onFailure(Call call, IOException e) {
                          Log.d(TAG, e.getMessage());
                      }

                      @Override
                      public void onResponse(Call call, Response response) throws IOException {
                          if (response.isSuccessful()) {
                              System.out.println(String.valueOf(response.code()));
                              // String reusult1 = response.body().string();
                              try {
                                  JSONObject my = new JSONObject(response.body().string());
                                  System.out.println(my.getString("status"));
                                  System.out.println(my.getString("info"));
                                  String status1 = "success";
                                  String status2 = "";
                                  String rStatus1 = my.getString("status");
                                  String rStatus2 = my.getString("info");

                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if(rStatus1.equals(status1) && rStatus2.equals(status2))
                                          {
                                              Intent intent = new Intent(SignUp_Page.this, Login_Page.class);
                                              startActivity(intent);
                                          }
                                          else
                                          {
                                              Toast.makeText(SignUp_Page.this, "Please enter the correct information!", Toast.LENGTH_LONG).show();
                                          }

                                      }
                                  });



                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }


                          }


                      }
                  });
              }
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

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
