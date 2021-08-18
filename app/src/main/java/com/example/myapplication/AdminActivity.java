package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding adminBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(adminBinding.getRoot());

        returnMain();
        registerAdmin();
    }

    void returnMain(){
        adminBinding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    void registerAdmin(){
        adminBinding.adminregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = true;
                Intent registerIntent = new Intent(AdminActivity.this, SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }
}