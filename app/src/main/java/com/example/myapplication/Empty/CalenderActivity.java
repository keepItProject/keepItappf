package com.example.myapplication.Empty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.Adaptors.calendarAdapter;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class CalenderActivity extends AppCompatActivity {
    // calendarAdapter calendarAdapter;
    DatabaseReference ref2, ref3;
    public static  String expired_data;
    public static  String name;

    ArrayList<String> nameslist;
    RecyclerView recyclerView;
    EditText search;

    private MCalendarView expCalendarView;
    RecyclerView.LayoutManager layoutManager;
    calendarAdapter adapter;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        nameslist=new ArrayList<>();
        search=(EditText) findViewById(R.id.searchView);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_calendar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        expCalendarView = ((MCalendarView) findViewById(R.id.calendar));

/////////////////
        expCalendarView.markDate(2020,4,10);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        // ref3 = FirebaseDatabase.getInstance().getReference().child("document").child("l1");

        //databaseReference= FirebaseDatabase.getInstance().getReference();
        //  firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        // String h=expired_data.substring(0,2);
        // nameslist.add(h);
        //  adapter = new calendarAdapter(CalenderActivity.this, nameslist);
        //  recyclerView.setAdapter(adapter);
        // nameslist.add("kkgkk");
        //nameslist.clear();
        //datelist.clear();
        //nameslist.clear();
        // recyclerView.removeAllViews();

        // expCalendarView.markDate(2020,2,19);
        //expCalendarView.travelTo(new DateData(2020, 2, 14));

       /* int i = Integer.parseInt(h);
        String h1=expired_data.substring(3,5);
        int i1 = Integer.parseInt(h1);
        String h2=expired_data.substring(6,10);
        int i2 = Integer.parseInt(h2);
        expCalendarView.markDate(i2,i1,i);*/
        databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datashot:dataSnapshot.getChildren() ){
                    expired_data=datashot.child("Expired_data").getValue(String.class);
                    name= datashot.child("Name").getValue(String.class);
                    String h=expired_data.substring(1,2);
                    String h1=expired_data.substring(4,5);
                    String h2=expired_data.substring(6,10);

                    int u=Integer.parseInt(h);
                    int u1=Integer.parseInt(h1);
                    int u2=Integer.parseInt(h2);
                    expCalendarView.markDate(u2,u1,u);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, final DateData date) {
                nameslist.clear();
                recyclerView.removeAllViews();
                databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot datashot : dataSnapshot.getChildren()) {
                            expired_data = datashot.child("Expired_data").getValue(String.class);
                            name = datashot.child("Name").getValue(String.class);


                            String g = date.getDayString();
                            String l = date.getMonthString();
                            String m = date.getYearString();
                            String n = g + "/" + l + "/" + m;

                            if (expired_data.equals(n)) {
                                nameslist.add(name);
                                // expCalendarView.markDate(2020,3,20);

                            } else {

                                nameslist.clear();
                                recyclerView.removeAllViews();
                            }
                            adapter = new calendarAdapter(CalenderActivity.this, nameslist);
                            recyclerView.setAdapter(adapter);
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }


}

