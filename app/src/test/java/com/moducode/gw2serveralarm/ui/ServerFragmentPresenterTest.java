package com.moducode.gw2serveralarm.ui;

import com.moducode.gw2serveralarm.PresenterLogger;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.ImmediateSchedulers;
import com.moducode.gw2serveralarm.schedulers.SchedulerProvider;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.service.NotificationService;
import com.moducode.gw2serveralarm.service.SharedPrefsManager;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragmentContract;
import com.moducode.gw2serveralarm.ui.fragment.ServerFragmentPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jay on 2017-09-02.
 */
public class ServerFragmentPresenterTest {

    @Mock
    private PresenterLogger logger;

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = new ServerFragmentPresenter(logger, fcmSubscribeService, new ImmediateSchedulers(), serverService);
        subject.attachView(view);

        fullServer = new ServerModel(1, "Test", "Full");
        nonFullServer = new ServerModel(2, "Test", "Medium");
        serverModelList = Arrays.asList(fullServer, nonFullServer);
    }

    @Test
    public void fetchServers_SUCCESS() throws Exception {
        when(serverService.listServers()).thenReturn(Observable.just(serverModelList));

        subject.fetchServers(true);

        verify(view).setData(ArgumentMatchers.<ServerModel>anyList());
        verify(view).showMessage(R.string.success_fetch_servers);
        verify(view).showContent();
    }

    @Test
    public void fetchServers_ERROR() throws Exception{
        Throwable e = new Throwable("Error");

        when(serverService.listServers()).thenReturn(Observable.<List<ServerModel>>error(e));

        subject.fetchServers(true);

        verify(view).showError(e, true);
    }

    @Test
    public void monitorServer() throws Exception{
        subject.monitorServer(fullServer);

        verify(fcmSubscribeService).subscribeToTopic(fullServer.getIdString());
    }

    @Test
    public void onResume_MONITORING_TRUE() throws Exception {
        when(fcmSubscribeService.isSubscribed()).thenReturn(true);

        subject.onResume();

        verify(fcmSubscribeService).showNotification();
        verify(view).showMonitoringView();
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

        verify(view).showAlarm();
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