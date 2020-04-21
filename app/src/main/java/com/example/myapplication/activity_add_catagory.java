package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.UserCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_add_catagory extends AppCompatActivity {

    activity_category cate = new activity_category();
    Toolbar toolbar;
    EditText name;
    Button addbt;
    String tit;


    RecyclerView recyclerHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catagory);
        recyclerHome = (RecyclerView) findViewById(R.id.recycler_);

        name = (EditText) findViewById(R.id.editText);
        //For tool bar
        addbt = (Button) findViewById(R.id.button3);
        addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String cateName=name.getText().toString().trim();
                if(cateName.isEmpty()){
                    Toast.makeText(activity_add_catagory.this, "اكتب اسم الفئة", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance().getReference().child("category").orderByChild("name").equalTo(cateName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                            if(dataSnapshot2.exists()){
                                showExisitDialog();


                            }else {
                                String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                                DatabaseReference push = FirebaseDatabase.getInstance().getReference().child("category").push();
                                String key=push.getKey();
                                UserCategory userCategory=new UserCategory();
                                userCategory.setId(key);
                                userCategory.setUser_id(uid);
                                userCategory.setType("user");
                                userCategory.setName(cateName);

                                push.setValue(userCategory).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(activity_add_catagory.this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(activity_add_catagory.this, activity_category.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(activity_add_catagory.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }


            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //-----------------------------------------------------------------
    }

    private void showExisitDialog() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view= LayoutInflater.from(this).inflate(R.layout.category_exist_dialog,null,false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.show();

        CardView repeat_card=view.findViewById(R.id.repeat_card);
        repeat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });



    }

    public void button3(View v) {
        Intent intent = new Intent(activity_add_catagory.this, MainActivity.class);
        startActivity(intent);
    }

}
