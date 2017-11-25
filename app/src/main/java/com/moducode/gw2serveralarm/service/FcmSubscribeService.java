package com.moducode.gw2serveralarm.service;

import io.reactivex.Completable;

/**
 * Created by Jay on 2017-11-07.
 */

public interface FcmSubscribeService {

    void subscribeToTopic(String topicId);

    void unSubscribeFromTopic();

    void showNotification();

    void removeNotification();

    boolean isSubscribed();

}
