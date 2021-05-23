package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cropverification extends AppCompatActivity {
    EditText Name, Number, Address, Area, CropType;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String farmerName,farmerNumber,farmerAddress,farmerArea,farmerCropType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropverification);
        init();
    }

    private void init() {
        Name = findViewById(R.id.farmername);
        Number = findViewById(R.id.farmernumber);
        Address = findViewById(R.id.farmeraddress);
        Area = findViewById(R.id.farmerarea);
        CropType = findViewById(R.id.croptype);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cropcertification");
    }

    public void submit(View view) {
        farmerName=Name.getText().toString();
        farmerNumber=Number.getText().toString();
        farmerAddress=Address.getText().toString();
        farmerArea=Area.getText().toString();
        farmerCropType=CropType.getText().toString();
        if(TextUtils.isEmpty(farmerName) || TextUtils.isEmpty(farmerAddress) || TextUtils.isEmpty(farmerNumber) || TextUtils.isEmpty(farmerArea) || TextUtils.isEmpty(farmerCropType)){
            Toast.makeText(getApplicationContext(),"Check the details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            certificateModel certificateModel=new certificateModel(farmerName,farmerNumber,farmerAddress,farmerArea,farmerCropType);
            String mdid=myRef.push().getKey();
            myRef.child(mdid).setValue(certificateModel);
            Name.setText("");
            Number.setText("");
            Area.setText("");
            Address.setText("");
            CropType.setText("");
            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
        }
    }
}