package suts.desigenpattens.learnnote.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by sutingshuai on 2019-08-26
 * Describe:
 */
public interface SchedulerProvider {
    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}
