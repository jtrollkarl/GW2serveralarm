package com.moducode.gw2serveralarm.service;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.moducode.gw2serveralarm.R;

/**
 * Created by Jay on 2017-11-13.
 */

public class NotificationServiceImpl implements NotificationService {

    private static final String CHANNEL_MONITORING = "channel_monitoring";
    private static final int ID_MONITORING = 31;

    private final NotificationManager manager;
    private final Context appContext;

    public NotificationServiceImpl(Context appContext) {
        this.appContext = appContext;
        manager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void showMonitoringNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(appContext, CHANNEL_MONITORING)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setOnlyAlertOnce(true)
                        .setOngoing(true);


        manager.notify(ID_MONITORING, builder.build());
    }

    @Override
    public void removeMonitoringNotification() {
        manager.cancel(ID_MONITORING);
    }

}
