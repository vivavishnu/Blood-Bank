package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Donors> DonorsList;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);
        recyclerView=findViewById(R.id.Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DonorsList=new ArrayList<>();
        myRef= FirebaseDatabase.getInstance().getReference("Donors");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    Donors donors=data.getValue(Donors.class);
                    DonorsList.add(donors);
                }
                MyAdapter adapter=new MyAdapter(DonorsList,DonorsActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}