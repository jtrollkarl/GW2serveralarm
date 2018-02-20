package com.moducode.gw2serveralarm.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.moducode.gw2serveralarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Jay on 2018-01-19.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {


    @BindView(R.id.ad_server_list)
    AdView adServerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        loadAd();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFragment();
        checkPlayServices();
    }

    protected abstract Fragment createFragment();

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if (f == null) {
            f = createFragment();
            fm.beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }

    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adServerList.loadAd(adRequest);
    }

    // TODO: 2017-11-27 tie this method to some sort of service
    private void checkPlayServices() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            Toast.makeText(this, R.string.error_play_services, Toast.LENGTH_LONG).show();
            Timber.w("Play services are not installed. App will not function correctly.");
        } else {
            Timber.d("PlayServices installed.");
        }
    }

}
