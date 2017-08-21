package com.moducode.gw2serveralarm.ui;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerFragmentPresenter extends MvpBasePresenter<ServerFragmentContract.View>
        implements ServerFragmentContract.Actions {

    private final CompositeDisposable compositeDisposable;
    private final SchedulerProvider schedulers;
    private final ServerService serverService;

    public ServerFragmentPresenter(SchedulerProvider schedulers, ServerService serverService) {
        this.compositeDisposable = new CompositeDisposable();
        this.schedulers = schedulers;
        this.serverService = serverService;
    }

    @Override
    public void fetchServers() {
        compositeDisposable.add(serverService.listServers("all")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(new DisposableSingleObserver<List<ServerModel>>() {
            @Override
            public void onSuccess(@NonNull List<ServerModel> serverModels) {
                if(isViewAttached()){
                    getView().showServerList(serverModels);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if(isViewAttached()){
                    getView().showError(R.string.error_servers_fetch, e);
                }
            }
        }));
    }

    @Override
    public void monitorServer(String serverId) {

    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
    }
}
