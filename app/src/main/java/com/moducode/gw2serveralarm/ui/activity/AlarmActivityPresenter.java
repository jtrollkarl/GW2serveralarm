package com.moducode.gw2serveralarm.ui.activity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.ui.activity.AlarmActivityContract;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-21.
 */

public class AlarmActivityPresenter extends MvpBasePresenter<AlarmActivityContract.View>
        implements AlarmActivityContract.Actions {

    @Inject
    AlarmServiceManager alarmServiceManager;

    @Inject
    public AlarmActivityPresenter(AlarmServiceManager alarmServiceManager) {
        this.alarmServiceManager = alarmServiceManager;
    }

    @Override
    public void startAlarmService() {

    }

    @Override
    public void stopAlarmService() {

    }

}
