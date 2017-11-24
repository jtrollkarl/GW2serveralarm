package com.moducode.gw2serveralarm;

import android.util.Log;

/**
 * Created by Jay on 2017-11-24.
 */

public class PresenterLogger {

    public void logD(String tag, String message){
        Log.d(tag, message);
    }

    public void logD(String tag, String message, Throwable e){
        Log.d(tag, message, e);
    }

    public void logE(String tag, String message, Throwable e){
        Log.d(tag, message, e);
    }

}
