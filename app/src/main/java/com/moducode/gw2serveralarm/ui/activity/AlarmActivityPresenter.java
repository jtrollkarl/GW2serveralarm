package com.moducode.gw2serveralarm.ui.activity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-21.
 */

public class AlarmActivityPresenter extends MvpBasePresenter<AlarmActivityContract.View>
        implements AlarmActivityContract.Actions {


    @Inject
    FcmSubscribeService fcmSubscribeService;

    @Inject
    AlarmServiceManager alarmServiceManager;


    @Inject
    public AlarmActivityPresenter(FcmSubscribeService fcmSubscribeService, AlarmServiceManager alarmServiceManager) {
        this.fcmSubscribeService = fcmSubscribeService;
        this.alarmServiceManager = alarmServiceManager;
    }

    @Override
    public void startAlarmService() {
        fcmSubscribeService.unSubscribeFromTopic();
        ifViewAttached(view -> view.showServerName(fcmSubscribeService.getSavedServer()));
        alarmServiceManager.startAlarmService();
    }

    @Override
    public void stopAlarmService() {
        alarmServiceManager.stopAlarmService();
    }

}
