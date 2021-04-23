package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText name,mail,number,pass,city,PinCode;
    Spinner blood;
    Button register;
    ArrayAdapter<String> ad;
    ArrayList<String> al;
    String BloodGroup;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.editTextPersonName);
        mail=findViewById(R.id.editTextTextEmailAddress);
        pass=findViewById(R.id.editTextTextPassword);
        number=findViewById(R.id.editTextNumber);
        city=findViewById(R.id.editTextCity);
        PinCode=findViewById(R.id.editTextPinCode);
        blood=findViewById(R.id.SpinnerBlood);
        register=findViewById(R.id.button);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Users");
        al=new ArrayList<>();
        al.add("A+");
        al.add("A-");
        al.add("B+");
        al.add("B-");
        al.add("AB+");
        al.add("AB-");
        al.add("O+");
        al.add("O-");
        ad=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item,al);
        ad.setDropDownViewResource(R.layout.spinner_item);
        blood.setAdapter(ad);
        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BloodGroup=blood.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DName=name.getText().toString();
                final String DMail=mail.getText().toString();
                String DPass=pass.getText().toString();
                final String DCity=city.getText().toString();
                final String DNumber=number.getText().toString();
                final String DPinCode=PinCode.getText().toString();
                final String DBloodGroup=BloodGroup;
                if(DName.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your name",Toast.LENGTH_SHORT).show();
                }
                else if(DMail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your mail",Toast.LENGTH_SHORT).show();
                }
                else if(DPass.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                }
                else if(DCity.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your city",Toast.LENGTH_SHORT).show();
                }
                else if(DNumber.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your number",Toast.LENGTH_SHORT).show();
                }
                else if(DPinCode.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your PinCode",Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(DMail,DPass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = mAuth.getCurrentUser().getUid();
                                Users usr = new Users();
                                usr.setBloodGroup(DBloodGroup);
                                usr.setName(DName);
                                usr.setCity(DCity);
                                usr.setMail(DMail);
                                usr.setNumber(DNumber);
                                usr.setPinCode(DPinCode);
                                myRef.child(uid).setValue(usr);
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Check your mail for verification link..", Toast.LENGTH_LONG).show();
                                            mail.setText("");
                                            pass.setText("");
                                            city.setText("");
                                            name.setText("");
                                            PinCode.setText("");
                                            number.setText("");
                                        } else {
                                            String msg = task.getException().getMessage();
                                            Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });

    }
}