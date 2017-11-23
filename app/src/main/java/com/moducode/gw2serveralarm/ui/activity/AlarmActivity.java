package com.moducode.gw2serveralarm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.moducode.gw2serveralarm.ui.fragment.AlarmFragment;

/**
 * Created by Jay on 2017-11-17.
 */

public class AlarmActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @Override
    protected Fragment createFragment() {
        return new AlarmFragment();
    }



    public static Intent newInstance(Context context){
        return new Intent(context, AlarmActivity.class);
    }

}
