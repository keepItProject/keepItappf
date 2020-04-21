package com.example.myapplication.Empty;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.example.myapplication.Adaptors.ViewPagerAdapter;
import com.example.myapplication.FragmentActive;
import com.example.myapplication.FragmentExpired;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class activity_cloth extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);

        //For tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        if(intent.hasExtra("title")){
            String title=intent.getStringExtra("title");
            id=intent.getStringExtra("id");
            toolbar.setTitle(title);
        }

        //START HERE

        tabLayout = (TabLayout)findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager)findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // ADD FRAGMENT HERE

        adapter.AddFragment(new FragmentActive(),"فعّالة");
        adapter.AddFragment(FragmentExpired.newInstance(id),"منتهية");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
