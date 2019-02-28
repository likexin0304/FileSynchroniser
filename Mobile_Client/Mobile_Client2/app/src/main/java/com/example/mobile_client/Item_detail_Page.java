package com.example.mobile_client;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    private ProgressDialog pDialog;


    private DownloadManager downloadManager;
    private Uri Download_Uri;
    private long refid;

    //String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
    //android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail__page);


        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

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
                Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                //download
                break;
            case R.id.action_delete:
                delete(SelectedItem);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                //delete
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
        inflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    ProgressDialog process;
    public void download(String filename) {
//        process = new ProgressDialog(this);
//        process.setTitle("Downloading");
//        process.setMessage("Please waiting");
//        process.show();
////        File dir = Environment.getExternalStoragePublicDirectory(Environment
////                .DIRECTORY_DOWNLOADS);
//        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//
//        String dirPath = dir.getAbsolutePath();
//        System.out.println(dirPath);
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
//
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                //.addFormDataPart("file", f.getName(),
//                // RequestBody.create(MediaType.parse("multipart/form-data"), f))
//                .addFormDataPart("username", username)
//                .addFormDataPart("url",filename)
//                //.addFormDataPart("filename","04.pdf")
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://teamparamount.cn:8080/Paramount/download")
//                .post(requestBody)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//
//
//
//
//
//            public void onFailure(Call call, IOException e) {
//                // error
//                System.out.println("Download Fail");
//            }
//
//            public void onResponse(Call call, Response response)  {
//                System.out.println("11111111---"+filename);
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                // save download file
//                try {
//                    is = response.body().byteStream();
//                    long total = response.body().contentLength();
//                    File file = new File(dirPath, filename);
//                    System.out.println("222222222222---"+filename);
//                    fos = new FileOutputStream(file);
//                    long sum = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                        sum += len;
//                        int progress = (int) (sum * 1.0f / total * 100);
//
//                        // downloading
//                    }
//                    fos.flush();
//                    // finished.
//                    process.dismiss();
//                } catch (Exception e) {
//                    System.out.println("Download Error");
//                } finally {
//                    try {
//                        if (is != null)
//                            is.close();
//                    } catch (IOException e) {
//                    }
//                    try {
//                        if (fos != null)
//                            fos.close();
//                    } catch (IOException e) {
//                    }
//                }
//            }
//        });

        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
        Download_Uri =Uri.parse("http://teamparamount.cn:8080/Paramount/download?username="+username+"&url="+filename);

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

    public void delete(String fileName)
    {
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



                            } else {
                                System.out.println("did not delete");
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




}
