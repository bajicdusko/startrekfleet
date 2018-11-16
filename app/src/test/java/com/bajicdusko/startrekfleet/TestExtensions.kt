package com.bajicdusko.startrekfleet

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentController

/**
 * Created by Dusko Bajic on 08.09.18.
 * GitHub @bajicdusko
 */


fun AppCompatActivity.mockActivityInternals(
    appCompatDelegateMock: AppCompatDelegate,
    fragmentControllerMock: FragmentController
) {
  Class.forName(
      "androidx.appcompat.app.AppCompatActivity"
  ).getDeclaredField("mDelegate").apply {
    isAccessible = true
    set(this@mockActivityInternals, appCompatDelegateMock)
  }

  Class.forName(
      "androidx.fragment.app.FragmentActivity"
  ).getDeclaredField("mFragments").apply {
    isAccessible = true
    set(this@mockActivityInternals, fragmentControllerMock)
  }
}