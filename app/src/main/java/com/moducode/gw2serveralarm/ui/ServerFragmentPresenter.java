package com.moducode.gw2serveralarm.ui;

import android.util.Log;

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
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
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
                .subscribeWith(new DisposableObserver<List<ServerModel>>() {
                    @Override
                    public void onComplete() {
                        if(isViewAttached()){
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
        compositeDisposable.add(Observable.interval(5, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<List<ServerModel>>>() {
                    @Override
                    public ObservableSource<List<ServerModel>> apply(@NonNull Long aLong) throws Exception {
                        return serverService.listServers(String.valueOf(server.getId()));
                    }
                })
                .takeUntil(new Predicate<List<ServerModel>>() {
                    @Override
                    public boolean test(@NonNull List<ServerModel> serverModels) throws Exception {
                        return serverModels.get(0).getPopulation().equals("Full");
                    }
                })
                .retry()
                .observeOn(schedulers.ui())
                .subscribeOn(schedulers.io())
                .subscribeWith(new DisposableObserver<List<ServerModel>>(){

                    @Override
                    public void onNext(@NonNull List<ServerModel> serverModels) {
                        if(isViewAttached()){
                            getView().logD("ping");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if(isViewAttached()){
                            getView().showError(R.string.error_servers_fetch, e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(isViewAttached()){
                            getView().logD("complete");
                        }
                    }
                }));

    }


    @Override
    public void onPause() {
        compositeDisposable.clear();
    }
}
