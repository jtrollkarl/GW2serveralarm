package com.moducode.gw2serveralarm;

import android.app.Application;
import android.os.Build;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Jay on 2017-11-10.
 */

public class Gw2Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        
    }

    private void initTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
