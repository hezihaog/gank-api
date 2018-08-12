package com.github.wally.base.http.scheduler


object SchedulerUtils {
    /**
     * 子线程转主线程
     */
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}