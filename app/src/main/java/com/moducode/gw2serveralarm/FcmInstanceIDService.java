package com.moducode.gw2serveralarm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Jay on 2017-11-06.
 */

public class FcmInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = FcmInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, IID_TOKEN);
    }
}
