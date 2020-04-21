package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Data.UserCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class editInfo extends AppCompatActivity {
    Toolbar toolbar;
    UserCategory userCategory;
    EditText h,y9;
    public static  String expired_data;
    public static  String name;
    Button o;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Spinner spinner;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
       // scrollView=  (ScrollView) findViewById(R.id.s1);
        toolbar = (Toolbar) findViewById(R.id.toolbar8);
        scrollView =findViewById(R.id.scrol);
        scrollView.setEnabled(false);
      //  scrollView.setPressed(true);
        //scrollView.setClickable(true);
       // Toolbar title = toolbar.findViewById(R.id.title);
        /*
        setSupportActionBar(toolbar);
       h=findViewById(R.id.textView0042);

        y9=findViewById(R.id.textView42);
        spinner=findViewById(R.id.spinner);
        List<String> list=new ArrayList<>();
        list.add("لعاب");
        list.add("الكترونيات");
        list.add("اجهزة منزلية");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue=parent.getItemAtPosition(position).toString();
                Toast.makeText(editInfo.this, "Selected:"+itemvalue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // y9=findViewById(R.id.textView18);
        o=findViewById(R.id.button);
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= FirebaseDatabase.getInstance().getReference();
               firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
               // String uid=databaseReference.getKey();
                databaseReference.child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datashot:dataSnapshot.getChildren() ){
                            expired_data=datashot.child("Expired_data").getValue(String.class);
                            name= datashot.child("Name").getValue(String.class);

                           String key=datashot.getKey();
                            String h1=h.getText().toString().trim();
                            if (expired_data.equals(h1)){


                            if(!h1.isEmpty()){
                                HashMap<String,Object> map=new HashMap<>();
                                map.put("Name",h1);
                                FirebaseDatabase.getInstance().getReference().child("document").child(key).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(editInfo.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });}
                            }else {
                                Toast.makeText(editInfo.this, "يرجى متبة اسم الفئة", Toast.LENGTH_SHORT).show();
                            }
                            String h2=y9.getText().toString().trim();
                          //  edit();

                            // databaseReference.child("User").child(uid).child("namme").setValue(uid);




                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menue) {
        //return super.onCreateOptionsMenu(menue);
        getMenuInflater().inflate(R.menu.editcat, menue);

        return true;
    }
    public void edit() {*/

    }

}

