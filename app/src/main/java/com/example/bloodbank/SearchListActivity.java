package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {
Spinner blood;
String BloodGroup;
ArrayList<String> al;
ArrayAdapter<String> ad;
Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        blood=findViewById(R.id.SpinnerBlood);
        search=findViewById(R.id.button);
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchListActivity.this,DonorsActivity.class);
                intent.putExtra("key",BloodGroup);
                startActivity(intent);
            }
        });
    }
}