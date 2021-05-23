package com.example.apnasathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class EmailLogin extends AppCompatActivity {
    EditText userEmail1,userPassword;
    private FirebaseAuth mAuth;
    TextView newAccount;
    String loggedin="";
    boolean Login_success=false;
    boolean done_login=false;
    String password="";
    ProgressBar progressBar;
    boolean isEmailVerified=false;
    String email="";
    CheckBox remember;
    final static String KEY_NAME="mypref";
    final static String KEY_CHECKBOX="rememberme";
    final static String email_verified="false";
    final static String KEY_UMail="android@gmail.com";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        init();
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmailLogin.this,EmailSignUp.class);
                startActivity(intent);
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    sharedPreferences=getSharedPreferences(KEY_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(KEY_CHECKBOX,"true");
                    editor.putString(KEY_UMail,userEmail1.getText().toString());
                    editor.apply();
                }
                else
                {
                    sharedPreferences=getSharedPreferences(KEY_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(KEY_CHECKBOX,"false");
                    editor.putString(KEY_UMail,userEmail1.getText().toString());
                    editor.apply();

                }
            }
        });
    }
    private void init() {
        mAuth = FirebaseAuth.getInstance();
        userEmail1=findViewById(R.id.user_email_login);
        newAccount=findViewById(R.id.newAccount);
        userPassword=findViewById(R.id.user_pass_login);
        remember=findViewById(R.id.checkBox);
        sharedPreferences=getSharedPreferences(KEY_NAME,MODE_PRIVATE);
        loggedin=sharedPreferences.getString(KEY_CHECKBOX,"");
        done_login=sharedPreferences.getBoolean("Login_Successful",false);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);


    }

    @Override
    public void onStart() {
        super.onStart();
        if(loggedin.equals("true") && done_login){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
    }
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Login_success=true;
            progressBar.setVisibility(View.INVISIBLE);
            sharedPreferences=getSharedPreferences(KEY_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("Login_Successful",Login_success);
            editor.putString(email_verified,"true");
            editor.apply();
            Intent intent=new Intent(EmailLogin.this,MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    public void singIn(View view) {
        progressBar.setVisibility(View.VISIBLE);
        email=userEmail1.getText().toString();
        password=userPassword.getText().toString();
        if(email.isEmpty() && password.isEmpty())
        {
            Toast.makeText(EmailLogin.this, "Fill The Details.",
                    Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerificaation();
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(e instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        userPassword.setError("Invalid Password");
                        userPassword.requestFocus();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                    else  if(e instanceof FirebaseAuthInvalidUserException){

                        userEmail1.setError("Invalid Email");
                        userEmail1.requestFocus();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(EmailLogin.this, "Please Check Your Connection Or Change Your City to Sirsa.",
                                Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
    private void sendEmailVerificaation() {

        isEmailVerified=mAuth.getCurrentUser().isEmailVerified();
        if(mAuth.getCurrentUser()!=null)
        {

            if(isEmailVerified )
            {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            }
            else {
                Toast.makeText(EmailLogin.this, " Please Verify Your Mail " ,
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

}