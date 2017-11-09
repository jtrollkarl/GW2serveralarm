package com.moducode.gw2serveralarm.ui;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.ImmediateSchedulers;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jay on 2017-09-02.
 */
public class ServerFragmentPresenterTest {

    private ServerFragmentPresenter serverPresenter;

    @Mock
    private ServerFragmentContract.View view;

    @Mock
    private ServerService serverService;

    @Mock
    FcmSubscribeService subscribeService;

    private ServerModel fullServer;
    private ServerModel nonFullServer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        serverPresenter = new ServerFragmentPresenter(new ImmediateSchedulers(), serverService, subscribeService);
        serverPresenter.attachView(view);

        fullServer = new ServerModel(1, "Test", "Full");
        nonFullServer = new ServerModel(2, "Test", "Medium");
    }

    @Test
    public void fetchServers_SUCCESS() throws Exception {
        when(serverService.listServers("all")).thenReturn(Observable.just(ArgumentMatchers.<ServerModel>anyList()));

        serverPresenter.fetchServers(true);

        verify(view).setData(ArgumentMatchers.<ServerModel>anyList());
        verify(view).showMessage(R.string.fetch_servers_success);
        verify(view).showContent();
    }

    @Test
    public void monitorServer() throws Exception {
        serverPresenter.monitorServer(fullServer);
        verify(view).showObservingView();
        verify(subscribeService).subscribeToTopic("1");
    }

    @Test
    public void onNotificationReceived() throws Exception{
        serverPresenter.onNotificationReceived(new MessageEvent("test"));
        verify(view).hideObservingView();
        verify(view).showAlarm();
    }
}