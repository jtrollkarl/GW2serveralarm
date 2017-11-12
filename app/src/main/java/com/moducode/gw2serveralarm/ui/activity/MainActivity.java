package com.moducode.gw2serveralarm.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.moducode.gw2serveralarm.ui.ServerFragment;
import com.moducode.gw2serveralarm.ui.activity.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ServerFragment();
    }
}
