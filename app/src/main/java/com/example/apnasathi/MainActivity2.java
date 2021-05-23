package com.example.apnasathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView nav;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    DatabaseReference myRef;
    Toolbar toolbar;
    Adapter adapter;
    List<slideImage> helperList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.event_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext(), helperList);
        recyclerView.setAdapter(adapter);
        nav = findViewById(R.id.navmenu);
        nav.bringToFront();
        drawerLayout = findViewById(R.id.drawer);
        mAuth = FirebaseAuth.getInstance();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("slider");
        nav.setCheckedItem(R.id.menu_home);
        nav.setNavigationItemSelectedListener(this);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    slideImage helper = new slideImage();
                    String imageURL = dataSnapshot.getValue().toString();
                    helper.setImage(imageURL);
                    helperList.add(helper);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                finishAffinity();
                break;

            case R.id.menu_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91-8168196670"));
                startActivity(intent);
                break;

            case R.id.menu_about:
                Toast.makeText(getApplicationContext(), "About Section", Toast.LENGTH_LONG).show();

                break;
            case R.id.menu_logout:

                mAuth.signOut();
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finishAffinity();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cropverify(View view) {
        startActivity(new Intent(MainActivity2.this, cropverification.class));
    }

    public void cropInsurance(View view) {
        startActivity(new Intent(MainActivity2.this, CropInsurance.class));
    }

    public void expertAdvice(View view) {
        startActivity(new Intent(MainActivity2.this, ExpertOptions.class));
    }

    public void buysell(View view) {
        startActivity(new Intent(MainActivity2.this,buysell.class));
    }
}