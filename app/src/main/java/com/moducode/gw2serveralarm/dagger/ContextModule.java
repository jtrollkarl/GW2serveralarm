package com.moducode.gw2serveralarm.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 2017-11-14.
 */

@Module
public class ContextModule {

    private final Context appContext;

    public ContextModule(Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @ServerFragmentPresenterComponentScope
    public Context context(){
        return appContext;
    }

}
