package com.moducode.gw2serveralarm.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.moducode.gw2serveralarm.R;

/**
 * Created by Jay on 2017-11-09.
 */

public class SharedPrefsManagerImpl implements SharedPrefsManager {

    private static final String SERVER_KEY = "server_key";

    private final SharedPreferences preferences;

    private final String KEY_PREF_NOTIFICATION;

    public SharedPrefsManagerImpl(Context context) {
        this.KEY_PREF_NOTIFICATION = context.getString(R.string.pref_notification_key);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean isMonitoringServer() {
        return preferences.contains(SERVER_KEY);
    }

    @Override
    public boolean isNotificationEnabled() {
        return preferences.getBoolean(KEY_PREF_NOTIFICATION, true);
    }

    @Override
    public void saveServer(String id) {
        preferences.edit().putString(SERVER_KEY, id).apply();
    }

    @Override
    public void clearSavedPrefs() {
        preferences.edit().remove(SERVER_KEY).apply();
    }

    @Override
    public String getSavedServer() {
        return preferences.getString(SERVER_KEY, "empty");
    }


}
