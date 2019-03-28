package com.example.mobile_client;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class Upload_Page extends AppCompatActivity {
    // pick file button
    Button Pick;




    private static final String TAG = "Upload_Page";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //pick file button connect to UI
        Pick = (Button)findViewById(R.id.pick);
        // get the access to system folder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //Log.v(TAG, "index=8");
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
              //  Log.v(TAG, "index=1");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_button();
    }

    //enable the pick button
    private  void enable_button()
    {
        Pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(Upload_Page.this)
                        .withRequestCode(10)
                        .start();
            }
        });
    }

    // access to the system file from phone
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults)
    {

        if(requestCode == 100 && (grandResults[0] == PackageManager.PERMISSION_GRANTED))
        {
            enable_button();

        }
        else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

            }
        }
    }

    //upload file
    ProgressDialog process;
   // ProgressDialog progress;
   private Call mCall;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String username = (String) MySharedPreferences.getuserName(Upload_Page.this);
        //super.onActivityResult(requestCode, resultCode, data);
        //send request to server

        if(requestCode == 10 && resultCode == RESULT_OK)
        {
            // set the message box to show uploading which can cancel before finished.
            process = new ProgressDialog(Upload_Page.this);
            process.setTitle("Uploading");
            process.setMessage("Please waiting");
            process.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    process.dismiss();
                    }
            });
            process.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //select the file
                    File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    // the type of file
                   // String content_type =  getMimeType(f.getPath());


                   // String file_path = f.getAbsolutePath();

                    OkHttpClient client = new OkHttpClient();



                   // RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    // request the body
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", f.getName(),
                                    RequestBody.create(MediaType.parse("multipart/form-data"), f))
                            .addFormDataPart("username", username)
                            .addFormDataPart("url","/")
                            .build();

                    Request request = new Request.Builder()
                            .url("http://teamparamount.cn:8080/Paramount/upload")
                            .post(requestBody)
                            .build();
                    mCall = client.newCall(request);
                    try{
//                        Response response = client.newCall(request).execute();
                        Response response = mCall.execute();

                        if(!response.isSuccessful()) {
                            throw new IOException("Error" + response);

                        }
                        else {
                            System.out.println(response);
                            process.dismiss();

                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Upload_Page.this, "Sorry, Cannot upload", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            t.start();
        }


    }
    //get the file's extension
    private String getMimeType(String path)
    {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

//menu
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
    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }







}
