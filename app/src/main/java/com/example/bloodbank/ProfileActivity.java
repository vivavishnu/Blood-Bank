package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ProfileActivity extends AppCompatActivity {
    TextView name,mail,city,blood,pincode,number;
    DatabaseReference myRef;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.textView14);
        mail=findViewById(R.id.textView15);
        number=findViewById(R.id.textView16);
        city=findViewById(R.id.textView17);
        pincode=findViewById(R.id.textView18);
        blood=findViewById(R.id.textView19);
        myRef=FirebaseDatabase.getInstance().getReference().child("Users").child(requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users usr = snapshot.getValue(Users.class);
                assert usr != null;
                name.setText(usr.getName());
                mail.setText(usr.getMail());
                number.setText(usr.getNumber());
                blood.setText(usr.getBloodGroup());
                city.setText(usr.getCity());
                pincode.setText(usr.getPinCode());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}