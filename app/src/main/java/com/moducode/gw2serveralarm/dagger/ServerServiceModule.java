package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.retrofit.RetrofitFactory;
import com.moducode.gw2serveralarm.retrofit.ServerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-15.
 */
@Module
public class ServerServiceModule {

    @Provides
    @PresenterComponentScope
    ServerService serverService(){
        return RetrofitFactory.INSTANCE.create(ServerService.class);
    }
}
