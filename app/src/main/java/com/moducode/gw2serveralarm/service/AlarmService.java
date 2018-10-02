package com.moducode.gw2serveralarm.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Jay on 2017-11-25.
 */

public class AlarmService extends Service {


    private static final String CHANNEL_ALARM = "channel_alarm";
    private static final int CHANNEL_ALARM_ID = 312;

    @Inject
    SharedPrefsManager sharedPrefsManager;

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private AudioManager audioManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("Creating AlarmService..");

        PresenterComponent component = DaggerPresenterComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        component.injectAlarmService(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Starting AlarmService...");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForeground(CHANNEL_ALARM_ID, getNotification(getChannelId()));

        }else startForeground(CHANNEL_ALARM_ID, getNotification(""));

        if(grantAudioFocus()){
            playAlarm();
        }
        if(sharedPrefsManager.isVibrateEnabled() && vibrator.hasVibrator()){
            beginVibrate();
        }

        return START_NOT_STICKY;
    }

    private boolean grantAudioFocus(){
        return audioManager.requestAudioFocus(null, AudioManager.STREAM_ALARM, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == 1;
    }

    private void playAlarm(){
        if(mediaPlayer == null){
            Timber.w("MediaPlayer was null, recreating...");
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(sharedPrefsManager.getAlarmUri()));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Timber.e(e, "No alarm sound found?");
            stopMediaPlayer();
        }
    }

    private void beginVibrate() {
        vibrator.vibrate(new long[] {0, 500, 500, 500}, 2 );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getChannelId(){
        String NOTIFICATION_CHANNEL_ID = "com.moducode.GW2Alarm";
        String channelName = "Alarm Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.RED);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        return NOTIFICATION_CHANNEL_ID;
    }

    // TODO: 2017-11-29 move to notification service?
    private Notification getNotification(String channelId){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(getString(R.string.notif_slot_free))
                .setChannelId(channelId)
                .setPriority(Notification.PRIORITY_MAX)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setOngoing(true);

        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("Killing AlarmService and freeing resources");
        stopForeground(true);
        stopMediaPlayer();
        stopVibrator();
        audioManager.abandonAudioFocus(null);
    }

    private void stopMediaPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void stopVibrator(){
        if(vibrator != null){
            vibrator.cancel();
            vibrator = null;
        }
    }
}
