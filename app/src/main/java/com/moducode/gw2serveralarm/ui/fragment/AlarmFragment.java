package com.moducode.gw2serveralarm.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.moducode.gw2serveralarm.R;
import com.moducode.gw2serveralarm.dagger.ContextModule;
import com.moducode.gw2serveralarm.dagger.DaggerPresenterComponent;
import com.moducode.gw2serveralarm.dagger.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Jay on 2017-11-17.
 */

public class AlarmFragment extends MvpFragment<AlarmFragmentContract.View, AlarmFragmentContract.Actions>
        implements AlarmFragmentContract.View {

    @BindView(R.id.b_stop)
    Button bStop;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public AlarmFragmentContract.Actions createPresenter() {
        Context appContext = getActivity().getApplicationContext();

        PresenterComponent component = DaggerPresenterComponent.builder()
                .contextModule(new ContextModule(appContext))
                .build();

        return new AlarmFragmentPresenter(component.getAlarmServiceManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.startAlarmService();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.b_stop)
    public void onViewClicked() {
        presenter.stopAlarmService();
    }

}
