package com.gaurav.nodemcu;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import com.gaurav.nodemcu.R;

/**
 * Created by GAURAV on 03-02-2019.
 */

public class NotificationHelper extends ContextWrapper {

    public static final String SECONDARY_CHANNEL = "Update";
    private NotificationManager manager;

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param ctx The application context
     */

    @TargetApi(Build.VERSION_CODES.O)
    public NotificationHelper(Context ctx) {
        super(ctx);

        final NotificationChannel chan2 = new NotificationChannel(SECONDARY_CHANNEL,
                "Updates", NotificationManager.IMPORTANCE_HIGH);
        chan2.setLightColor(Color.BLUE);
        chan2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(chan2);
    }

    /**
     * Build notification for secondary channel.
     *
     * @return A Notification.Builder configured with the selected channel and details
     */
    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotification2() {
        return new Notification.Builder(getApplicationContext(), SECONDARY_CHANNEL)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);
    }

    /**
     * Send a notification.
     *
     * @param id           The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

    /**
     * Get the small icon for this app
     *
     * @return The small icon resource id
     */
    private int getSmallIcon() {
        return R.drawable.rslogo;
    }

    /**
     * Get the notification manager.
     * <p>
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}