package com.bajicdusko.androiddomain

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * Created by Dusko Bajic on 07.09.18.
 * GitHub @bajicdusko
 */
class MainLooperExecutor : Executor {

  companion object {
    var mainHandler: Handler? = null
  }


  override fun execute(runnable: Runnable?) {

    if(mainHandler == null) {
      mainHandler = Handler(Looper.getMainLooper())
    }

    mainHandler?.post(runnable)
  }
}