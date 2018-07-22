package com.bajicdusko.startrekfleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bajicdusko.data.interactor.GetShipClasses

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
