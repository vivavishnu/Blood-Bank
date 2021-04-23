package com.example.bloodbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Donors> DonorsList;
    Context context;

    public MyAdapter(ArrayList<com.example.bloodbank.Donors> donors, Context context) {
        this.DonorsList = donors;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donors donors=DonorsList.get(position);
        holder.Number.setText(donors.getNumber());
        holder.PinCode.setText(donors.getPinCode());
        holder.BloodGroup.setText(donors.getBloodGroup());
        holder.Name.setText(donors.getName());
        holder.City.setText(donors.getCity());
    }

    @Override
    public int getItemCount() {
        return DonorsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView BloodGroup,Name,Number,City,PinCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BloodGroup=itemView.findViewById(R.id.TextBlood);
            Name=itemView.findViewById(R.id.TextPersonName);
            City=itemView.findViewById(R.id.TextCity);
            PinCode=itemView.findViewById(R.id.TextPinCode);
            Number=itemView.findViewById(R.id.TextNumber);
        }
    }
}
