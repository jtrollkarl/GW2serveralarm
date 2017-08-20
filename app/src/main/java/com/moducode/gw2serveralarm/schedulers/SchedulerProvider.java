package com.moducode.gw2serveralarm.schedulers;

import io.reactivex.Scheduler;

/**
 * Created by Jay on 2017-08-20.
 */

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();

    Scheduler compute();
}
