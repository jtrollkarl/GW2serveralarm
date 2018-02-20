package com.moducode.gw2serveralarm.service;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Jay on 2017-11-16.
 */

public class FcmMessagingDelegateImpl implements FcmMessagingDelegate {

    @Override
    public void subscribeToTopic(String topicId) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicId);
    }

    @Override
    public void unsubscribeFromTopic(String topicId) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicId);
    }
}
