package com.example.core.core.utils

import android.os.Handler
import android.os.Looper
import org.jetbrains.annotations.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class AppExecutors @VisibleForTesting constructor(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {

    companion object {
        private const val THREAD_COUNT = 3
    }

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )
    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}
