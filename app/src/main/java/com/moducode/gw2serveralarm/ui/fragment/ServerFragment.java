package com.moducode.gw2serveralarm.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.dagger.ContextModule;

import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.ui.adapter.ServerListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class ServerFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<ServerModel>, ServerFragmentContract.View, ServerFragmentContract.Actions>
        implements ServerFragmentContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        ServerListAdapter.OnItemClickListener {


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
    public List<ServerModel> getData() {
        return adapter == null ? null : adapter.getData();
    }

    @NonNull
    @Override
    public LceViewState<List<ServerModel>, ServerFragmentContract.View> createViewState() {
        return new RetainingLceViewState<>();
    }

    @NonNull
    @Override
    public ServerFragmentContract.Actions createPresenter() {
        Context appContext = getActivity().getApplicationContext();

        PresenterComponent component = DaggerPresenterComponent.builder()
                .contextModule(new ContextModule(appContext))
                .build();

        return new ServerFragmentPresenter(component.getFcmSubscribeService(),
                component.getSchedulerProvider(),
                component.getServerService());
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, false);
        contentView.setRefreshing(false);
    }

    @Override
    public void showMessage(@StringRes int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMonitoringView(String serverName) {
        contentView.setVisibility(View.GONE);
        monitoringView.setVisibility(View.VISIBLE);
        monitoringView.setText(getString(R.string.tv_monitoring_msg, serverName));
    }

    @Override
    public void hideMonitoringView() {
        contentView.setVisibility(View.VISIBLE);
        monitoringView.setVisibility(View.GONE);
    }

    @Override
    public void onServerClick(ServerModel server) {
        presenter.monitorServer(server);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getString(R.string.error_fetch_servers);
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
        Timber.d("Refreshing servers...");
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
        super.onDestroy();
        presenter.onDestroy();
        Timber.d("onDestroy called");
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
