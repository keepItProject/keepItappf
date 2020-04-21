package com.example.myapplication;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adaptors.cateAdapter;
import com.example.myapplication.Data.UserCategory;
import com.example.myapplication.Data.invoice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class activity_category extends AppCompatActivity{

    Toolbar toolbar;
    RecyclerView recyclerHome;
    ArrayList<String> titles;
    ArrayList<Integer> Images;
    cateAdapter adapter;

    private TextView title;

    Button n, bt, addbt;
    EditText name;
    String tit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //For tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //title=toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);

       // title.setText(toolbar.getTitle());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        titles = new ArrayList<>();
        Images = new ArrayList<>();

        //filling up the app with data
        adding_data();

//        adapter.setOnItemClickListener(new cateAdapter.OnitemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(activity_category.this, activity_homepage.class);
//                startActivity(intent);
//            }
//        });
        //shaden


/*
        bt = (ImageButton) findViewById(R.id.imageButton1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_homedev.class);
                startActivity(intent);
            }
        });
        bt = (ImageButton) findViewById(R.id.imageButton6);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_electr.class);
                startActivity(intent);
            }
        });
        bt = (ImageButton) findViewById(R.id.imageButton7);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_game.class);
                startActivity(intent);
            }
        });
        bt = (ImageButton) findViewById(R.id.imageButton8);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_furn.class);
                startActivity(intent);
            }
        });

        bt = (ImageButton) findViewById(R.id.imageButton5);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_cloth.class);
                startActivity(intent);
            }
        });
        bt = (ImageButton) findViewById(R.id.imageButton9);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_car.class);
                startActivity(intent);
            }
        });
        bt = (ImageButton) findViewById(R.id.imageButton10);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_category.this, activity_add_catagory.class);
                startActivity(intent);
            }
        });*/

/*
        DatabaseReference document = FirebaseDatabase.getInstance().getReference().child("document").push();
        String key=document.getKey();
        invoice invoice=new invoice();
        invoice.setEDate("2020/2/12");
        invoice.setPDate("2020/2/11");
        invoice.setName("حقيبة");
        invoice.setCategoryId("5");
        invoice.setServiceProvider("Louis Vuitton");
        invoice.setServiceProviderPhone("0576723788");
        invoice.setServiceProviderWebsite("www.ddd.com");
        invoice.setId(key);

        document.setValue(invoice);


        DatabaseReference document2 = FirebaseDatabase.getInstance().getReference().child("document").push();
        String key2=document2.getKey();
        invoice invoice1=new invoice();
        invoice1.setEDate("2020/2/10");
        invoice1.setPDate("2020/2/8");
        invoice1.setName("سيارة");
        invoice1.setCategoryId("4");
        invoice1.setServiceProvider("Toyota");
        invoice1.setServiceProviderPhone("3443543");
        invoice1.setServiceProviderWebsite("www.tttt.com");
        invoice1.setId(key2);

        document2.setValue(invoice1);*/


        recyclerHome = findViewById(R.id.recycler_);
        recyclerHome.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new cateAdapter( this);
        adapter.setUserCategories(userCategories);
        adapter.setMode("normal");
        recyclerHome.setAdapter(adapter);


    }



    ArrayList<UserCategory> userCategories=new ArrayList<>();

    private void DisplayCards() {




        final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    if(userCategories ==null)
                        userCategories=new ArrayList<>();
                        userCategories.clear();
                    Log.d("MUTEE",dataSnapshot.getChildrenCount()+ "items");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        UserCategory userCategory=snapshot.getValue(UserCategory.class);

                        if((userCategory.getType()!=null && userCategory.getType().equals("static")) || userCategory.getUser_id().equals(uid))
                        userCategories.add(userCategory);
                    }


                }
                UserCategory add_cate=new UserCategory();
                add_cate.setId("add");
                add_cate.setUser_id("add");
                add_cate.setName("اضافة");
                add_cate.setType("add");
                userCategories.add(add_cate);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    userCategories.sort(new IdSorter());
                }*/

                adapter.setUserCategories(userCategories);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*UserCategory add_cate=new UserCategory();
        add_cate.setId("add");
        add_cate.setUser_id("313");
        add_cate.setName("اضافة");

        UserCategory ca1=new UserCategory();
        ca1.setId("add");
        ca1.setUser_id("313");
        ca1.setName("اضافة");


        UserCategory ca2=new UserCategory();
        ca2.setId("add");
        ca2.setUser_id("313");
        ca2.setName("اضافة");

        UserCategory ca3=new UserCategory();
        ca3.setId("add");
        ca3.setUser_id("313");
        ca3.setName("اضافة");

        UserCategory ca4=new UserCategory();
        ca4.setId("add");
        ca4.setUser_id("313");
        ca4.setName("اضافة");

        UserCategory ca5=new UserCategory();
        ca5.setId("add");
        ca5.setUser_id("313");
        ca5.setName("اضافة");

        UserCategory ca6=new UserCategory();
        ca6.setId("add");
        ca6.setUser_id("313");
        ca6.setName("اضافة");

        UserCategory ca7=new UserCategory();
        ca7.setId("add");
        ca7.setUser_id("313");
        ca7.setName("اضافة");

        UserCategory ca8=new UserCategory();
        ca8.setId("add");
        ca8.setUser_id("313");
        ca8.setName("اضافة");

        UserCategory ca9=new UserCategory();
        ca9.setId("add");
        ca9.setUser_id("313");
        ca9.setName("اضافة");

        UserCategory ca10=new UserCategory();
        ca10.setId("add");
        ca10.setUser_id("313");
        ca10.setName("اضافة");

        UserCategory ca11=new UserCategory();
        ca11.setId("add");
        ca11.setUser_id("313");
        ca11.setName("اضافة");
        userCategories.add(ca1);
        userCategories.add(ca2);
        userCategories.add(ca3);
        userCategories.add(ca4);
        userCategories.add(ca5);
        userCategories.add(ca6);
        userCategories.add(ca7);
        userCategories.add(ca8);
        userCategories.add(ca9);
        userCategories.add(ca7);
        userCategories.add(ca9);
        userCategories.add(ca11);
        userCategories.add(ca10);
        userCategories.add(ca11);
        userCategories.add(ca8);
        userCategories.add(add_cate);*/


    }

    private void adding_data() {
        titles.add("أجهزة منزلية");
        titles.add("الكترونيات");
        titles.add("العاب");
        titles.add("أثاث");

        titles.add("أزياء");
        titles.add("سيارات");
        titles.add("إضافة");

        Images.add(R.drawable.blender);
        Images.add(R.drawable.tvvv);
        Images.add(R.drawable.game);
        Images.add(R.drawable.furn);
        Images.add(R.drawable.fash);
        Images.add(R.drawable.carr);
        Images.add(R.drawable.add);

        //display data

    }

    @Override
    protected void onStart() {
        super.onStart();
        DisplayCards();
    }
    //public void inform(View view) {
    // Intent intent = new Intent(activity_category.this, information.class);
    //  startActivity(intent);
    //}
    // public void calendar(View v){
    //  Intent intent = new Intent(activity_category.this, calenderr.class);
    //startActivity(intent);
    //}
    //public void hooommee(View v){
    //  Intent intent = new Intent(activity_category.this, homepage.class);
    //startActivity(intent);

    // For menu

    public boolean onCreateOptionsMenu(Menu menue) {
        //return super.onCreateOptionsMenu(menue);
        getMenuInflater().inflate(R.menu.editcat, menue);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.meueline){
            setAdapterMode("edit");
            return true;
        }

        if(id==android.R.id.home){
            if(adapter.getMode().equals("edit") || adapter.getMode().equals("delete")){
                setAdapterMode("normal");
            }else {
                setAdapterMode("delete");
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {

       if(adapter.getMode().equals("edit") || adapter.getMode().equals("delete")){
           setAdapterMode("normal");
       }else {
           super.onBackPressed();
       }

    }

    private void setAdapterMode(String mode){
        if(mode.equals("edit") || mode.equals("delete")){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.delete);
        }


        adapter.setMode(mode);
        adapter.setUserCategories(adapter.getUserCategories());
        //DisplayCards();
    }


    //Card view

    /*private void initialize(){



    }*/

    public class IdSorter implements Comparator<UserCategory>
    {
        @Override
        public int compare(UserCategory o1, UserCategory o2) {
            return o2.getId().compareTo(o1.getId());
        }
    }

}
