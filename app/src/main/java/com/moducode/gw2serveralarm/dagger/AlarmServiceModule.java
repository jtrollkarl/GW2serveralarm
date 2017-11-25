package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.service.AlarmServiceManagerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-24.
 */

@Module(includes = ContextModule.class)
public class AlarmServiceModule {

    @Provides
    @PresenterComponentScope
    AlarmServiceManager alarmServiceManager(Context appContext){
        return new AlarmServiceManagerImpl(appContext);
    }

}
