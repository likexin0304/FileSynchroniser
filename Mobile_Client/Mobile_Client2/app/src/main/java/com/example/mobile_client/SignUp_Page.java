package com.example.mobile_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__page);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Username = (EditText)findViewById(R.id.Sign_Username);
        Password = (EditText)findViewById(R.id.Sign_password);
        Repassword = (EditText)findViewById(R.id.Sign_repassword);
        question1 = (EditText)findViewById(R.id.Sign_question1);
        question2 = (EditText)findViewById(R.id.Sign_question2);
        Submit = (Button)findViewById(R.id.Sign_button);
        //HaveAccount = (TextView)findViewById(R.id.tv_haveAccount);



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_up();
            }
        });



    }
    private void sign_up()
    {
        Intent intent = new Intent(this, Login_Page.class);
        startActivity(intent);
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
