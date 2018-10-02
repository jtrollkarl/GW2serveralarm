package com.moducode.gw2serveralarm.ui;

import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.ImmediateSchedulers;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragmentContract;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragmentPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jay on 2017-09-02.
 */
public class ServerFragmentPresenterTest {

    @Mock
    private FcmSubscribeService fcmSubscribeService;

    @Mock
    private ServerService serverService;

    private ServerFragmentPresenter subject;

    @Mock
    private ServerFragmentContract.View view;

    private ServerModel fullServer;
    private ServerModel nonFullServer;
    private List<ServerModel> serverModelList;

    private static final String testServerName = "test";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        subject = new ServerFragmentPresenter(fcmSubscribeService, new ImmediateSchedulers(), serverService);
        subject.attachView(view);

        fullServer = new ServerModel(1, "Test", "Full");
        nonFullServer = new ServerModel(2, "Test", "Medium");
        serverModelList = Arrays.asList(fullServer, nonFullServer);
    }

    @Test
    public void fetchServers_SUCCESS() {
        when(serverService.listServers()).thenReturn(Observable.just(serverModelList));

        subject.fetchServers(true);

        verify(view).setData(ArgumentMatchers.anyList());
        verify(view).showContent();
    }

    @Test
    public void someTestMethod(){
        Throwable e = new Throwable("Error");
        when(serverService.listServers()).thenReturn(Observable.error(e));


    }

    @Test
    public void fetchServers_ERROR() {
        Throwable e = new Throwable("Error");

        when(serverService.listServers()).thenReturn(Observable.error(e));

        subject.fetchServers(true);

        verify(view).showError(e, true);
    }

    @Test
    public void monitorServer() {
        subject.monitorServer(fullServer);

        verify(fcmSubscribeService).subscribeToServer(fullServer);
    }

    @Test
    public void onResume_MONITORING_TRUE() {
        when(fcmSubscribeService.isSubscribed()).thenReturn(true);
        when(fcmSubscribeService.getSavedServer()).thenReturn(testServerName);

        subject.onResume();

        verify(fcmSubscribeService).showNotification();
        verify(view).showMonitoringView(testServerName);
    }

    @Test
    public void onResume_MONITORING_FALSE() throws Exception{
        when(serverService.listServers()).thenReturn(Observable.just(serverModelList));

        when(fcmSubscribeService.isSubscribed()).thenReturn(false);

        subject.onResume();

        verify(fcmSubscribeService).removeNotification();
        verify(view).hideMonitoringView();
    }

    @Test
    public void onReceiveNotification() throws Exception{
        subject.onNotificationReceived(new MessageEvent("Test"));

        verify(view).hideMonitoringView();
    }

    @Test
    public void onClickMonitoringView() throws Exception{
        when(serverService.listServers()).thenReturn(Observable.<List<ServerModel>>empty());

        subject.onClickMonitoringView();

        verify(fcmSubscribeService).unSubscribeFromTopic();
        verify(view).hideMonitoringView();
    }
}