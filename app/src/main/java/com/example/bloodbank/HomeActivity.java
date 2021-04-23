package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.util.Objects.requireNonNull;

public class HomeActivity extends AppCompatActivity {
    TextView logout,BecomeDonor,NeedDonor,profile;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String dName;
    String dMail;
    String dCity;
    String dNumber;
    String dPinCode;
    String dBloodGroup;
    String uid;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BecomeDonor=findViewById(R.id.textView3);
        NeedDonor=findViewById(R.id.textView4);
        logout=findViewById(R.id.textView6);
        profile=findViewById(R.id.textView5);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        BecomeDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert=new AlertDialog.Builder(HomeActivity.this);
                View view=getLayoutInflater().inflate(R.layout.confirm_donor,null);
                Button ok=view.findViewById(R.id.buttonOK);
                Button cancel=view.findViewById(R.id.buttonCancel);
                alert.setView(view);
                final AlertDialog dialog=alert.create();
                dialog.setCanceledOnTouchOutside(false);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        uid= requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                        Donors usr=new Donors();
                        usr.setName(dName);
                        usr.setMail(dMail);
                        usr.setBloodGroup(dBloodGroup);
                        usr.setCity(dCity);
                        usr.setNumber(dNumber);
                        usr.setPinCode(dPinCode);
                        myRef=FirebaseDatabase.getInstance().getReference().child("Donors");
                        myRef.child(uid).setValue(usr);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"You are a DONOR now",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();

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
                dBloodGroup =usr.getBloodGroup();
                dNumber =usr.getNumber();
                dPinCode =usr.getPinCode();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
               startActivity(intent);
            }
        });
        NeedDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,RequestActivity.class);
                startActivity(intent);
            }
        });

    }
}