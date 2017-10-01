package com.moducode.gw2serveralarm.ui;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Jay on 2017-08-20.
 */

public class ServerFragmentPresenter extends MvpBasePresenter<ServerFragmentContract.View>
        implements ServerFragmentContract.Actions {

    private final CompositeDisposable compositeDisposable;
    private final SchedulerProvider schedulers;
    private final ServerService serverService;
    private Disposable serverObserver;

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
                .subscribeWith(new DisposableObserver<List<ServerModel>>() {
                    @Override
                    public void onComplete() {
                        if (isViewAttached()) {
                            getView().showMessage(R.string.fetch_servers_success);
                        }
                    }

                    @Override
                    public void onNext(@NonNull List<ServerModel> serverModels) {
                        if (isViewAttached()) {
                            getView().showServerList(serverModels);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(R.string.error_servers_fetch, e);
                        }
                    }
                }));
    }

    @Override
    public void monitorServer(final ServerModel server) {
        if(!server.getPopulation().equals("Full")){
            if(isViewAttached()){
                getView().showMessage(R.string.msg_server_not_full);
                return;
            }
        }

        if(serverObserver != null){
            serverObserver.dispose();
        }

        serverObserver = getMonitoringObservable(server).subscribeWith(getMonitoringSubscriber());
    }

    private Observable<ServerModel> getMonitoringObservable(final ServerModel server){
        return Observable.interval(5, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<ServerModel>>() {
                    @Override
                    public ObservableSource<ServerModel> apply(@NonNull Long aLong) throws Exception {
                        return serverService.getServer(String.valueOf(server.getId()));
                    }
                })
                .takeUntil(new Predicate<ServerModel>() {
                    @Override
                    public boolean test(@NonNull ServerModel serverModel) throws Exception {
                        return !serverModel.getPopulation().equals("Full");
                    }
                })
                .retry()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io());
    }

    private DisposableObserver<ServerModel> getMonitoringSubscriber(){
        return new DisposableObserver<ServerModel>() {

            @Override
            public void onNext(@NonNull ServerModel serverModel) {
                if (isViewAttached()) {
                    getView().logD("ping");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (isViewAttached()) {
                    getView().showError(R.string.error_servers_fetch, e);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getView().logD("complete");
                    getView().showAlarm();
                }
            }
        };
    }

    @Override
    public void onPause() {
        compositeDisposable.clear();
    }
}
