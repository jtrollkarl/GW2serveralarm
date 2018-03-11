package com.moducode.gw2serveralarm.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


import com.google.android.gms.ads.AdRequest

import com.google.android.gms.ads.MobileAds

import com.moducode.gw2serveralarm.R


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


}
