package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorListActivity extends AppCompatActivity {
    DatabaseReference myRef;
    ArrayList<String> al;
    ArrayAdapter<String> ad;
    ListView DonorList;
    String BloodGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorlist);
        Intent intent=getIntent();
        BloodGroup=intent.getStringExtra("key");
        myRef=FirebaseDatabase.getInstance().getReference("Donors");
        DonorList=findViewById(R.id.listview2);
        al=new ArrayList<>();
        ad=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,al);
        DonorList.setAdapter(ad);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren())
                {
                            Donors usr = data.getValue(Donors.class);
                             assert usr != null;
                             if(usr.getBloodGroup().equals(BloodGroup)) {
                                 al.add("Donor Name :" + usr.getName() + "  \n"
                                         + "BloodGroup :" + usr.getBloodGroup() + " \n"
                                         + "City :" + usr.getCity() + "-" + usr.getPinCode() + " \n"
                                         + "Mobile Number :" + usr.getNumber());
                                 ad.notifyDataSetChanged();
                             }
                }

                if(al.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"No Donors Found With BloodGroup "+BloodGroup,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}