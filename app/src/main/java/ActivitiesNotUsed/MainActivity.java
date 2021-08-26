package ActivitiesNotUsed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.SignupActivity;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

import Utils.passwordHash;
import ViewModel.PersonViewModel;
import roomTest.Person;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding main_binding;
    private PersonViewModel mPersonViewModel;
    private List<Person> listUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_binding = ActivityMainBinding.inflate(getLayoutInflater());
        mPersonViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        setContentView(main_binding.getRoot());
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

        SignIn();
        SignUp();
    }
    void SignUp(){
        main_binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean admin = false;
                Intent registerIntent = new Intent(MainActivity.this, SignupActivity.class);
                registerIntent.putExtra("admin", admin);
                startActivity(registerIntent);
            }
        });
    }
    void SignIn(){
        main_binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkUsername = false;
                Boolean checkPassword = false;
                Boolean checkAdmin = false;
                if(TextUtils.isEmpty(main_binding.PasswordEditText.getText().toString()) || TextUtils.isEmpty(main_binding.UserNameEditText.getText().toString())){
                    Toast.makeText(MainActivity.this, "You must fill in all fields", Toast.LENGTH_LONG).show();
                }else{
                    for (int i = 0; i< listUsers.size(); i++){
                        if(listUsers.get(i).getUserName().equals(main_binding.UserNameEditText.getText().toString())){
                            checkUsername = true;
                        }
                        if(checkUsername) {
                            String password = passwordHash.getMd5(main_binding.PasswordEditText.getText().toString());
                            if (listUsers.get(i).getUserPassword().equals(password)){
                                checkPassword = true;
                                checkAdmin = listUsers.get(i).getIsAdmin();
                                break;
                            }
                        }
                    }
                }
                if(checkUsername && checkPassword){
                    //Toast.makeText(MainActivity.this, "The user exist", Toast.LENGTH_LONG).show();
                    if(checkAdmin){
                        Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(adminIntent);
                    }else{
                        Intent normalIntent = new Intent(MainActivity.this, NormalActivity.class);
                        startActivity(normalIntent);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Username or password is wrong.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}