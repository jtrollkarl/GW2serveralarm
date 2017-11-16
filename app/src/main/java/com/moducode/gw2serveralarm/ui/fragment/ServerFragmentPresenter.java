package com.moducode.gw2serveralarm.ui.fragment;

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

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerFragmentPresenter extends MvpBasePresenter<ServerFragmentContract.View>
        implements ServerFragmentContract.Actions {

    private static final String TAG = ServerFragmentPresenter.class.getSimpleName();

    private final FcmSubscribeService fcmSubscribeService;
    private final SchedulerProvider schedulers;
    private final ServerService serverService;

    private final CompositeDisposable compositeDisposable;

    @Inject
    public ServerFragmentPresenter(FcmSubscribeService fcmSubscribeService, SchedulerProvider schedulers, ServerService serverService) {
        this.fcmSubscribeService = fcmSubscribeService;
        this.schedulers = schedulers;
        this.serverService = serverService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void fetchServers(final boolean pullToRefresh) {
        if(isViewAttached()){
            getView().showLoading(pullToRefresh);
        }

        compositeDisposable.add(serverService.listServers()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(new DisposableObserver<List<ServerModel>>() {
                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            getView().showMessage(R.string.success_fetch_servers);
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
        fcmSubscribeService.unSubscribeFromTopic();
        if(isViewAttached()){
            getView().showAlarm();
            getView().hideMonitoringView();
        }
    }

    @Override
    public void onClickMonitoringView() {
        fcmSubscribeService.unSubscribeFromTopic();
        if(isViewAttached()){
            getView().hideMonitoringView();
        }
        fetchServers(false);
    }

    @Override
    public void monitorServer(final ServerModel server) {
        if(isViewAttached()){
            getView().showMonitoringView();
        }
        fcmSubscribeService.subscribeToTopic(server.getIdString());
    }

    @Override
    public void onResume() {
        if(fcmSubscribeService.isSubscribed()){
            fcmSubscribeService.showNotification();
            if(isViewAttached()) getView().showMonitoringView();
        }else {
            fcmSubscribeService.removeNotification();
            if(isViewAttached()) getView().hideMonitoringView();
            fetchServers(false);
        }
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
        fcmSubscribeService.removeNotification();
    }


}
