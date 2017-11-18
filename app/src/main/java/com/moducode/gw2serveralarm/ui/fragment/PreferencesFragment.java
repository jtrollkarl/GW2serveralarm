package com.moducode.gw2serveralarm.ui.fragment;

import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.moducode.gw2serveralarm.R;

import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;


import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-12.
 */

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = PreferencesFragment.class.getSimpleName();

    @Inject
    FcmSubscribeService fcmSubscribeService;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PresenterComponent component = DaggerPresenterComponent.builder()
                .contextModule(new ContextModule(getActivity().getApplicationContext()))
                .build();

        component.injectPreferencesFragment(this);

        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "KEY_SHOW_NOTIFICATION":
                if (!sharedPreferences.getBoolean("KEY_SHOW_NOTIFICATION", true)){
                    fcmSubscribeService.removeNotification();
                }else{
                    fcmSubscribeService.showNotification();
                }
        }

    }
}
