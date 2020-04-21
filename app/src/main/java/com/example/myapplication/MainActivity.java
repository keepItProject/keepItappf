package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
//    public EditText emailId, password;
//    FirebaseAuth mFirebaseAuth;
//    Button btnsignUp;
//    TextView tvSignIn;

    Timer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = new Timer();
        time.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              Intent intent = new Intent(MainActivity.this, activity_login.class);
                              startActivity(intent);
                              finish();
                          }
                      }
                , 5000);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference User = database.getReference("User");
//        User.child("Name").setValue("Bsha");
//        // com.example.myapplication.Data.User.child("Name").setValue("Bshayer");
//
//        DatabaseReference Document = database.getReference("Document");
//        Document.child("Invoice Number");
//
//        DatabaseReference ServiceProvider = database.getReference("Service Provider");
//        ServiceProvider.child("Phone Number");
//        ServiceProvider.child("Name");
//
//        DatabaseReference Category = database.getReference("Category");
//        //Category.child("Name");
//        // this is my test

    }
}