package com.example.myhospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegDoctorForm extends AppCompatActivity {
    private EditText eName,eSurname,eAddress,eId,ePhoneNo,eMail,ePassWord,eRePassword;
    private CheckBox cbFemale,cbMale,cbUnisex;
    private Spinner spSpecialization,spAvailabity;
    private Button btnRegisterP;
    private TextView Gobacklog;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regdoctorform);
        Gobacklog = findViewById(R.id.tvBack);
        eName = findViewById(R.id.txtName);
        eSurname = findViewById(R.id.txtSurname);
        eAddress = findViewById(R.id.txtPostalAddress);
        eId = findViewById(R.id.txtID);
        ePhoneNo = findViewById(R.id.txtPhone);
        eMail = findViewById(R.id.txtloginEmail);
        ePassWord = findViewById(R.id.txtcreatePassword);
        eRePassword = findViewById(R.id.txtrePassword);
        cbFemale = findViewById(R.id.checkFamale);
        cbMale = findViewById(R.id.checkMale);
        cbUnisex = findViewById(R.id.checkUniSex);
        btnRegisterP = findViewById(R.id.btnReg);
        spAvailabity = findViewById(R.id.spAvailabity);
        spSpecialization = findViewById(R.id.spSpecialization);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btnRegisterP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String email = eMail.getText().toString().trim();
                final  String phoneNO = ePhoneNo.getText().toString().trim();
                final  String password = ePassWord.getText().toString().trim();
                final  String rePassword = eRePassword.getText().toString().trim();
                final  String name = eName.getText().toString().trim();
                final  String surname = eSurname.getText().toString().trim();
                final  String address = eAddress.getText().toString().trim();
                final  String id = eId.getText().toString().trim();
                String availabity = spAvailabity.getSelectedItem().toString();
                String specialization = spSpecialization.getSelectedItem().toString();


                if (TextUtils.isEmpty(email)) {
                    eMail.setError("Phone NO require");
                }


                if (TextUtils.isEmpty(phoneNO)) {
                    ePhoneNo.setError("Phone number require");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    eName.setError("Name is require");
                    return;
                }

                if (TextUtils.isEmpty(surname)) {
                    eSurname.setError("Surname is require");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ePassWord.setError("Password is require");
                    return;
                }
                if (TextUtils.isEmpty(rePassword)) {
                    eRePassword.setError("Re-enter is password require");
                    return;
                }
                if (!rePassword.equals(password)) {
                    eRePassword.setError("Password do not match");
                    ePassWord.setError("Password do not match");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    eAddress.setError("Address is require");
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    eId.setError("Address is require");
                }else{
                    loader.setMessage("Registration in prograss..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task){
                                    if(!task.isSuccessful()){
                                        String error =  task.getException().toString();
                                        Toast.makeText(RegDoctorForm.this, "Error occured: " + error, Toast.LENGTH_SHORT).show();
                                    }else{
                                        String currentUserId = mAuth.getCurrentUser().getUid();
                                        userDatabaseRef = FirebaseDatabase.getInstance().getReference()
                                                .child("user").child(currentUserId);
                                        HashMap userInfo = new HashMap();
                                        userInfo.put("name",name);
                                        userInfo.put("email",email);
                                        userInfo.put("surname",surname);
                                        userInfo.put("address",address);
                                        userInfo.put("phonecontact",phoneNO);
                                        userInfo.put("type","patient");
                                        userInfo.put("password",password);
                                        userInfo.put("availabity",availabity);
                                        userInfo.put("specialization",specialization);
                                        if(cbFemale.isChecked()){
                                            userInfo.put("gander","female");

                                        }else if(cbMale.isChecked()){
                                            userInfo.put("gander","male");
                                        }else if(cbUnisex.isChecked()){
                                            userInfo.put("gander","unisex");
                                        }

                                        userDatabaseRef.updateChildren(userInfo)
                                                .addOnCompleteListener(new OnCompleteListener(){
                                                    @Override
                                                    public void onComplete(@NonNull Task task){
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegDoctorForm.this, "Details set successfull", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(RegDoctorForm.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                        finish();
                                                        loader.dismiss();
                                                    }
                                                });
                                    }


                                }
                            });
                }

                Intent intentProfile = new Intent( RegDoctorForm .this, MainActivity.class);
                startActivity(intentProfile);
            }
        });

        Gobacklog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLog = new Intent( RegDoctorForm .this, Registration.class);
                startActivity(intentLog);
            }
        });
    }


}