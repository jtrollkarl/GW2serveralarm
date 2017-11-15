package com.moducode.gw2serveralarm.service;

/**
 * Created by Jay on 2017-11-09.
 */

public interface SharedPrefsManager {

    boolean isMonitoringServer();

    boolean isNotificationEnabled();

    void saveServer(String id);

    void clearSavedTopic();

    String getSavedServer();

}
