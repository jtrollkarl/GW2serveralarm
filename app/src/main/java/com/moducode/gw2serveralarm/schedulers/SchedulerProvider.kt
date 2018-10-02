package com.moducode.gw2serveralarm.schedulers

import io.reactivex.Scheduler

/**
 * Created by Jay on 2017-08-20.
 */

interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler

    fun compute(): Scheduler
}
