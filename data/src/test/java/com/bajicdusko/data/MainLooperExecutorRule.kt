package com.bajicdusko.data

import android.os.Handler
import com.bajicdusko.androiddomain.BackgroundExecutor
import com.bajicdusko.androiddomain.MainLooperExecutor
import com.bajicdusko.androiddomain.availableProcessors
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by Dusko Bajic on 07.09.18.
 * GitHub @bajicdusko
 */
class MainLooperExecutorRule : TestWatcher() {
  override fun apply(base: Statement?, description: Description?): Statement {
    return object : Statement() {
      override fun evaluate() {
        MainLooperExecutor.mainHandler = Handler()


        BackgroundExecutor.executorService = ThreadPoolExecutor(
            availableProcessors,
            availableProcessors * 2,
            10,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>(),
            RejectedExecutionHandler { runnable, _ -> runnable.run() }
        )

        try {
          base?.evaluate()
        } finally {
          MainLooperExecutor.mainHandler = null
          BackgroundExecutor.executorService = BackgroundExecutor.initThreadPoolExecutor()
        }
      }
    }
  }
}