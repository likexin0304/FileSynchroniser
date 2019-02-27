package com.example.mobile_client;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    //String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);
    //android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail__page);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            case R.id.action_download:
                download();
                Toast.makeText(getApplicationContext(), "Downloaded", Toast.LENGTH_SHORT).show();
                //download
                break;
            case R.id.action_delete:
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

    public void download() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DOWNLOADS);
        String dirPath = dir.getAbsolutePath();
        System.out.println(dirPath);
        OkHttpClient okHttpClient = new OkHttpClient();

        String username = (String) MySharedPreferences.getuserName(Item_detail_Page.this);


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //.addFormDataPart("file", f.getName(),
                       // RequestBody.create(MediaType.parse("multipart/form-data"), f))
                .addFormDataPart("username", username)
                .addFormDataPart("url","SIA_Tutorial___01.pdf")
                //.addFormDataPart("filename","04.pdf")
                .build();

        Request request = new Request.Builder()
                .url("http://teamparamount.cn:8080/Paramount/download")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {





            public void onFailure(Call call, IOException e) {
                // 下载失败
                System.out.println("Download Fail");
            }

            public void onResponse(Call call, Response response)  {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(dirPath, "05.pdf");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                    }
                    fos.flush();
                    // 下载完成
                } catch (Exception e) {
                    System.out.println("Download Error");
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

//    public void delete()
//    {
//        System.out.println("111111111111111111111111111111111");
//
//    OkHttpClient okHttpClient = new OkHttpClient();
//
//    Request request = new Request.Builder()
//            .get()
//            .url("http://teamparamount.cn8080/Paramount/delete?username=" + username +"ulr=")
//            .build();
//
//    Call call = okHttpClient.newCall(request);
//
//            System.out.println("22222222222");
//
//            call.enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            System.out.println("fail to connect");
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            if (response.isSuccessful()) {
//
//                System.out.println(String.valueOf(response.code()));
//
//
//                try {
//                    JSONObject my = new JSONObject(response.body().string());
//                    System.out.println(my.getString("status"));
//                    System.out.println(my.getString("info"));
//
//                    //Toast.makeText(Login_Page.this,my.getString("status"),Toast.LENGTH_SHORT).show();
//                    String status1 = "success";
//                    String status2 = "";
//                    String rStatus1 = my.getString("status");
//                    String rStatus2 = my.getString("info");
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (rStatus1.equals(status1)) {
//
//
//
//                                System.out.println("success");
//
//
//
//
//
//                            } else {
//                                System.out.println("did not delete");
//                            }
//
//                        }
//                    });
//
//                } catch (JSONException e) {
//                    // Toast.makeText(Login_Page.this, "Login fail", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//
//                }
//
//
//            }
//
//
//        }
//    });
//
//    //}
//}



}
