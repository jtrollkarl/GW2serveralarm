package com.moducode.gw2serveralarm.ui.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragment;


public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ServerFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(PreferenceActivity.newInstance(getApplicationContext()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
