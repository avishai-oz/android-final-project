package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class TryService extends Service {

    NotificationCompat.Builder builder;
    NotificationManager manager;
    final Handler handler = new Handler();
    String currentTime ;
    String currentDay;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Query q;

    public TryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onStop(){
    }


    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        q = myRef.child("leaders names:").orderByKey();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dst : dataSnapshot.getChildren()) {
                    String sportNameKey = dst.getKey();
                    LeaderNameAndRecord leaderNameAndRecord = dst.getValue(LeaderNameAndRecord.class);
                    if(MainActivity.using_this_phone!=null && MainActivity.using_this_phone.getName().equals(leaderNameAndRecord.getPreviousPersonName())&&
                       !MainActivity.using_this_phone.getName().equals(leaderNameAndRecord.getLeadingPersonName())){ // checks if the user isn't first now, but was previously
                        leaderNameAndRecord.setPreviousPersonName("Person already got notification");
                        myRef.child("leaders names:").child(dst.getKey()).setValue(leaderNameAndRecord);
                        notifyRightNow();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Service.START_STICKY;
    }

    public void notifyRightNow(){
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Best Sport Record")
                .setContentText(currentTime)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        int NOTIFICATION_ID = 12345;
        Intent notification_intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, 0, notification_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setContentIntent(contentIntent);
        builder.setContentText("עקפו אותך באחת הקטגוריות");
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }
}