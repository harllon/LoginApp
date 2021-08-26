package ActivitiesNotUsed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.MainActivity2;
import com.example.myapplication.databinding.ActivityNormalBinding;

public class NormalActivity extends AppCompatActivity {
    private ActivityNormalBinding normal_binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        normal_binding = ActivityNormalBinding.inflate(getLayoutInflater());
        setContentView(normal_binding.getRoot());

        returnSign();
    }
    void returnSign(){
        normal_binding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(NormalActivity.this, MainActivity2.class);
                startActivity(signinIntent);
            }
        });
    }
}