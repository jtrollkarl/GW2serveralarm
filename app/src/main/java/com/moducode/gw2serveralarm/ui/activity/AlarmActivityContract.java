package com.moducode.gw2serveralarm.ui.activity;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Jay on 2017-11-21.
 */

public interface AlarmActivityContract {

    interface View extends MvpView {
        void showServerName(String serverName);
    }

    interface Actions extends MvpPresenter<View> {
        void startAlarmService();

        void stopAlarmService();
    }
}
