package com.moducode.gw2serveralarm.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jay on 2017-08-20.
 */

public class ImmediateSchedulers implements SchedulerProvider {

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.trampoline();
    }
}
