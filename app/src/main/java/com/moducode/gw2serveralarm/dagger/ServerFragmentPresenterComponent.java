package com.moducode.gw2serveralarm.dagger;

import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragmentPresenter;

import dagger.Component;

/**
 * Created by Jay on 2017-11-14.
 */

@ServerFragmentPresenterComponentScope
@Component(modules = FcmSubscribeServiceModule.class)
public interface ServerFragmentPresenterComponent {

    void injectServerFragmentPresenter(ServerFragmentPresenter presenter);



}
