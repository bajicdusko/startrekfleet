package com.bajicdusko.androiddomain

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

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
  }

  private val executorService = ThreadPoolExecutor(
      availableProcessors,
      availableProcessors * 2,
      10,
      TimeUnit.SECONDS,
      LinkedBlockingQueue<Runnable>(),
      SimpleThreadFactory()
  )

  fun execute(fn: () -> Unit, callback: (() -> Unit)?) {
    executorService.execute {
      fn()
      callback?.let {
        Handler(Looper.getMainLooper()).post(it)
      }
    }
  }
}

private class SimpleThreadFactory : ThreadFactory {
  override fun newThread(p0: Runnable?): Thread = Thread(p0)
}