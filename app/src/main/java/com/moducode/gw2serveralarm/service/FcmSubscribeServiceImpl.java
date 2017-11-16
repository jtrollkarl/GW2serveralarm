package com.moducode.gw2serveralarm.service;

import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-07.
 */

public class FcmSubscribeServiceImpl implements FcmSubscribeService {

    private final NotificationService notificationService;
    private final SharedPrefsManager sharedPrefsManager;

    @Inject
    public FcmSubscribeServiceImpl(SharedPrefsManager sharedPrefsManager, NotificationService notificationService) {
        this.sharedPrefsManager = sharedPrefsManager;
        this.notificationService = notificationService;
    }

    @Override
    public void subscribeToTopic(String topicId) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicId);
        sharedPrefsManager.saveServer(topicId);
        if(sharedPrefsManager.isNotificationEnabled()){
            notificationService.showMonitoringNotification();
        }
    }

    @Override
    public void unSubscribeFromTopic() {
        String topic = sharedPrefsManager.getSavedServer();
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        sharedPrefsManager.clearSavedTopic();
        notificationService.removeMonitoringNotification();
    }

    @Override
    public boolean isSubscribed() {
        return sharedPrefsManager.isMonitoringServer();
    }
}
