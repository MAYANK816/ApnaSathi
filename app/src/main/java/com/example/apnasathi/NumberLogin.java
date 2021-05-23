package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class NumberLogin extends AppCompatActivity {
    EditText mobileNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_login);
        mobileNumber = findViewById(R.id.editTextNumber);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        countryCodePicker.registerCarrierNumberEditText(mobileNumber);
    }

    public void Otpactivigty(View view) {
        if (TextUtils.isEmpty(mobileNumber.getText().toString())) {
            Toast.makeText(NumberLogin.this, "Enter a Number", Toast.LENGTH_SHORT).show();
        } else if (mobileNumber.getText().toString().replace(" ", "").length() != 10) {
            Toast.makeText(NumberLogin.this, "Enter a valid Number", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(NumberLogin.this, OtpActivity.class);
            intent.putExtra("number", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
            startActivity(intent);
            finish();
        }
    }
}