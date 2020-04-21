package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Data.UserCategory;
import com.example.myapplication.Data.invoice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class docinfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Context mContext;
    private com.example.myapplication.Data.invoice invoice;

    Button button6;
    private TextView doc_name;
    private TextView doc_number;
    private TextView doc_pdate;
    private TextView doc_cat;
    private TextView doc_service_provider;
    private TextView doc_service_provider_phone;
    private TextView doc_service_provider_website;
    private ImageView doc_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_info);
        doc_name=findViewById(R.id.doc_name);
        doc_image=findViewById(R.id.doc_image);
        doc_number=findViewById(R.id.doc_number);
        doc_pdate=findViewById(R.id.doc_pdate);
        doc_cat=findViewById(R.id.doc_cat);
        doc_service_provider=findViewById(R.id.doc_service_provider);
        doc_service_provider_phone=findViewById(R.id.doc_service_provider_phone);
        doc_service_provider_website=findViewById(R.id.doc_service_provider_website);

        final Intent intent=getIntent();
        if(intent.hasExtra("invoice")){
            invoice=(com.example.myapplication.Data.invoice)intent.getSerializableExtra("invoice");
            doc_name.setText( invoice.getName()==null?" الاسم ":" الاسم "+invoice.getName());

            doc_number.setText(invoice.getNumber()==null?" رقم الفاتورة ":" رقم الفاتورة "+ invoice.getNumber());

            doc_pdate.setText(invoice.getPDate()!=null?" تاريخ الشراء "+invoice.getPDate():" تاريخ الشراء ");


            doc_service_provider.setText(invoice.getServiceProvider()==null?" مقدم الخدمة ":

                    " مقدم الخدمة "+invoice.getServiceProvider());

            doc_service_provider_phone.setText(invoice.getServiceProviderPhone()==null?" رقم مزود الخدمة "
                    :" رقم مزود الخدمة "+invoice.getServiceProviderPhone());

            doc_service_provider_website.setText(
                    invoice.getServiceProviderWebsite()==null?" موقع مزود الخدمة " :

                            " موقع مزود الخدمة " +invoice.getServiceProviderWebsite());

            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.coverfile);
            Glide.with(this).load(invoice.getImage()).centerCrop().into(doc_image);
            try{
                FirebaseDatabase.getInstance().getReference().child("category").child(invoice.getCategoryId())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    UserCategory userCategory=dataSnapshot.getValue(com.example.myapplication.Data.UserCategory.class);
                                    doc_cat.setText(" التصنيف "+ userCategory.getName());




                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

            }catch (Exception e){
                doc_cat.setText(" التصنيف ");
            }

            if(invoice.getImage()!=null){
                doc_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1=new Intent(docinfoActivity.this,ViewImageActivity.class);
                        intent1.putExtra("url",invoice.getImage());
                        startActivity(intent1);
                    }
                });
            }

        }

        if(intent.hasExtra("name")){
            String name=intent.getStringExtra("name");
            String date=intent.getStringExtra("date");

            doc_name.setText(" الاسم "+ name);

            doc_pdate.setText(" تاريخ الشراء "+date);
        }


        //tool bar ******
        toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);


        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(docinfoActivity.this);
                builder.setCancelable(true);
                View view = LayoutInflater.from(docinfoActivity.this).inflate(R.layout.delete_cate_dialog, null, false);
                CardView yes_card=view.findViewById(R.id.yes_card);
                builder.setView(view);
                final AlertDialog alertDialog = builder.show();
                CardView no_card=view.findViewById(R.id.no_card);
                no_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                    }
                });


                yes_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference().child("document").child("l1").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    alertDialog.dismiss();
                                }
                            }
                        });

                    }
                });


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
//*************}
