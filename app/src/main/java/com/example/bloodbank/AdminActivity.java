package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    Button Requests,SignOut,DonorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Requests=findViewById(R.id.button4);
        DonorList=findViewById(R.id.button5);
        SignOut=findViewById(R.id.button6);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(AdminActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        DonorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, SearchListActivity.class);
                startActivity(intent);
            }
        });
        Requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(AdminActivity.this,AdminRequestsActivity.class);
                startActivity(it);
            }
        });

    }
}