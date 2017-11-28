package com.moducode.gw2serveralarm.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.StringRes;

import com.moducode.gw2serveralarm.R;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-09.
 */

public class SharedPrefsManagerImpl implements SharedPrefsManager {

    private static final String SERVER_KEY = "server_key";

    private final SharedPreferences preferences;

    private final String KEY_PREF_NOTIFICATION;
    private final String KEY_ALARM_VIBRATE;
    private final String KEY_ALARM_LIGHT;
    private final String KEY_ALARM_SOUND;

    @Inject
    public SharedPrefsManagerImpl(Context context) {
        this.KEY_PREF_NOTIFICATION = context.getString(R.string.pref_notification_key);
        this.KEY_ALARM_VIBRATE = context.getString(R.string.pref_alarm_vibrate_key);
        this.KEY_ALARM_LIGHT = context.getString(R.string.pref_alarm_light_key);
        this.KEY_ALARM_SOUND = context.getString(R.string.pref_alarm_sound_key);
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
    public void clearSavedTopic() {
        preferences.edit().remove(SERVER_KEY).apply();
    }

    @Override
    public String getSavedServer() {
        return preferences.getString(SERVER_KEY, "empty");
    }

    @Override
    public boolean isVibrateEnabled() {
        return preferences.getBoolean(KEY_ALARM_VIBRATE, true);
    }

    @Override
    public boolean isLedEnabled() {
        return preferences.getBoolean(KEY_ALARM_LIGHT, true);
    }

    @Override
    public String getAlarmUri() {
        return preferences.getString(KEY_ALARM_SOUND, Settings.System.DEFAULT_ALARM_ALERT_URI.toString());
    }

}
