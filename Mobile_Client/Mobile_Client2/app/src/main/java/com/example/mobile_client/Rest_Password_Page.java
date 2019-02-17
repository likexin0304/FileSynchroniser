package com.example.mobile_client;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Rest_Password_Page extends AppCompatActivity {
    //r_username
    //tv_Sq1
    //tv_Sq2
    //Answer1
    //Answer2
    //r_password
    //r_repassword
    //r_sumbit

    private EditText r_Username;
    private EditText r_Answer1;
    private EditText r_Answer2;
    private TextView Security_question1;
    private TextView Security_question2;
    private EditText r_Password;
    private EditText r_Repassword;
    private Button r_Sumbit;
    private Button r_Back;

    private String TAG = "Rest_Password_Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest__password__page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        r_Username = (EditText)findViewById(R.id.r_username);
        r_Answer1 = (EditText)findViewById(R.id.Answer1);
        r_Answer2 = (EditText)findViewById(R.id.Answer2);
        Security_question1 = (TextView)findViewById(R.id.tv_Sq1);
        Security_question2 = (TextView)findViewById(R.id.tv_Sq2);
        r_Password = (EditText)findViewById(R.id.r_password);
        r_Repassword =(EditText)findViewById(R.id.r_repassword);
        r_Sumbit = (Button)findViewById(R.id.r_sumbit);
        //r_Back = (Button)findViewById(R.id.bt_back);


        r_Sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goTohome();


                if (TextUtils.isEmpty(r_Username.getText().toString()) || TextUtils.isEmpty(r_Answer1.getText().toString()) ||
                        TextUtils.isEmpty(r_Answer2.getText().toString()) ||TextUtils.isEmpty(r_Password.getText().toString()))
                {
                    Toast.makeText(Rest_Password_Page.this, "Please fill all blanks", Toast.LENGTH_LONG).show();
                }
                else {

                    OkHttpClient okHttpClient = new OkHttpClient();
                    //requestbody
                    FormBody formBody = new FormBody.Builder()
                            .add("username", r_Username.getText().toString())
                            .add("answer1", r_Answer1.getText().toString())
                            .add("answer2", r_Answer2.getText().toString())
                            .add("password", r_Password.getText().toString())
                            .build();


                    Request request = new Request.Builder()
                            .url("http://35.178.35.227:8080/Paramount/forget")
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
                                            if (rStatus1.equals(status1) && rStatus2.equals(status2)) {
                                                Intent intent = new Intent(Rest_Password_Page.this, Login_Page.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Rest_Password_Page.this, "Please enter the correct information!", Toast.LENGTH_LONG).show();
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

//        r_Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goBack();
//
//            }
//        });



    private void goTohome()
    {
        Intent intent = new Intent(Rest_Password_Page.this, Login_Page.class);
        startActivity(intent);
    }
//    private void goBack()
//    {
//        Intent intent = new Intent(Rest_Password_Page.this, Login_Page.class);
//        startActivity(intent);
//    }
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
