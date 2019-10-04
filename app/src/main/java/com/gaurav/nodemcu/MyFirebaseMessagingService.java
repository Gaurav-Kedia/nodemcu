package com.gaurav.nodemcu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sendtoken(s);
    }

    private void sendtoken(String token) {
        // Write a message to the database
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("token");
        myRef.setValue(token);*/
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        send_message(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
    }

    public void send_message(String body, String title){

        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationHelper noti;
        int NOTI_SECONDARY1 = 1202;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            noti = new NotificationHelper(this);
            Notification.Builder nb = noti.getNotification2();
            nb.setSmallIcon(R.drawable.ic_launcher_foreground);
            nb.setContentTitle(title);
            nb.setContentText(body);
            nb.setContentIntent(pi);

            noti.notify(NOTI_SECONDARY1, nb);
        } else {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentText(body);

            builder.setContentIntent(pi);
            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
    }
}
