package com.moducode.gw2serveralarm.ui.activity;

import com.moducode.gw2serveralarm.PresenterLogger;
import com.moducode.gw2serveralarm.service.AlarmServiceManager;
import com.moducode.gw2serveralarm.service.FcmSubscribeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Jay on 2017-11-25.
 */
public class AlarmActivityPresenterTest {

    @Mock
    PresenterLogger logger;

    @Mock
    FcmSubscribeService fcmSubscribeService;

    @Mock
    AlarmServiceManager alarmServiceManager;

    @Mock AlarmActivityContract.View view;

    @InjectMocks
    private AlarmActivityPresenter subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subject.attachView(view);
    }

    @Test
    public void startAlarmService() throws Exception {
        subject.startAlarmService();
        verify(fcmSubscribeService).unSubscribeFromTopic();
        verify(alarmServiceManager).startAlarmService();
    }

    @Test
    public void stopAlarmService() throws Exception {
        subject.stopAlarmService();
        verify(alarmServiceManager).stopAlarmService();
    }

}