package com.moducode.gw2serveralarm.ui;

import android.support.annotation.StringRes;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.moducode.gw2serveralarm.data.ServerModel;

import java.util.List;

/**
 * Created by Jay on 2017-08-20.
 */

public interface ServerFragmentContract {

    interface View extends MvpView{
        void showServerList(List<ServerModel> serverModels);
        void showError(@StringRes int error, Throwable throwable);
        void showAlarm();
    }

    interface Actions extends MvpPresenter<ServerFragmentContract.View>{
        void fetchServers();
        void monitorServer(ServerModel server);
        void onPause();
    }

}
