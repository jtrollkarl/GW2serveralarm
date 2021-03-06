package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import com.moducode.gw2serveralarm.service.SharedPrefsManager;
import com.moducode.gw2serveralarm.service.SharedPrefsManagerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-26.
 */

@Module(includes = ContextModule.class)
public class SharedPrefsManagerModule {

    @Provides
    @PresenterComponentScope
    public SharedPrefsManager sharedPrefsManager(Context appContext){
        return new SharedPrefsManagerImpl(appContext);
    }

}
