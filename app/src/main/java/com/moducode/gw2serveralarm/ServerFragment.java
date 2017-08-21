package com.moducode.gw2serveralarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.RetrofitFactory;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.BaseSchedulerProvider;
import com.moducode.gw2serveralarm.ui.ServerFragmentContract;
import com.moducode.gw2serveralarm.ui.ServerFragmentPresenter;
import com.moducode.gw2serveralarm.ui.adapter.ServerListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ServerFragment extends MvpFragment<ServerFragmentContract.View, ServerFragmentContract.Actions>
        implements ServerFragmentContract.View,
        AdapterView.OnItemClickListener {

    private static final String TAG = ServerFragment.class.getSimpleName();

    @BindView(R.id.server_list)
    ListView serverList;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        serverList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public ServerFragmentContract.Actions createPresenter() {
        return new ServerFragmentPresenter(new BaseSchedulerProvider(), RetrofitFactory.create(ServerService.class));
    }

    @Override
    public void showServerList(List<ServerModel> serverModels) {
        serverList.setAdapter(new ServerListAdapter(getContext(), serverModels));
    }

    @Override
    public void showError(@StringRes int error, Throwable throwable) {
        Log.e(TAG, "presenter error", throwable);
    }

    @Override
    public void showAlarm() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ServerModel model = (ServerModel) serverList.getItemAtPosition(i);
        presenter.monitorServer(model);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchServers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onPause();
    }


}
