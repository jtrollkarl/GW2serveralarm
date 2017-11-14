package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.service.FcmSubscribeServiceImpl;
import com.moducode.gw2serveralarm.service.NotificationService;
import com.moducode.gw2serveralarm.service.SharedPrefsManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-14.
 */

@Module(includes = {NotificationServiceModule.class, SharedPrefsModule.class})
public class FcmSubscribeServiceModule {

    @Provides
    public FcmSubscribeService fcmSubscribeService(SharedPrefsManager sharedPrefsManager, NotificationService notificationService){
        return new FcmSubscribeServiceImpl(sharedPrefsManager, notificationService);
    }


}
