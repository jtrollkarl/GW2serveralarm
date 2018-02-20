package com.moducode.gw2serveralarm;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

/**
 * Created by Jay on 2017-11-06.
 */

public class FcmInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
        Timber.d(IID_TOKEN);
    }

}
