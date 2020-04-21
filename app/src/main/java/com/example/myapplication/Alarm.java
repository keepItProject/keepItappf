package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.myapplication.receivers.AlarmReceiver;

/**
 * Created by Mohamed Fadel
 * Date: 4/4/2020.
 * email: mohamedfadel91@gmail.com.
 */
public class Alarm {

    /**
     * Schedule a time for the alarm to ring at.
     *
     * @param context    An active context instance.
     * @param timeMillis A UNIX timestamp specifying the next time for the alarm to ring.
     */
    public static void setAlarm(Context context, int id, long timeMillis) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager.setAlarmClock(
                    new AlarmManager.AlarmClockInfo(
                            timeMillis,
                            PendingIntent.getActivity(context, 0, new Intent(context, addDoc2Activity.class), 0)
                    ),
                    getIntent(context, id)
            );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                manager.setExact(AlarmManager.RTC_WAKEUP, timeMillis, getIntent(context, id));
            else
                manager.set(AlarmManager.RTC_WAKEUP, timeMillis, getIntent(context, id));

            Intent intent = new Intent("android.intent.action.ALARM_CHANGED");
            intent.putExtra("alarmSet", true);
            context.sendBroadcast(intent);
        }

        manager.set(AlarmManager.RTC_WAKEUP,
                timeMillis,
                getIntent(context, id));

    }

    /**
     * The intent to fire when the alarm should ring.
     *
     * @param context An active context instance.
     * @return A PendingIntent that will open the alert screen.
     */
    private static PendingIntent getIntent(Context context, int id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.EXTRA_ALARM_ID, id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
