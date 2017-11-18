package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.ui.fragment.PreferencesFragment;

import dagger.Component;

/**
 * Created by Jay on 2017-11-14.
 */

@PresenterComponentScope
@Component(modules = {FcmSubscribeServiceModule.class, SchedulerModule.class, ServerServiceModule.class})
public interface PresenterComponent {

    FcmSubscribeService getFcmSubscribeService();

    SchedulerProvider getSchedulerProvider();

    ServerService getServerService();

    void injectPreferencesFragment(PreferencesFragment preferencesFragment);
}
