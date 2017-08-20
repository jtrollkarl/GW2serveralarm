package com.moducode.gw2serveralarm;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.RetrofitFactory;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.BaseSchedulerProvider;
import com.moducode.gw2serveralarm.ui.ServerFragmentContract;
import com.moducode.gw2serveralarm.ui.ServerFragmentPresenter;

import java.util.List;

public class ServerFragment extends MvpFragment<ServerFragmentContract.View, ServerFragmentContract.Actions>
        implements ServerFragmentContract.View  {

    private static final String TAG = ServerFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public ServerFragmentContract.Actions createPresenter() {
        return new ServerFragmentPresenter(new BaseSchedulerProvider(), RetrofitFactory.create(ServerService.class));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchServers();
    }

    @Override
    public void showServerList(List<ServerModel> serverModels) {

    }

    @Override
    public void showError(@StringRes int error, Throwable throwable) {
        Log.e(TAG, "presenter error", throwable);
    }
}
