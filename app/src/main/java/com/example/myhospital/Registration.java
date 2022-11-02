package com.example.myhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Registration extends AppCompatActivity {
    private TextView Goback;
    private Button btnDoctor;
    private Button btnPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Goback = findViewById(R.id.tvbackLog);

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent( Registration .this, MainActivity.class);
                startActivity(intentLog);
            }
        });
        btnDoctor = findViewById(R.id.regDoctor);

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDocReg = new Intent( Registration .this, RegDoctorForm.class);
                startActivity(intentDocReg);
            }
        });
        btnPatient = findViewById(R.id.regPatient);

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDocReg = new Intent( Registration .this, RegPatientForm.class);
                startActivity(intentDocReg);
            }
        });
    }
}