package com.example.mobile_client;

import android.content.Intent;
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

public class  EditProfilePage extends AppCompatActivity {

    //initial all variables
    private EditText old_password;
    private EditText new_password;
    private TextView Username;
    private Button edit_sumbit;
    private String TAG = "EditProfilePage";
    //private Button go_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        // action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // initial all variables from UI
        old_password = (EditText)findViewById(R.id.oldPassword);
        new_password = (EditText)findViewById(R.id.newPassword);
        Username = (TextView)findViewById(R.id.Username);
        edit_sumbit = (Button)findViewById(R.id.e_sumbit);
        //go_Back = (Button)findViewById(R.id.e_back);


        //get username from SharedPreferences
        String username = (String) MySharedPreferences.getuserName(EditProfilePage.this);
        Username.setText("Username：" + username);
        // change password button function
        edit_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // check input
                if (TextUtils.isEmpty(old_password.getText().toString()) || TextUtils.isEmpty(new_password.getText().toString())) {
                    Toast.makeText(EditProfilePage.this, "Please fill all blanks", Toast.LENGTH_LONG).show();
                } else {

                    // connect to server and change password
                    OkHttpClient okHttpClient = new OkHttpClient();
                    //requestbody
                    FormBody formBody = new FormBody.Builder()
                            .add("username", username)
                            .add("password", old_password.getText().toString())
                            .add("newpassword", new_password.getText().toString())

                            .build();

                    // builder request body
                    Request request = new Request.Builder()
                            .url("http://teamparamount.cn:8080/Paramount/changepassword")
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d(TAG, e.getMessage());
                        }
                        // response  success
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
                                                Intent intent = new Intent(EditProfilePage.this, Main_Page.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(EditProfilePage.this, "Please enter the correct information!", Toast.LENGTH_LONG).show();
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
