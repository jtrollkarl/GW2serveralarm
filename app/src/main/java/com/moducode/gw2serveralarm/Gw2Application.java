package com.moducode.gw2serveralarm;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Jay on 2017-11-10.
 */

public class Gw2Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

}
