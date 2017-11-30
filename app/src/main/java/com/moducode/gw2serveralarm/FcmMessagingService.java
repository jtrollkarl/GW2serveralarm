package com.moducode.gw2serveralarm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.ui.activity.AlarmActivity;

import org.greenrobot.eventbus.EventBus;

public class FcmMessagingService extends FirebaseMessagingService {

    private static final String TAG = FcmMessagingService.class.getSimpleName();


    public FcmMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "test");

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
