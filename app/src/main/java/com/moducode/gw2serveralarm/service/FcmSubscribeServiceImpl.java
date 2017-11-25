package com.moducode.gw2serveralarm.service;


import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-07.
 */

public class FcmSubscribeServiceImpl implements FcmSubscribeService {

    private final FcmMessagingDelegate fcmMessagingDelegate;
    private final NotificationService notificationService;
    private final SharedPrefsManager sharedPrefsManager;


    // TODO: 2017-11-17 how to show notification onResume from opening the app if it already exists, without redrawing it again?

    @Inject
    public FcmSubscribeServiceImpl(FcmMessagingDelegate fcmMessagingDelegate,
                                   SharedPrefsManager sharedPrefsManager,
                                   NotificationService notificationService) {
        this.fcmMessagingDelegate = fcmMessagingDelegate;
        this.sharedPrefsManager = sharedPrefsManager;
        this.notificationService = notificationService;
    }

    @Override
    public void subscribeToTopic(String topicId) {
        fcmMessagingDelegate.subscribeToTopic(topicId);
        sharedPrefsManager.saveServer(topicId);
        if(sharedPrefsManager.isNotificationEnabled()){
            notificationService.showMonitoringNotification();
        }
    }

    @Override
    public void unSubscribeFromTopic() {
        String topic = sharedPrefsManager.getSavedServer();
        fcmMessagingDelegate.unsubscribeFromTopic(topic);
        sharedPrefsManager.clearSavedTopic();
        notificationService.removeMonitoringNotification();
    }

    @Override
    public void removeNotification() {
        notificationService.removeMonitoringNotification();
    }

    @Override
    public void showNotification(){
        if(sharedPrefsManager.isNotificationEnabled() && sharedPrefsManager.isMonitoringServer()){
            notificationService.showMonitoringNotification();
        }
    }

    @Override
    public boolean isSubscribed() {
        return sharedPrefsManager.isMonitoringServer();
    }
}
