package com.example.myapplication.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class calendarAdapter extends RecyclerView.Adapter <calendarAdapter.calendarViewHolder>{


    ArrayList<String> nameslist;
    Context context;

    public calendarAdapter(Context context, ArrayList <String> nameslist){
        this.context = context;
        this.nameslist = nameslist;
    }


    @NonNull
    @Override
    public calendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.calendarlist,parent,false);

        return new calendarAdapter.calendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull calendarViewHolder holder, int position) {
        holder.invName.setText(nameslist.get(position));
    }


    @Override
    public int getItemCount() {
        return nameslist.size();
    }



    public static class calendarViewHolder extends RecyclerView.ViewHolder{

        TextView invName;

        public calendarViewHolder(@NonNull View itemView) {
            super(itemView);
            invName = (TextView) itemView.findViewById(R.id.invoicename);
        }
    }
}
