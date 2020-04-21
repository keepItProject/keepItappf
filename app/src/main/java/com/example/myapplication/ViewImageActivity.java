package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ImageView imageView3=findViewById(R.id.imageView3);
        Intent intent=getIntent();
        if(intent.hasExtra("url")){
            String url=intent.getStringExtra("url");

            Glide.with(this).load(url).centerCrop()
                    .into(imageView3);
        }
    }
}
