package com.moducode.gw2serveralarm.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @InjectMocks
    private FcmSubscribeServiceImpl subject;

    private static final String topicId = "testTopic";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void subscribeToTopic_NOTIFICATION_TRUE() throws Exception {
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(true);

        subject.subscribeToTopic(topicId);

        verify(fcmMessagingDelegate).subscribeToTopic(topicId);
        verify(sharedPrefsManager).saveServer(topicId);
        verify(notificationService).showMonitoringNotification();
    }

    @Test
    public void subscribeToTopic_NOTIFICATION_FALSE() throws Exception {
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(false);

        subject.subscribeToTopic(topicId);

        verify(fcmMessagingDelegate).subscribeToTopic(topicId);
        verify(sharedPrefsManager).saveServer(topicId);
    }

    @Test
    public void unSubscribeFromTopic() throws Exception {
        when(sharedPrefsManager.getSavedServer()).thenReturn(topicId);

        subject.unSubscribeFromTopic();

        verify(sharedPrefsManager).clearSavedTopic();
        verify(fcmMessagingDelegate).unsubscribeFromTopic(topicId);
        verify(notificationService).removeMonitoringNotification();
    }

    @Test
    public void isSubscribed() throws Exception {
        assertEquals(subject.isSubscribed(), anyBoolean());
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

        subject.showNotification();
        verify(notificationService).showMonitoringNotification();
    }

    @Test
    public void showNotification_FALSE() throws Exception{
        when(sharedPrefsManager.isNotificationEnabled()).thenReturn(false);
        when(sharedPrefsManager.isMonitoringServer()).thenReturn(true);

        subject.showNotification();
        verify(notificationService, never());
    }
}