package com.moducode.gw2serveralarm.ui.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.app.Fragment;

import android.support.v7.app.AppCompatActivity;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.ui.fragment.PreferencesFragment;

/**
 * Created by Jay on 2017-11-12.
 */

public class PreferenceActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        FragmentManager fm = getFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if(f == null){
            f = new PreferencesFragment();
            fm.beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }


    public static Intent newInstance(Context currentActivity){
        return new Intent(currentActivity, PreferenceActivity.class);
    }
}
