package com.moducode.gw2serveralarm.ui;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.data.MessageEvent;
import com.moducode.gw2serveralarm.data.ServerModel;
import com.moducode.gw2serveralarm.retrofit.ServerService;
import com.moducode.gw2serveralarm.schedulers.ImmediateSchedulers;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;
import com.moducode.gw2serveralarm.service.SharedPrefsManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
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

    private ServerFragmentPresenter subject;

    @Mock
    private ServerFragmentContract.View view;

    @Mock
    private ServerService serverService;

    @Mock
    private FcmSubscribeService fcmSubscribeService;

    @Mock
    private SharedPrefsManager sharedPrefsManager;

    private ServerModel fullServer;
    private ServerModel nonFullServer;
    private List<ServerModel> serverModelList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        subject = new ServerFragmentPresenter(new ImmediateSchedulers(), serverService, fcmSubscribeService, sharedPrefsManager);
        subject.attachView(view);

        fullServer = new ServerModel(1, "Test", "Full");
        nonFullServer = new ServerModel(2, "Test", "Medium");
        serverModelList = Arrays.asList(fullServer, nonFullServer);
    }

    @Test
    public void fetchServers_SUCCESS() throws Exception {
        when(serverService.listServers("all")).thenReturn(Observable.just(ArgumentMatchers.<ServerModel>anyList()));

        subject.fetchServers(true);

        verify(view).setData(ArgumentMatchers.<ServerModel>anyList());
        verify(view).showMessage(R.string.fetch_servers_success);
        verify(view).showContent();
    }

    @Test
    public void monitorServer() throws Exception {
        subject.monitorServer(fullServer);
        verify(fcmSubscribeService).subscribeToTopic(String.valueOf(fullServer.getId()));
        verify(view).showMonitoringView();
        verify(sharedPrefsManager).saveServer(String.valueOf(fullServer.getId()));
    }

    @Test
    public void onNotificationReceived() throws Exception{
        when(sharedPrefsManager.getSavedServer()).thenReturn("test");

        subject.onNotificationReceived(new MessageEvent("test"));
        verify(view).hideMonitoringView();
        verify(view).showAlarm();
        verify(fcmSubscribeService).unSubscribeFromTopic(anyString());
        verify(sharedPrefsManager).clearSavedPrefs();
    }

    @Test
    public void onResume_MONITORING_FALSE() throws Exception{
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(false);
        when(serverService.listServers("all")).thenReturn(Observable.just(serverModelList));

        subject.onResume();
        verify(view).setData(ArgumentMatchers.<ServerModel>anyList());
        verify(view).showMessage(R.string.fetch_servers_success);
        verify(view).showContent();
        verify(view).hideMonitoringView();
    }

    @Test
    public void onResume_MONITORING_TRUE() throws Exception{
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(true);
        when(serverService.listServers("all")).thenReturn(Observable.just(serverModelList));

        subject.onResume();
        verify(view).showMonitoringView();
    }

    @Test
    public void onClickMonitoringView() throws Exception{
        when(serverService.listServers("all")).thenReturn(Observable.just(serverModelList));
        when(sharedPrefsManager.getSavedServer()).thenReturn("test");

        subject.onClickMonitoringView();

        verify(sharedPrefsManager).clearSavedPrefs();
        verify(fcmSubscribeService).unSubscribeFromTopic("test");
        verify(view).hideMonitoringView();
    }

}