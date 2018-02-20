package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.schedulers.BaseSchedulerProvider;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-15.
 */
@Module
public class SchedulerModule {

    @Provides
    @PresenterComponentScope
    SchedulerProvider schedulerProvider(){
        return new BaseSchedulerProvider();
    }

}
