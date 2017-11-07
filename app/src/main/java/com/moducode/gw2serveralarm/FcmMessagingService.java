package com.moducode.gw2serveralarm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmMessagingService extends FirebaseMessagingService {
    public FcmMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
