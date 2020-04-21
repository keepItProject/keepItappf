package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Empty.CalenderActivity;
import com.example.myapplication.Empty.SearchActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_homepage extends AppCompatActivity{
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    Toolbar toolbar;
    ImageButton bt;
    ImageView cal ,ser ,ho,pro;
    Button b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //For tool bar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //--------------------------------------------------
        floatingActionButton=findViewById(R.id.orderPlus);
        bottomAppBar = findViewById(R.id.bar);
        bt= (ImageButton) findViewById(R.id.category);
        //botoom bar--------------------------------------------
        cal=(ImageView) findViewById(R.id.cale);
        ser=(ImageView) findViewById(R.id.sae);
        ho=(ImageView) findViewById(R.id.homi);
        pro=(ImageView) findViewById(R.id.userr);
//----------------------------------------------------------------
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_homepage.this, activity_category.class);
                startActivity(intent);
            }
        });

//----------------------------------------------------
        //ho.bringToFront();
        ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_homepage.this,activity_homepage.class);
                startActivity(intent);
            }
        });
        //
        //ser.bringToFront();
        ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_homepage.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //
       // pro.bringToFront();
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_homepage.this, profilActivity.class);
                startActivity(intent);
            }
        });
        //
        //cal.bringToFront();
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity_homepage.this, CalenderActivity.class);
                startActivity(intent);
            }
        });
        //

    }

    // For menu

    public boolean onCreateOptionsMenu (Menu menu){
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.roptions,menu);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rmenu));

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cond){
            Intent intent =new Intent(activity_homepage.this, ConditionsActivity.class);
            startActivity(intent); }

        if (id == R.id.MCI){

            WebView webview= new WebView(this);
            setContentView(webview);
            webview.loadUrl("https://mci.gov.sa/ar/pages/default.aspx");
        }



        if (id == R.id.logout){
            Toast.makeText(this,"logout", Toast.LENGTH_SHORT).show();
            finish();    return true;}

        return super.onOptionsItemSelected(item);///////////////////////////
    }

    public void tomyphoto(View view) {
        Intent i =new Intent(activity_homepage.this, TakePhoto.class);
        startActivity(i);
    }
}
