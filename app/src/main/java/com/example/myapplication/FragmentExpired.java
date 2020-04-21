package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptors.invoiceAdapter;
import com.example.myapplication.Data.invoice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentExpired extends Fragment {

    View v;
    private RecyclerView recyclerView;
    public static List<invoice> expiredInvoices;
    //Database
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    //information
    public static String name;
    public static String purchase_data;
    public static String expired_data;



    public FragmentExpired() {
    }
    invoiceAdapter invoiceAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.expired_fragment,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.expired_recyclerview);
        invoiceAdapter = new invoiceAdapter(getContext(),expiredInvoices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(invoiceAdapter);
        return v;
    }

    public  static FragmentExpired newInstance(String id){
        FragmentExpired fragmentExpired=new FragmentExpired();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fragmentExpired.setArguments(bundle);
        return fragmentExpired;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expiredInvoices = new ArrayList<>();
       // expiredInvoices.add(new invoice("bshayer","9/7/1998","9/7/1999"));
        String category_id=getArguments().getString("id");
        Log.d("MUTEE", "onCreate: "+category_id);

        //databaseReference=

                Query query=FirebaseDatabase.getInstance().
                getReference().child("document").orderByChild("categoryId").equalTo(category_id);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

       // expiredInvoices.clear();
       // recyclerView.removeAllViews();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MUTEE", "onDataChange: "+dataSnapshot.getChildrenCount());
                if(dataSnapshot.getChildrenCount()>0){
                    expiredInvoices.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        invoice invoice=child.getValue(com.example.myapplication.Data.invoice.class);
                        expiredInvoices.add(invoice);

                    }
                    invoiceAdapter.notifyDataSetChanged();


                }else {
                    Toast.makeText(getContext(), "No Docments", Toast.LENGTH_SHORT).show();
                }
                /*for (DataSnapshot datashot:dataSnapshot.getChildren()){
                    name= datashot.child("Name").getValue(String.class);
                    purchase_data=datashot.child("Expired_data").getValue(String.class);  //wrong data********
                    expired_data=datashot.child("Expired_data").getValue(String.class);

                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                    if(expired_data.compareTo(currentDate)<0) { //less than currentDate

                    }

                    else if(expired_data.compareTo(currentDate)>0) { //greater than currentDate
                        expiredInvoices.add(new invoice (name,purchase_data,expired_data));
                    }

                    else if(expired_data.compareTo(currentDate)==0) { //both dates are equal

                    }

                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    } //onCreate


}
