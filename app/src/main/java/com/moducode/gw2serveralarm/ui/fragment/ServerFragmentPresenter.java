package com.moducode.gw2serveralarm.ui.fragment;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
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
import timber.log.Timber;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerFragmentPresenter extends MvpBasePresenter<ServerFragmentContract.View>
        implements ServerFragmentContract.Actions {


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
        ifViewAttached(view -> view.showLoading(pullToRefresh));

        compositeDisposable.add(serverService.listServers()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(new DisposableObserver<List<ServerModel>>() {
                    @Override
                    public void onComplete() {
                        Timber.d("Fetched servers from network");
                        ifViewAttached(MvpLceView::showContent);
                    }

                    @Override
                    public void onNext(@NonNull List<ServerModel> serverModels) {
                        ifViewAttached(view -> {
                            Collections.sort(serverModels);
                            view.setData(serverModels);
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e,"Error fetching servers");
                        ifViewAttached(v -> v.showError(e, pullToRefresh));
                    }
                }));
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotificationReceived(MessageEvent messageEvent) {
        Timber.d("Notification received message was: %s", messageEvent.getMessage());
        fcmSubscribeService.unSubscribeFromTopic();
        ifViewAttached(v -> v.hideMonitoringView());
    }

    @Override
    public void onClickMonitoringView() {
        fcmSubscribeService.unSubscribeFromTopic();
        ifViewAttached(v -> v.hideMonitoringView());
        fetchServers(false);
    }

    @Override
    public void monitorServer(final ServerModel server) {
        ifViewAttached(v -> v.showMonitoringView(server.getName()));
        fcmSubscribeService.subscribeToServer(server);
    }

    @Override
    public void onResume() {
        if(fcmSubscribeService.isSubscribed()){
            fcmSubscribeService.showNotification();
            ifViewAttached(view -> view.showMonitoringView(fcmSubscribeService.getSavedServer()));
        }else {
            fcmSubscribeService.removeNotification();
            ifViewAttached(view -> view.hideMonitoringView());
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
