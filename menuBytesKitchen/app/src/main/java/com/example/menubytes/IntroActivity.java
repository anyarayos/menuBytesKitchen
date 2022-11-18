package com.example.menubytes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {
    private Button loginBtn;
    private EditText kitchenUserName;
    private EditText kitchenPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        kitchenUserName = findViewById(R.id.kitchenUserName);
        kitchenPassword = findViewById(R.id.kitchenPassword);

        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = kitchenUserName.getText().toString();
                String password = kitchenPassword.getText().toString();
                if(user_name.equals("") || password.equals("")){
                    Toast.makeText(IntroActivity.this, "Do not leave any fields blank!", Toast.LENGTH_SHORT).show();
                }else{
                    Task checkUserNameExistence = new Task(Task.CHECK_USER_NAME_EXISTENCE, new AsyncResponse() {
                        @Override
                        public void onFinish(Object output) {
                            if(output!=null){
                                Task checkUserNamePassword = new Task(Task.CHECK_USER_NAME_PASSWORD, new AsyncResponse() {
                                    @Override
                                    public void onFinish(Object output) {
                                        if(output!=null){
                                            Intent i = new Intent(IntroActivity.this, MainActivity.class);
                                            i.putExtra("user_id",(String)output);
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(IntroActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(IntroActivity.this, "Username does not exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }); checkUserNameExistence.execute(user_name);
                }
                /*
                * if(username is kitchen device)
                *   if(username and password match)
                *   start activity
                * */


            }
        });

    }
}