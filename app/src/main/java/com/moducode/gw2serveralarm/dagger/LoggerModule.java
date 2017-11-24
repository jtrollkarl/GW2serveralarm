package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.PresenterLogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-24.
 */

@Module
public class LoggerModule {

    @Provides
    @PresenterComponentScope
    PresenterLogger presenterLogger(){
        return new PresenterLogger();
    }

}
