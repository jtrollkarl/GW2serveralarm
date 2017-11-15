package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import com.moducode.gw2serveralarm.service.NotificationService;
import com.moducode.gw2serveralarm.service.NotificationServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-14.
 */

@Module(includes = ContextModule.class)
public class NotificationServiceModule {

    @Provides
    @PresenterComponentScope
    public NotificationService notificationService(Context context){
        return new NotificationServiceImpl(context);
    }

}
