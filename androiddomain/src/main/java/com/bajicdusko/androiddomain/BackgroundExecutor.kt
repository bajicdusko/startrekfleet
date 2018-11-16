package com.bajicdusko.androiddomain

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by Dusko Bajic on 28.07.18.
 * GitHub @bajicdusko
 */

val availableProcessors = Runtime.getRuntime().availableProcessors()

class BackgroundExecutor private constructor() {

  companion object {
    val threadPool: BackgroundExecutor by lazy {
      BackgroundExecutor()
    }

    var executorService = initThreadPoolExecutor()

    fun initThreadPoolExecutor(): ThreadPoolExecutor {
      return ThreadPoolExecutor(
          availableProcessors,
          availableProcessors * 2,
          10,
          TimeUnit.SECONDS,
          LinkedBlockingQueue<Runnable>(),
          SimpleThreadFactory()
      )
    }
  }

  fun execute(fn: () -> Unit, callback: (() -> Unit)?) {
    executorService.execute {
      fn()
      callback?.let {
        MainLooperExecutor().execute(it)
      }
    }
  }
}

class SimpleThreadFactory : ThreadFactory {
  override fun newThread(p0: Runnable?): Thread = Thread(p0)
}