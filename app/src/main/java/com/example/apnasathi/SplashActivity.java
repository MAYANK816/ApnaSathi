package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    final static String KEY_NAME="mypref";
    final static String KEY_CHECKBOX="rememberme";
    final static String email_verified="false";
    final static String KEY_UMail="android@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        String verified=sharedPreferences.getString(email_verified, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser()!=null && verified.equals("true") ) {
                    Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
               else { Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
               }
            }
        },3000);
    }
}