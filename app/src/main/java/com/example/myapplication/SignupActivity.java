package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivitySignupBinding;

import java.util.List;
import java.util.Objects;

import Utils.passwordHash;
import ViewModel.PersonViewModel;
import roomTest.Person;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding sign_binding;
    private PersonViewModel mPersonViewModel;
    private List<Person> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sign_binding = ActivitySignupBinding.inflate(getLayoutInflater());
        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        setContentView(sign_binding.getRoot());
        Boolean admin = getIntent().getBooleanExtra("admin", false);

        //Get all the existent users
        mPersonViewModel.getAllPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                try{
                    listUsers = Objects.requireNonNull(people);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        signUp(admin);
        returnSignIn();
    }

    //Return for the Login Screen
    void returnSignIn(){
        sign_binding.returnBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(SignupActivity.this, MainActivity2.class);
                startActivity(signinIntent);
            }
        });
    }

    //Main function for the signUp
    private void signUp(Boolean admin){
        sign_binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean userCheck = false;

                String email = sign_binding.emailEditText.getText().toString().toLowerCase();
                String username = sign_binding.usernameEditText.getText().toString();
                String password = sign_binding.passwordEditText.getText().toString();
                String name = sign_binding.nameEditText.getText().toString().toLowerCase();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)){
                    Toast.makeText(SignupActivity.this, "You must fill in all fields!", Toast.LENGTH_LONG).show();
                }else{
                    for (int i = 0; i< listUsers.size(); i++){
                        if(listUsers.get(i).getUserName().equals(username)){
                            userCheck = true;
                            break;
                        }
                    }
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    Boolean checkEmail = email.matches(emailPattern);
                    if(userCheck == true){
                        Toast.makeText(SignupActivity.this, "This username already exists", Toast.LENGTH_LONG).show();
                        userCheck = false;
                    }else{
                        if(checkEmail){
                            password = passwordHash.getMd5(password);
                            Person newUser = new Person(username, email, password, name, admin);
                            mPersonViewModel.insert(newUser);
                            checkEmail = false;
                            Intent inicialIntent = new Intent(SignupActivity.this, MainActivity2.class);
                            startActivity(inicialIntent);
                        }else{
                            Toast.makeText(SignupActivity.this, "This email is invalid", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
}