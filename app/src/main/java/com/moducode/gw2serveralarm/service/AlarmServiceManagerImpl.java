package com.moducode.gw2serveralarm.service;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Jay on 2017-11-21.
 */

public class AlarmServiceManagerImpl implements AlarmServiceManager {

    private final Context appContext;
    private final Intent alarmIntent;

    public AlarmServiceManagerImpl(Context appContext) {
        this.appContext = appContext;
        alarmIntent = new Intent(appContext, AlarmService.class);
    }

    @Override
    public void startAlarmService() {
        appContext.startService(alarmIntent);
    }

    @Override
    public void stopAlarmService() {
        appContext.stopService(alarmIntent);
    }

}
