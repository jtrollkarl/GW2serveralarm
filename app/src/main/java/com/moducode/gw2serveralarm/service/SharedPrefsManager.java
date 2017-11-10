package com.moducode.gw2serveralarm.service;

/**
 * Created by Jay on 2017-11-09.
 */

public interface SharedPrefsManager {

    boolean isMonitoringServer();

    void saveServer(String id);

    void clearSavedPrefs();

    String getSavedServer();

}
