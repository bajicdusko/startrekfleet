package com.bajicdusko.startrekfleet

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * Created by Dusko Bajic on 09.08.18.
 * GitHub @bajicdusko
 */

class StarTrekFleetInstrumentationRunner : AndroidJUnitRunner(){
  override fun newApplication(
      cl: ClassLoader?,
      className: String?,
      context: Context?
  ): Application {
    return super.newApplication(cl, TestStarTrekFleetApp::class.java.name, context)
  }
}