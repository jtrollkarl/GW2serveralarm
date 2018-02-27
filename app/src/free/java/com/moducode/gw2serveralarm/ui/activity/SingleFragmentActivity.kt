package com.moducode.gw2serveralarm.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.google.android.gms.ads.AdRequest

import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.moducode.gw2serveralarm.R

import timber.log.Timber

import kotlinx.android.synthetic.free.activity_main.*

/**
 * Created by Jay on 2018-01-19.
 */

abstract class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        MobileAds.initialize(this, getString(R.string.admob_app_id))

        loadAd()
        setupFragment()
        checkPlayServices()
    }

    protected abstract fun createFragment(): Fragment

    private fun setupFragment() {
        var f: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (f == null) {
            f = createFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit()
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        ad_server_list.loadAd(adRequest)
    }

    // TODO: 2017-11-27 tie this method to some sort of service
    private fun checkPlayServices() {
        val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)

        when (resultCode) {
            ConnectionResult.SUCCESS -> Timber.d("PlayServices installed.")
            else -> {
                Toast.makeText(this, R.string.error_play_services, Toast.LENGTH_LONG).show()
                Timber.w("Play services are not installed? App will not function correctly.")
            }
        }
    }

}
