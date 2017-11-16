package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.service.FcmSubscribeServiceImpl;
import com.moducode.gw2serveralarm.service.NotificationService;
import com.moducode.gw2serveralarm.service.NotificationServiceImpl;
import com.moducode.gw2serveralarm.service.SharedPrefsManager;
import com.moducode.gw2serveralarm.service.SharedPrefsManagerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-14.
 */

@Module(includes = {ContextModule.class})
public class FcmSubscribeServiceModule {

    // TODO: 2017-11-15 this can be cleaned to include notificationservice and sharedprefs?

    @Provides
    @PresenterComponentScope
    public FcmSubscribeService fcmSubscribeService(SharedPrefsManager sharedPrefsManager, NotificationService notificationService){
        return new FcmSubscribeServiceImpl(sharedPrefsManager, notificationService);
    }


    @Provides
    @PresenterComponentScope
    public SharedPrefsManager sharedPrefsManager(Context appContext){
        return new SharedPrefsManagerImpl(appContext);
    }

    @Provides
    @PresenterComponentScope
    public NotificationService notificationService(Context appContext){
        return new NotificationServiceImpl(appContext);
    }

}
