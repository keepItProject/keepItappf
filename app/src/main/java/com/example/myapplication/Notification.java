package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class Notification extends AppCompatActivity {
    private static final String chid="simplified_coding";
    private static final String chname="Simplified Coding";
    private static final String chdesc="Simplified Coding Notifications";
    private
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Button button9no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        firebaseAuth=FirebaseAuth.getInstance();
        Intent notificationIntent = new Intent(this, Alarmrecevier.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String month="03";
        Calendar cal = Calendar.getInstance();

        cal.set(2020,2,24,18,10);

        //  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
       /*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(chid,chname, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chdesc);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        button9no = findViewById(R.id.button9no);
        button9no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaynotifaction();
            }
        });
      //AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

       //Calendar calendar=Calendar.getInstance();
     //  calendar.add(Calendar.SECOND,5);
      //
       // alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),);*/
    }

    private void displaynotifaction(){
        NotificationCompat.Builder mbulider= new NotificationCompat.Builder(this,chid).setSmallIcon(R.drawable.camera).setContentTitle("KEEP IT App")
                .setContentText("").setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mbulider.build());



    }
}
