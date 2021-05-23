package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QueryforExpert extends AppCompatActivity {
    EditText farmerName, farmerNumber, farmerQuery;
    String Name, Number, Query;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryfor_expert);
        init();
    }

    private void init() {
        farmerName = findViewById(R.id.farmername);
        farmerNumber = findViewById(R.id.farmernumber);
        farmerQuery = findViewById(R.id.expertQuery);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("QueryforExpert");
    }

    public void submit(View view) {
        Name = farmerName.getText().toString();
        Number = farmerNumber.getText().toString();
        Query = farmerQuery.getText().toString();
        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Number) || TextUtils.isEmpty(Query)) {
            Toast.makeText(getApplicationContext(), "Check the details", Toast.LENGTH_SHORT).show();
        } else {
            queryModel certificateModel = new queryModel(Name, Number, Query);
            String mdid = myRef.push().getKey();
            myRef.child(mdid).setValue(certificateModel);
            farmerName.setText("");
            farmerNumber.setText("");
            farmerQuery.setText("");
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }
}