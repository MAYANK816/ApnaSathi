package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ExpertOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_options);

    }

    public void expertQuery(View view) {
        startActivity(new Intent(ExpertOptions.this, QueryforExpert.class));
    }

    public void callExpert(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+91-1800-180-1551"));
        startActivity(intent);
    }
}