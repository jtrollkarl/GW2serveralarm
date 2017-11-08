package com.moducode.gw2serveralarm.ui;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerFragmentPresenter extends MvpBasePresenter<ServerFragmentContract.View>
        implements ServerFragmentContract.Actions {

    private final SchedulerProvider schedulers;
    private final ServerService serverService;
    private final FcmSubscribeService fcmSubscribeService;
    private final CompositeDisposable compositeDisposable;


    public ServerFragmentPresenter(SchedulerProvider schedulers, ServerService serverService, FcmSubscribeService fcmSubscribeService) {
        this.schedulers = schedulers;
        this.serverService = serverService;
        this.fcmSubscribeService = fcmSubscribeService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void fetchServers(final boolean pullToRefresh) {
        if(isViewAttached()){
            getView().showLoading(pullToRefresh);
        }

        compositeDisposable.add(serverService.listServers("all")
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(new DisposableObserver<List<ServerModel>>() {
                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            getView().showMessage(R.string.fetch_servers_success);
                            getView().showContent();
                        }
                    }

                    @Override
                    public void onNext(@NonNull List<ServerModel> serverModels) {
                        if (isViewAttached()) {
                            Collections.sort(serverModels);
                            getView().setData(serverModels);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(e, pullToRefresh);
                        }
                    }
                }));
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationReceived(MessageEvent messageEvent) {
        getView().logD(messageEvent.getMessage());
        if(isViewAttached()){
            getView().showAlarm();
        }
        // TODO: 2017-11-08 show alarm
    }

    @Override
    public void monitorServer(final ServerModel server) {
        fcmSubscribeService.subscribeToTopic(String.valueOf(server.getId()));
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

}
