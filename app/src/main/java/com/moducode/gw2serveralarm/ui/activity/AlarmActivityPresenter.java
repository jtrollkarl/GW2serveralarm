package com.moducode.gw2serveralarm.ui.activity;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.PresenterLogger;
import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.service.FcmSubscribeServiceImpl;
import com.moducode.gw2serveralarm.ui.activity.AlarmActivityContract;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-21.
 */

public class AlarmActivityPresenter extends MvpBasePresenter<AlarmActivityContract.View>
        implements AlarmActivityContract.Actions {

    @Inject
    PresenterLogger logger;

    @Inject
    FcmSubscribeService fcmSubscribeService;

    @Inject
    AlarmServiceManager alarmServiceManager;


    @Inject
    public AlarmActivityPresenter(PresenterLogger logger, FcmSubscribeService fcmSubscribeService, AlarmServiceManager alarmServiceManager) {
        this.logger = logger;
        this.fcmSubscribeService = fcmSubscribeService;
        this.alarmServiceManager = alarmServiceManager;
    }

    @Override
    public void startAlarmService() {
        fcmSubscribeService.unSubscribeFromTopic();
        alarmServiceManager.startAlarmService();
    }

    @Override
    public void stopAlarmService() {
        alarmServiceManager.stopAlarmService();
    }

}