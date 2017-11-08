package com.moducode.gw2serveralarm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moducode.gw2serveralarm.data.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class FcmMessagingService extends FirebaseMessagingService {

    private static final String TAG = FcmMessagingService.class.getSimpleName();

    public FcmMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "test");
        EventBus.getDefault().post(new MessageEvent(remoteMessage.getMessageId()));
    }

}
