package com.moducode.gw2serveralarm.service;


import com.moducode.gw2serveralarm.data.ServerModel;

/**
 * Created by Jay on 2017-11-07.
 */

public interface FcmSubscribeService {

    void subscribeToServer(ServerModel server);

    void unSubscribeFromTopic();

    void showNotification();

    void removeNotification();

    boolean isSubscribed();

    String getSavedServer();
}
