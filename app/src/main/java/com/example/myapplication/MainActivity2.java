package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.databinding.ActivityMain2Binding;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding main2Binding;
    //public ActionBarDrawerToggle actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main2Binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(main2Binding.getRoot());
        //actionBar = new ActionBarDrawerToggle(this, main2Binding.drawerID, R.string.nav_open, R.string.nav_close);
        //main2Binding.drawerID.addDrawerListener(actionBar);
        //actionBar.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBar.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}