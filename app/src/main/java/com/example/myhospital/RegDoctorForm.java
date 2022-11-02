package com.example.myhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegDoctorForm extends AppCompatActivity {
    private TextView Gobacklog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regdoctorform);
        Gobacklog = findViewById(R.id.tvBack);

        Gobacklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent( RegDoctorForm .this, Registration.class);
                startActivity(intentLog);
            }
        });
    }


}