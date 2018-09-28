package com.moducode.gw2serveralarm.service;

import com.moducode.gw2serveralarm.data.ServerModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_SMART_NULLS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

/**
 * Created by Jay on 2017-11-16.
 */
public class FcmSubscribeServiceImplTest {

    @Mock
    private FcmMessagingDelegate fcmMessagingDelegate;

    @Mock
    private NotificationService notificationService;

    @Mock
    private SharedPrefsManager sharedPrefsManager;

    @Mock
    private AlarmServiceManager alarmServiceManager;

    @InjectMocks
    private FcmSubscribeServiceImpl subject;

    private static final String topicId = "testTopic";
    private static final String testServerName = "test";

    private ServerModel nonFullServer = new ServerModel(2, "Test", "Medium");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void subscribeToTopic_NOTIFICATION_TRUE() {
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(true);
        when(sharedPrefsManager.getSavedServerName()).thenReturn(testServerName);

        subject.subscribeToServer(nonFullServer);

        verify(fcmMessagingDelegate).subscribeToTopic(nonFullServer.getIdString());
        verify(sharedPrefsManager).saveServerId(nonFullServer.getIdString());
        verify(sharedPrefsManager).saveServerName(nonFullServer.getName());
        verify(notificationService).showMonitoringNotification(testServerName);
    }

    @Test
    public void subscribeToTopic_NOTIFICATION_FALSE() {
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(false);

        subject.subscribeToServer(nonFullServer);

        verify(fcmMessagingDelegate).subscribeToTopic(nonFullServer.getIdString());
        verify(sharedPrefsManager).saveServerId(nonFullServer.getIdString());
        verify(sharedPrefsManager).saveServerName(nonFullServer.getName());
        verify(notificationService, never()).showMonitoringNotification(nonFullServer.getName());
    }

    @Test
    public void unSubscribeFromTopic() throws Exception {
        when(sharedPrefsManager.getSavedServerId()).thenReturn(topicId);

        subject.unSubscribeFromTopic();

        verify(sharedPrefsManager).clearSavedTopic();
        verify(fcmMessagingDelegate).unsubscribeFromTopic(topicId);
        verify(notificationService).removeMonitoringNotification();
    }

    @Test
    public void isSubscribed() throws Exception {
        assertEquals(subject.isSubscribed(), false);
    }

    @Test
    public void isSubscribed_TRUE() throws Exception{
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(true);
        assertEquals(subject.isSubscribed(), true);
    }

    @Test
    public void removeNotification() throws Exception{
        subject.removeNotification();
        verify(notificationService).removeMonitoringNotification();
    }

    @Test
    public void showNotification_TRUE() throws Exception{
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(true);
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(true);
        when(sharedPrefsManager.getSavedServerName()).thenReturn(testServerName);

        subject.showNotification();
        verify(notificationService).showMonitoringNotification(testServerName);
    }

    @Test
    public void showNotification_FALSE() throws Exception{
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(false);
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(true);

        subject.showNotification();
        verify(notificationService, never()).showMonitoringNotification(anyString());
    }

    @Test
    public void getSavedServerName() throws Exception{
        when(sharedPrefsManager.getSavedServerName()).thenReturn(testServerName);

        assertEquals(subject.getSavedServer(), testServerName);
    }

}