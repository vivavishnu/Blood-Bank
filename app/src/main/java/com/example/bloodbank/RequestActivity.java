package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class RequestActivity extends AppCompatActivity {
   ArrayList<String> List;
    ArrayAdapter<String> Adapter;
    Spinner blood;
    String BloodGroup;
    Button request;
    DatabaseReference myRef;
    String dName,dNumber,dCity,dPinCode,dMail;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        blood=findViewById(R.id.SpinnerBlood);
        request=findViewById(R.id.button7);
        myRef= FirebaseDatabase.getInstance().getReference();
        List =new ArrayList<>();
        List.add("A+");
        List.add("A-");
        List.add("B+");
        List.add("B-");
        List.add("AB+");
        List.add("AB-");
        List.add("O+");
        List.add("O-");
        Adapter =new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item, List);
        Adapter.setDropDownViewResource(R.layout.spinner_item);
        blood.setAdapter(Adapter);
        blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BloodGroup=blood.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=myRef.push().getKey();
                Requests usr=new Requests();
                usr.setName(dName);
                usr.setMail(dMail);
                usr.setBloodGroup(BloodGroup);
                usr.setCity(dCity);
                usr.setNumber(dNumber);
                usr.setPinCode(dPinCode);
                myRef=FirebaseDatabase.getInstance().getReference().child("Requests");
                myRef.child(id).setValue(usr);
                Toast.makeText(getApplicationContext(),"Request sent Successfully...",Toast.LENGTH_SHORT).show();

            }
        });
        myRef=FirebaseDatabase.getInstance().getReference().child("Users").child(requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users usr=snapshot.getValue(Users.class);
                assert usr != null;
                dName =usr.getName();
                dMail =usr.getMail();
                dCity =usr.getCity();
                dNumber =usr.getNumber();
                dPinCode =usr.getPinCode();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}