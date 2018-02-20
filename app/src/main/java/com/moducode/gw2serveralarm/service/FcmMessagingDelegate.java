package com.moducode.gw2serveralarm.service;

/**
 * Created by Jay on 2017-11-16.
 */

public interface FcmMessagingDelegate {

    void subscribeToTopic(String topicId);

    void unsubscribeFromTopic(String topicId);

}
