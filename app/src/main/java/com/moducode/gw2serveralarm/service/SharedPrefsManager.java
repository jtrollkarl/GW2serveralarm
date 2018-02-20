package com.moducode.gw2serveralarm.service;



/**
 * Created by Jay on 2017-11-09.
 */

public interface SharedPrefsManager {

    boolean isMonitoringServer();

    boolean isNotificationEnabled();

    void saveServerId(String id);

    String getSavedServerId();

    void saveServerName(String serverName);

    String getSavedServerName();

    void clearSavedTopic();


    boolean isVibrateEnabled();

    boolean isLedEnabled();

    String getAlarmUri();
}
