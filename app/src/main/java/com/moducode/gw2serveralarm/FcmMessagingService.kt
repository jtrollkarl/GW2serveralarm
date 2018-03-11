package com.moducode.gw2serveralarm


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moducode.gw2serveralarm.data.MessageEvent
import com.moducode.gw2serveralarm.ui.activity.AlarmActivity

import org.greenrobot.eventbus.EventBus

import timber.log.Timber

class FcmMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Timber.d("Remote message received")

        startActivity(AlarmActivity.newInstance(applicationContext))
        EventBus.getDefault().post(MessageEvent(extractPayload(remoteMessage)))
    }

    private fun extractPayload(message: RemoteMessage?): String = "Key: ${message?.collapseKey} From: ${message?.from} Id: ${message?.messageId} Type: ${message?.messageType} Sent at: ${message?.sentTime} To:  ${message?.to} ${extractNotification(message)}"

    private fun extractNotification(message: RemoteMessage?): String = message?.notification?.title?.plus(message.notification?.body) ?: "No notification"
}
