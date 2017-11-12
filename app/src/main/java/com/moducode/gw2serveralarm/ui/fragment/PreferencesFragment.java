package com.moducode.gw2serveralarm.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.moducode.gw2serveralarm.R;

/**
 * Created by Jay on 2017-11-12.
 */

public class PreferencesFragment extends PreferenceFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
