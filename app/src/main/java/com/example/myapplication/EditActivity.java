package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.UserCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    CardView card;
    private TextView textt,title;
    private ImageView imagee;
    private UserCategory userCategory;
    private LinearLayout layout_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        textt=findViewById(R.id.textt);
        textt=findViewById(R.id.textt);
        imagee=findViewById(R.id.imagee);
        layout_card=findViewById(R.id.layout_card);
        card=findViewById(R.id.card);
        Intent intent=getIntent();
        if(intent.hasExtra("cate")){
            float x=intent.getFloatExtra("x",0f);
            float y=intent.getFloatExtra("y",0f);
            userCategory= (UserCategory) intent.getSerializableExtra("cate");
            textt.setText(userCategory.getName());
            textt.setFocusable(true);



        }
    }

    public void edit(View view) {
        String name=textt.getText().toString().trim();
        if(!name.isEmpty()){
            HashMap<String,Object> map=new HashMap<>();
            map.put("name",name);
            FirebaseDatabase.getInstance().getReference().child("category").child(userCategory.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(EditActivity.this, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }else {
            Toast.makeText(this, "يرجى متبة اسم الفئة", Toast.LENGTH_SHORT).show();
        }
    }
}
