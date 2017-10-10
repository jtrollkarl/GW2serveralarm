package com.moducode.gw2serveralarm.ui;

import android.support.annotation.StringRes;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

/**
 * Created by Jay on 2017-08-20.
 */

public interface ServerFragmentContract {

    interface View extends MvpLceView<List<ServerModel>>{
        void showAlarm();
        void logD(String logMsg);
        void showMessage(@StringRes int msg);

    }

    interface Actions extends MvpPresenter<ServerFragmentContract.View>{
        void fetchServers(boolean pullToRefresh);
        void monitorServer(ServerModel server);
        void onPause();
    }

}
