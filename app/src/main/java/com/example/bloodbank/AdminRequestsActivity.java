package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminRequestsActivity extends AppCompatActivity {
    DatabaseReference myRef;
    ArrayList<String> al;
    ArrayAdapter<String> ad;
    ListView RequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests);
        RequestList=findViewById(R.id.ListView2);
        myRef= FirebaseDatabase.getInstance().getReference("Requests");
        al=new ArrayList<String>();
        ad=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,al);
        RequestList.setAdapter(ad);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    Requests usr=data.getValue(Requests.class);
                    assert usr != null;
                    al.add("Requested BloodGroup :" + usr.getBloodGroup() + "  \n"
                            +"Requested By :"+" \n"
                            + "Name :" + usr.getName() + " \n"
                            + "City :" + usr.getCity() + "-" + usr.getPinCode() + " \n"
                            + "Mobile Number :" + usr.getNumber());
                    ad.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}