package com.moducode.gw2serveralarm.service

import android.content.Context
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.preference.PreferenceManager
import androidx.content.edit

import com.moducode.gw2serveralarm.R

import javax.inject.Inject

/**
 * Created by Jay on 2017-11-09.
 */

class SharedPrefsManagerImpl @Inject constructor(context: Context) : SharedPrefsManager {

    private val SERVER_ID_KEY = "server_id_key"
    private val SERVER_NAME_KEY = "server_name_key"

    private val preferences: SharedPreferences

    private val KEY_PREF_NOTIFICATION: String
    private val KEY_ALARM_VIBRATE: String
    private val KEY_ALARM_LIGHT: String
    private val KEY_ALARM_SOUND: String

    init {
        // TODO: 2018-02-25 this is redundant
        this.KEY_PREF_NOTIFICATION = context.getString(R.string.pref_notification_key)
        this.KEY_ALARM_VIBRATE = context.getString(R.string.pref_alarm_vibrate_key)
        this.KEY_ALARM_LIGHT = context.getString(R.string.pref_alarm_light_key)
        this.KEY_ALARM_SOUND = context.getString(R.string.pref_alarm_sound_key)
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun isMonitoringServer(): Boolean = preferences.contains(SERVER_ID_KEY)


    override fun isNotificationEnabled(): Boolean = preferences.getBoolean(KEY_PREF_NOTIFICATION, true)


    override fun saveServerId(id: String) {
        preferences.edit { putString(SERVER_ID_KEY, id) }
    }

    override fun clearSavedTopic() {
        preferences.edit { remove(SERVER_ID_KEY) }
    }

    override fun getSavedServerId(): String = preferences.getString(SERVER_ID_KEY, "empty")


    override fun isVibrateEnabled(): Boolean = preferences.getBoolean(KEY_ALARM_VIBRATE, true)


    override fun isLedEnabled(): Boolean = preferences.getBoolean(KEY_ALARM_LIGHT, true)


    override fun getAlarmUri(): String = preferences.getString(KEY_ALARM_SOUND, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString())


    override fun saveServerName(serverName: String) {
        preferences.edit { putString(SERVER_NAME_KEY, serverName) }
    }

    override fun getSavedServerName(): String = preferences.getString(SERVER_NAME_KEY, "?")

}
