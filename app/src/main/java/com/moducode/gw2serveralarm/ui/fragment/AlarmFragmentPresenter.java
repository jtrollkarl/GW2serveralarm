package com.moducode.gw2serveralarm.ui.fragment;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-21.
 */

public class AlarmFragmentPresenter extends MvpBasePresenter<AlarmFragmentContract.View>
        implements AlarmFragmentContract.Actions {

    @Inject
    FcmSubscribeService fcmSubscribeService;

    public AlarmFragmentPresenter(FcmSubscribeService fcmSubscribeService) {
        this.fcmSubscribeService = fcmSubscribeService;
    }

    @Override
    public void startAlarmService() {

    }

    @Override
    public void stopAlarmService() {

    }

}
