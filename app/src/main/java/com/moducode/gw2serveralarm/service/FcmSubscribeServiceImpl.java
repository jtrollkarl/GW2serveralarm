package com.moducode.gw2serveralarm.service;


import com.moducode.gw2serveralarm.data.ServerModel;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-07.
 */

public class FcmSubscribeServiceImpl implements FcmSubscribeService {

    private final FcmMessagingDelegate fcmMessagingDelegate;
    private final NotificationService notificationService;
    private final SharedPrefsManager sharedPrefsManager;

    @Inject
    public FcmSubscribeServiceImpl(FcmMessagingDelegate fcmMessagingDelegate,
                                   SharedPrefsManager sharedPrefsManager,
                                   NotificationService notificationService) {
        this.fcmMessagingDelegate = fcmMessagingDelegate;
        this.sharedPrefsManager = sharedPrefsManager;
        this.notificationService = notificationService;
    }

    @Override
    public void subscribeToServer(ServerModel server) {
        fcmMessagingDelegate.subscribeToTopic(server.getIdString());
        sharedPrefsManager.saveServerId(server.getIdString());
        sharedPrefsManager.saveServerName(server.getName());
        if(sharedPrefsManager.isNotificationEnabled()){
            notificationService.showMonitoringNotification(sharedPrefsManager.getSavedServerName());
        }
    }

    @Override
    public void unSubscribeFromTopic() {
        String topic = sharedPrefsManager.getSavedServerId();
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
            notificationService.showMonitoringNotification(sharedPrefsManager.getSavedServerName());
        }
    }

    @Override
    public boolean isSubscribed() {
        return sharedPrefsManager.isMonitoringServer();
    }

    @Override
    public String getSavedServer() {
        return sharedPrefsManager.getSavedServerName();
    }
}
