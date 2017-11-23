package com.moducode.gw2serveralarm.ui.fragment;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Jay on 2017-11-21.
 */

public interface AlarmFragmentContract {

    interface View extends MvpView {

    }

    interface Actions extends MvpPresenter<View> {
        void startAlarmService();

        void stopAlarmService();
    }
}
