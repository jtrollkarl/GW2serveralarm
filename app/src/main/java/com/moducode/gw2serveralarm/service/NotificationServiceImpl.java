package com.moducode.gw2serveralarm.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.ui.activity.MainActivity;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-13.
 */

public class NotificationServiceImpl implements NotificationService {

    private static final String CHANNEL_MONITORING = "channel_monitoring";
    private static final int ID_MONITORING = 31;

    private final NotificationManager manager;
    private final Context appContext;
    private Notification notification;

    // TODO: 2017-11-13 clicking on notification should take the user back to the app
    // TODO: 2017-11-13 add name of server to notification

    @Inject
    public NotificationServiceImpl(Context appContext) {
        this.appContext = appContext;
        manager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void showMonitoringNotification() {
        if(isNotificationShowing()) return;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(appContext, CHANNEL_MONITORING)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(appContext.getString(R.string.notif_monitor_title))
                        .setContentText(appContext.getString(R.string.notif_monitor_summ))
                        .setChannelId(CHANNEL_MONITORING)
                        .setContentIntent(buildToAppIntent())
                        .setOnlyAlertOnce(true)
                        .setShowWhen(false)
                        .setOngoing(true);
        
        notification = builder.build();
        manager.notify(ID_MONITORING, notification);
    }

    @Override
    public void removeMonitoringNotification() {
        manager.cancel(ID_MONITORING);
        notification = null;
    }

    private PendingIntent buildToAppIntent(){
        Intent mainIntent = new Intent(appContext, MainActivity.class);
        return PendingIntent.getActivity(appContext, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private boolean isNotificationShowing(){
        return notification != null;
    }
}
