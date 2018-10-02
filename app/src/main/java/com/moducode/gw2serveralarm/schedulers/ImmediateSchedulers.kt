package com.moducode.gw2serveralarm.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jay on 2017-08-20.
 */

class ImmediateSchedulers : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()
    
    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun compute(): Scheduler = Schedulers.trampoline()

}
