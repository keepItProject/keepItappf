package com.example.myapplication.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.docinfoActivity;

import java.util.ArrayList;
//-----------------------------------------------------------------------

//----------------------------------------------------------------------------



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> namelist;
    ArrayList<String> datelist;
    ArrayList<String> explist;



    public SearchAdapter(Context context, ArrayList<String> namelist, ArrayList<String> datelist, ArrayList<String> explist
    ) {
        this.context = context;
        this.namelist = namelist;
        this.datelist = datelist;
        this.explist = explist;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.searchlist,parent,false);

        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, final int position) {
        holder.namein.setText(namelist.get(position));
        holder.datein.setText(datelist.get(position));
        holder.exp.setText(explist.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, docinfoActivity.class);
                intent.putExtra("Name",namelist.get(position));
                intent.putExtra("date",datelist.get(position));
                intent.putExtra("Pdate",datelist.get(position));

                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return namelist.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView namein;
        TextView datein;
        TextView exp;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            namein=(TextView) itemView.findViewById(R.id.nameinf);
            datein=(TextView) itemView.findViewById(R.id.dateinf);
            exp=(TextView) itemView.findViewById(R.id.expired_date);



        }
    }
}
