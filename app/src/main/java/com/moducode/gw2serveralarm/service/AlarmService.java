package com.moducode.gw2serveralarm.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by Jay on 2017-11-25.
 */

public class AlarmService extends Service {

    private static final String TAG = "AlarmService";

    private static final String CHANNEL_ALARM = "channel_alarm";
    private static final int CHANNEL_ALARM_ID = 312;

    @Inject
    SharedPrefsManager sharedPrefsManager;

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Creating AlarmService..");

        PresenterComponent component = DaggerPresenterComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        component.injectAlarmService(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting AlarmService...");

        startForeground(CHANNEL_ALARM_ID, getNotification());
        playAudio();

        return START_NOT_STICKY;
    }

    private void playAudio(){
        try {
            mediaPlayer.setDataSource(this, Uri.parse(sharedPrefsManager.getAlarmUri()));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "No alarm sound found?", e);
            stopMediaPlayer();
        }
    }

    private Notification getNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ALARM)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.notif_monitor_title))
                .setContentText(getString(R.string.notif_slot_free))
                .setChannelId(CHANNEL_ALARM)
                .setPriority(Notification.PRIORITY_MAX)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setOngoing(true);

        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Killing AlarmService and freeing resources");
        stopForeground(true);
        stopMediaPlayer();
    }

    private void stopMediaPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
