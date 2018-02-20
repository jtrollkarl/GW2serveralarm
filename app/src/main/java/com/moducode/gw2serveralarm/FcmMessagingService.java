package com.moducode.gw2serveralarm;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.ui.activity.AlarmActivity;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class FcmMessagingService extends FirebaseMessagingService {

    public FcmMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d("Remote message received");

        startActivity(AlarmActivity.newInstance(getApplicationContext()));
        EventBus.getDefault().post(new MessageEvent(extractPayload(remoteMessage)));
    }

    private String extractPayload(RemoteMessage message){
        return "Key: "+  message.getCollapseKey() +
                " From: " + message.getFrom() +
                " Id: "+ message.getMessageId() +
                " Type: "+ message.getMessageType() +
                " Sent at: " + message.getSentTime() +
                " To: " + message.getTo() + extractNotification(message);
    }

    private String extractNotification(RemoteMessage message){
        if(message.getNotification() != null){
            return message.getNotification().getTitle() + " " + message.getNotification().getBody();
        }else {
            return "No notification";
        }
    }
}
