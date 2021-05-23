package com.example.apnasathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.fragments.BuyFragment;
import com.example.fragments.SellFragment;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

public class buysell extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buysell);
    }

    public void sellFragment(View view) {
        SellFragment buyFragment=new SellFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linearLayout1,buyFragment);
        fragmentTransaction.commit();
    }

    public void buyFragment(View view) {
        BuyFragment buyFragment=new BuyFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linearLayout1,buyFragment);
        fragmentTransaction.commit();
    }
}