package com.moducode.gw2serveralarm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jay on 2017-11-17.
 */

//no fragment here as the alarm will always take up the entire display area

public class AlarmActivity extends MvpActivity<AlarmActivityContract.View, AlarmActivityContract.Actions>
        implements AlarmActivityContract.View {

    @BindView(R.id.tv_stop_msg)
    TextView tvStopMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    @NonNull
    @Override
    public AlarmActivityContract.Actions createPresenter() {
        Context appContext = getApplicationContext();

        PresenterComponent component = DaggerPresenterComponent.builder()
                .contextModule(new ContextModule(appContext))
                .build();

        return new AlarmActivityPresenter(component.getFcmSubscribeService(),
                component.getAlarmServiceManager());
    }


    public static Intent newInstance(Context context) {
        Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
        alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return alarmActivityIntent;
    }

    @Override
    public void showServerName(String serverName) {
        tvStopMsg.setText(getString(R.string.tv_stop_msg_server, serverName));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startAlarmService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopAlarmService();
    }

    @OnClick(R.id.tv_stop_msg)
    public void onViewClicked() {
        presenter.stopAlarmService();
        finish();
    }

}
