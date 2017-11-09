package com.moducode.gw2serveralarm.service;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Jay on 2017-11-07.
 */

public class FcmSubscribeServiceImpl implements FcmSubscribeService {

    @Override
    public void subscribeToTopic(String topicId) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicId);
    }

    @Override
    public void unSubscribeFromTopic(String topicId) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicId);
    }

}
