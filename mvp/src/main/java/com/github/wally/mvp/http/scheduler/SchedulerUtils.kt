package com.github.wally.mvp.http.scheduler


object SchedulerUtils {
    /**
     * 子线程转主线程
     */
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}