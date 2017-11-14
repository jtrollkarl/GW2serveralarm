package com.moducode.gw2serveralarm.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.RetrofitFactory;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.BaseSchedulerProvider;
import com.moducode.gw2serveralarm.service.FcmSubscribeServiceImpl;
import com.moducode.gw2serveralarm.service.NotificationServiceImpl;
import com.moducode.gw2serveralarm.service.SharedPrefsManagerImpl;
import com.moducode.gw2serveralarm.ui.adapter.ServerListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ServerFragment extends MvpLceFragment<SwipeRefreshLayout, List<ServerModel>, ServerFragmentContract.View, ServerFragmentContract.Actions>
        implements ServerFragmentContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        ServerListAdapter.OnItemClickListener {

    private static final String TAG = ServerFragment.class.getSimpleName();

    Unbinder unbinder;
    @BindView(R.id.server_recycler)
    RecyclerView serverRecycler;
    @BindView(R.id.contentView)
    SwipeRefreshLayout contentView;
    @BindView(R.id.monitoringView)
    TextView monitoringView;
    @BindView(R.id.loadingView)
    ProgressBar loadingView;
    @BindView(R.id.errorView)
    TextView errorView;

    private ServerListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentView.setOnRefreshListener(this);

        adapter = new ServerListAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        serverRecycler.setLayoutManager(layoutManager);
        serverRecycler.addItemDecoration(new DividerItemDecoration(serverRecycler.getContext(), layoutManager.getOrientation()));
        serverRecycler.setAdapter(adapter);
    }

    @Override
    public ServerFragmentContract.Actions createPresenter() {
        return new ServerFragmentPresenter(
                new BaseSchedulerProvider(),
                RetrofitFactory.create(ServerService.class),
                new FcmSubscribeServiceImpl());
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, false);
        Log.e(TAG, "presenter error", e);
        contentView.setRefreshing(false);
    }

    @Override
    public void logD(String logMsg) {
        Log.d(TAG, logMsg);
    }

    @Override
    public void showMessage(@StringRes int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMonitoringView() {
        contentView.setVisibility(View.GONE);
        monitoringView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMonitoringView() {
        contentView.setVisibility(View.VISIBLE);
        monitoringView.setVisibility(View.GONE);
    }

    @Override
    public void showAlarm() {
        Log.d(TAG, "show alarm!!!!!!!");
    }

    @Override
    public void onServerClick(ServerModel server) {
        presenter.monitorServer(server);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "Error fetching servers";
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void setData(List<ServerModel> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.fetchServers(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick({R.id.monitoringView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.monitoringView:
                presenter.onClickMonitoringView();
                break;
        }
    }

}
