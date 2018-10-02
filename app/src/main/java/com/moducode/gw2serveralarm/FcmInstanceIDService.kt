package com.moducode.gw2serveralarm


import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

import timber.log.Timber

/**
 * Created by Jay on 2017-11-06.
 */

class FcmInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val IID_TOKEN = FirebaseInstanceId.getInstance().token
        Timber.d(IID_TOKEN)
    }

}
