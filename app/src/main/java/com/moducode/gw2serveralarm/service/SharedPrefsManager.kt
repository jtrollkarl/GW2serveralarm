package com.moducode.gw2serveralarm.service


/**
 * Created by Jay on 2017-11-09.
 */

interface SharedPrefsManager {

    fun isMonitoringServer(): Boolean

    fun isNotificationEnabled(): Boolean

    fun getSavedServerId(): String

    fun getSavedServerName(): String

    fun isVibrateEnabled(): Boolean

    fun isLedEnabled(): Boolean

    fun getAlarmUri(): String

    fun saveServerId(id: String)

    fun saveServerName(serverName: String)

    fun clearSavedTopic()
}
