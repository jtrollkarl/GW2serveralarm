package com.moducode.gw2serveralarm.ui.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import com.moducode.gw2serveralarm.R;

import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;


import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-12.
 */

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

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
        //perhaps not an elegant solution, but switch statement cases
        //cannot be used with fetching resource strings
        if (equalsStringKey(key, R.string.pref_notification_key)) {
            if (!sharedPreferences.getBoolean(getString(R.string.pref_notification_key), true)) {
                fcmSubscribeService.removeNotification();
            } else {
                fcmSubscribeService.showNotification();
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_test_key))) {
            fcmSubscribeService.subscribeToServer(ServerModel.getFakeServer());
            getActivity().finish();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private boolean equalsStringKey(String key, @StringRes int resId) {
        return key.equals(getString(resId));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }
}
