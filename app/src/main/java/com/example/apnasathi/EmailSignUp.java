package com.example.apnasathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailSignUp extends AppCompatActivity {
    EditText userregEmail1,userregPassword1,userregPassword2;
    Button singupbtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    final static String KEY_NAME="mypref";
//    final static String KEY_CHECKBOX="rememberme";
   final static String email_verified="false";
    final static String KEY_UMail="android@gmail.com";

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);
        init();
    }
    private void init() {
        mAuth = FirebaseAuth.getInstance();
        userregEmail1=findViewById(R.id.user_email_login);
        progressBar=findViewById(R.id.spin_kit);
        userregPassword1=findViewById(R.id.user_pass_login);
        userregPassword2=findViewById(R.id.user_confirm_pass_login);
        singupbtn=findViewById(R.id.button);
        userregEmail1.setVisibility(View.VISIBLE);
        userregPassword1.setVisibility(View.VISIBLE);

    }

    public void signUp(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email1=userregEmail1.getText().toString();
        String password1=userregPassword1.getText().toString();
        String password2=userregPassword2.getText().toString();

        if(email1.isEmpty() && password1.isEmpty() && password2.isEmpty() && (!(password1.equals(password2))))
        {
            Toast.makeText(EmailSignUp.this, "Fill The Details.",
                    Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }

        else{

            singupbtn.setVisibility(View.INVISIBLE);
            mAuth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.VISIBLE);
                                userregEmail1.setVisibility(View.INVISIBLE);
                                userregPassword1.setVisibility(View.INVISIBLE);
                                userregPassword2.setVisibility(View.INVISIBLE);
                                sendEmail();
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EmailSignUp.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
        
    }

    private void sendEmail() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful() ){
                        sendToLogin();
                    }
                }
            });
        }
    }

    private void sendToLogin() {
        Toast.makeText(EmailSignUp.this, "GO Verify your Mail",
                Toast.LENGTH_SHORT).show();
        datasave();
    }

    private void datasave() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(EmailSignUp.this,EmailLogin.class));
        finish();
        sharedPreferences=getSharedPreferences(KEY_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_UMail,userregEmail1.getText().toString());
        editor.putString(email_verified,"false");
        editor.apply();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}