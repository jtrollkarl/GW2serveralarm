package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.service.AlarmServiceManagerImpl;
import com.moducode.gw2serveralarm.service.FcmMessagingDelegate;
import com.moducode.gw2serveralarm.service.FcmMessagingDelegateImpl;
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

@Module(includes = {ContextModule.class, SharedPrefsManagerModule.class})
public class FcmSubscribeServiceModule {

    @Provides
    @PresenterComponentScope
    public FcmSubscribeService fcmSubscribeService(FcmMessagingDelegate fcmMessagingDelegate,
                                                   SharedPrefsManager sharedPrefsManager,
                                                   NotificationService notificationService){
        return new FcmSubscribeServiceImpl(fcmMessagingDelegate, sharedPrefsManager, notificationService);
    }

    @Provides
    @PresenterComponentScope
    public NotificationService notificationService(Context appContext){
        return new NotificationServiceImpl(appContext);
    }

    @Provides
    @PresenterComponentScope
    public FcmMessagingDelegate fcmMessagingDelegate(){
        return new FcmMessagingDelegateImpl();
    }

}
