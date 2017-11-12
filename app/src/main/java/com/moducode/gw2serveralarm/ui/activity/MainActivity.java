package com.moducode.gw2serveralarm.ui.activity;

import android.support.v4.app.Fragment;

import com.moducode.gw2serveralarm.ui.fragment.ServerFragment;


public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ServerFragment();
    }
}
