package com.example.mobile_client;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Item_detail_Page extends AppCompatActivity {
//  initial variables
    private ProgressDialog pDialog;


    private DownloadManager downloadManager;
    private Uri Download_Uri;
    private long refid;

    private EditText new_fileName;
    private EditText new_fileName2;
    private TextView old_fileName;
    private Button file_sumbit;
    private TextView Size;
    private TextView Type;
    private TextView Version;
    private TextView Time;
    private String TAG = "EditProfilePage";
    //String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
    //android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail__page);
        new_fileName = (EditText)findViewById(R.id.newFileName);
        new_fileName2 = (EditText)findViewById(R.id.newFileName2);
        old_fileName = (TextView)findViewById(R.id.oldFileName);
        file_sumbit = (Button)findViewById(R.id.f_button);
        Size = (TextView)findViewById(R.id.f_size);
        Type = (TextView)findViewById(R.id.f_type);
        Version = (TextView)findViewById(R.id.f_version);
        Time = (TextView)findViewById(R.id.f_time);
        // download manager
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String SelectedItem = intent.getStringExtra(("fileName"));
        old_fileName.setText("File Name：" + SelectedItem);
        detail(SelectedItem);
        file_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(new_fileName.getText().toString()) || TextUtils.isEmpty(new_fileName2.getText().toString())) {
                    Toast.makeText(Item_detail_Page.this, "Please fill all blanks", Toast.LENGTH_LONG).show();
                }else if((!new_fileName.getText().toString().equals(new_fileName2.getText().toString()) )){
                    Toast.makeText(Item_detail_Page.this, "Please enter the same file Name", Toast.LENGTH_LONG).show();

                }else{
                    rename(SelectedItem,new_fileName.getText().toString());






                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //get file name from home fragment
        Intent intent = getIntent();
        String SelectedItem = intent.getStringExtra(("fileName"));
        System.out.println(SelectedItem);
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_download:
                download(SelectedItem);
                //onRequestPermissionsResult(,,,SelectedItem);
                //Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                //download
                break;
            case R.id.action_delete:
                delete(SelectedItem);
                //Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                //delete
                break;
            case R.id.action_history:
                history(SelectedItem);

            default:
                //no error
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // download function
    public void download(String filename) {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            // this will request for permission when permission is not true
        }else{
            new AlertDialog.Builder(Item_detail_Page.this)
                    .setTitle("Download")
                    .setMessage("Are you sure you want to download this file?")

                     //set alert content
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
                            Download_Uri =Uri.parse("http://teamparamount.cn:8080/Paramount/download?username="+username+"&url="+filename);
                            System.out.println(Download_Uri);
                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(true);
                            request.setTitle(filename);
                            request.setDescription(filename);
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);


                            refid = downloadManager.enqueue(request);

                            Log.e("OUT", "" + refid);
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.presence_busy)
                    .show();

//            String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
//            Download_Uri =Uri.parse("http://teamparamount.cn:8080/Paramount/download?username="+username+"&url="+filename);
//            System.out.println(Download_Uri);
//            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//            request.setAllowedOverRoaming(true);
//            request.setTitle(filename);
//            request.setDescription(filename);
//            request.setVisibleInDownloadsUi(true);
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
//
//
//            refid = downloadManager.enqueue(request);
//
//            Log.e("OUT", "" + refid);

        }




    }
    //delete function
    public void delete(String fileName)
    {
        new AlertDialog.Builder(Item_detail_Page.this)
                .setTitle("Delete File")
                .setMessage("Are you sure you want to delete this file?")

                //set alert content
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        System.out.println("111111111111111111111111111111111" + fileName);
                        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
                        OkHttpClient okHttpClient = new OkHttpClient();

                        Request request = new Request.Builder()
                                .get()
                                .url("http://teamparamount.cn:8080/Paramount/delete?username=" + username+"&"+"url="+fileName)
                                .build();

                        Call call = okHttpClient.newCall(request);

                        System.out.println("gggggggggggggggggggggggggggg" + fileName);

                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println("fail to connect");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {

                                    System.out.println(String.valueOf(response.code()));

                                    System.out.println("cccccccccccccccccccccccccccccccccc" + fileName);
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
                                                if (rStatus1.equals(status1)) {


                                                    System.out.println("33333333333333333");
                                                    System.out.println(rStatus2);

                                                    System.out.println("55555555555555555555555555555555" + fileName);
                                                    Intent intent = new Intent(Item_detail_Page.this, Main_Page.class);
                                                    startActivity(intent);
                                                    Toast.makeText(Item_detail_Page.this, "The File has been deleted", Toast.LENGTH_LONG).show();


                                                } else {
                                                    System.out.println("did not delete");
                                                    Toast.makeText(Item_detail_Page.this, "The file can not delete !", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } catch (JSONException e) {
                                        // Toast.makeText(Login_Page.this, "Login fail", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();

                                    }


                                }
                                System.out.println("++++++++++++++++++++++++++++" + fileName);


                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.presence_busy)
                .show();


    }
    //rename function
    public void rename(String oldName,String newName)
    {
        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url("http://teamparamount.cn:8080/Paramount/rename?username=" + username+"&"+"url="+oldName+"&"+"newUrl="+newName)
                .build();

        Call call = okHttpClient.newCall(request);

        System.out.println("****************************1New File Name: " + newName);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail to connect");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    System.out.println(String.valueOf(response.code()));

                    System.out.println("****************************2New File Name: " +newName);
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
                                if (rStatus1.equals(status1)) {


                                    System.out.println("33333333333333333");
                                    System.out.println(rStatus2);

                                    System.out.println("****************************3New File Name: " +newName);
                                    Intent intent = new Intent(Item_detail_Page.this, Main_Page.class);
                                    startActivity(intent);
                                    Toast.makeText(Item_detail_Page.this, "The File name has been changed to"+ newName, Toast.LENGTH_LONG).show();


                                } else {
                                    System.out.println("did not delete");
                                    Toast.makeText(Item_detail_Page.this, "The file can not rename! ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }
                System.out.println("****************************4New File Name: " + newName);


            }
        });
    }
    //show file detail function
    public void detail(String fileName)
    {
        System.out.println("111111111111111111111111111111111");
        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url("http://teamparamount.cn:8080/Paramount/detail?username=" + username+"&url="+fileName)
                .build();

        Call call = okHttpClient.newCall(request);

        System.out.println("22222222222");

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail to connect");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    System.out.println(String.valueOf(response.code()));


                    try {
                        JSONObject my = new JSONObject(response.body().string());
                        System.out.println(my.getString("status"));
                        System.out.println("+++++++++++"+my.getString("info"));
//                        JSONArray arr = my.getJSONArray("info");
//
//
//                        for (int i = 0; i < arr.length(); i++)
//                        {
//                            String info_size = arr.getJSONObject(i).getString("size");
//                            String info_time = arr.getJSONObject(i).getString("time");
//                            String info_type = arr.getJSONObject(i).getString("type");
//                            String info_version = arr.getJSONObject(i).getString("version");
//                            System.out.println("+++++++++++"+info_size);
//                            System.out.println("----------"+info_time);
//                            System.out.println("+++++++++++"+info_type);
//                            System.out.println("-----------"+info_version);
//
//
//                        }
                       // System.out.println("+++++++++++"+info_size);

                        String status1 = "success";
                        String status2 = "";
                        String rStatus1 = my.getString("status");
                        String rStatus2 = my.getString("info");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (rStatus1.equals(status1)) {
                                    // parse file name from server
//                                    Size = (TextView)findViewById(R.id.f_size);
//                                    Type = (TextView)findViewById(R.id.f_type);
//                                    Version = (TextView)findViewById(R.id.f_version);
//                                    Time = (TextView)findViewById(R.id.f_time);
                                   String newString = rStatus2.replace("{", "");
                                   String newString1 = newString.replace("size", "");
                                    String newString2 = newString1.replace("type", "");
                                    String newString3 = newString2.replace("time", "");
                                    String newString4 = newString3.replace("version", "");
                                    String newString5 = newString4.replace("\"\":", "");
                                    String newString6 = newString5.replace("\"", "");


                                   System.out.println("json string =  "+newString6);
                                   String[] strs = newString6.split(",");

                                    Size.setText(strs[0]);
                                    Type.setText(strs[2]);
                                    Version.setText(strs[3]);
                                    Time.setText(strs[1]);


//                                    for (int i = 0, len = strs.length; i < len; i++)
//                                    {
//                                        System.out.println(strs[i].toString());
////                                        String size1 = strs[i].toString();
////                                        System.out.println(size1);
//                                        //Size.setText(size1);
////                                        Type.setText(strs[i].toString());
////                                        Version.setText(strs[i].toString());
////                                        Time.setText(strs[i].toString());
//
//                                    }















                                    //System.out.println("file:" + rStatus2);


                                } else {
                                    System.out.println("did not get json file");

                                }

                            }
                        });

                    } catch (JSONException e) {
                        // Toast.makeText(Login_Page.this, "Login fail", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }


                }


            }
        });

        //}
    }
    // find the old version of file
    public void history(String fileName)
    {
        new AlertDialog.Builder(Item_detail_Page.this)
                .setTitle("File version history")
                .setMessage("Are you sure you want to revert this file?")

                //set alert content
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        System.out.println("111111111111111111111111111111111" + fileName);
                        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
                        OkHttpClient okHttpClient = new OkHttpClient();

                        Request request = new Request.Builder()
                                .get()
                                .url("http://teamparamount.cn:8080/Paramount/revert?username=" + username+"&"+"url="+fileName)
                                .build();

                        Call call = okHttpClient.newCall(request);

                        System.out.println("gggggggggggggggggggggggggggg" + fileName);

                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println("fail to connect");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {

                                    System.out.println(String.valueOf(response.code()));

                                    System.out.println("cccccccccccccccccccccccccccccccccc" + fileName);
                                    try {
                                        JSONObject my = new JSONObject(response.body().string());
                                        System.out.println(my.getString("status"));
                                        System.out.println(my.getString("info"));


                                        String status1 = "success";
                                        String status2 = "can not revert first version";
                                        String rStatus1 = my.getString("status");
                                        String rStatus2 = my.getString("info");


                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (rStatus1.equals(status1)) {


                                                    System.out.println("33333333333333333");
                                                    System.out.println(rStatus2);

                                                    Toast.makeText(Item_detail_Page.this, "Success!", Toast.LENGTH_SHORT).show();
                                                    System.out.println("55555555555555555555555555555555" + fileName);
                                                    Intent intent = new Intent(Item_detail_Page.this, Main_Page.class);
                                                    startActivity(intent);
                                                    Toast.makeText(Item_detail_Page.this, "Success!", Toast.LENGTH_SHORT).show();
                                                    //Toast.makeText(Item_detail_Page.this, "The File name has been changed to"+newName, Toast.LENGTH_LONG).show();





                                                } else {
                                                    System.out.println("did not revert");
                                                    Toast.makeText(Item_detail_Page.this, "The file can not revert first version", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    } catch (JSONException e) {
                                        // Toast.makeText(Login_Page.this, "Login fail", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();

                                    }


                                }
                                System.out.println("++++++++++++++++++++++++++++" + fileName);


                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.presence_busy)
                .show();

    }


}
