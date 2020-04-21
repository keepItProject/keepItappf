package com.example.myapplication.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.invoice;
import com.example.myapplication.R;
import com.example.myapplication.docinfoActivity;

import java.util.List;

public class invoiceAdapter extends RecyclerView.Adapter<invoiceAdapter.MyViewHolder> {

    Context context;
    List<invoice> data;

    public invoiceAdapter(Context context, List<invoice> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.invoicelist,parent,false);
        MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.purchase.setText(data.get(position).getPDate());
        holder.expired.setText(data.get(position).getEDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, docinfoActivity.class);
                intent.putExtra("invoice",data.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView purchase;
        TextView expired;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.invoice_name);
            purchase = (TextView) itemView.findViewById(R.id.purchase_date);
            expired = (TextView) itemView.findViewById(R.id.expired_date);
        }
    }
}
