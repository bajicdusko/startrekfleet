package com.bajicdusko.startrekfleet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.startrekfleet.base.ViewModelFactory
import com.bajicdusko.startrekfleet.ship.ShipClassActivity
import com.bajicdusko.startrekfleet.shipclass.ShipClassAdapter
import com.bajicdusko.startrekfleet.shipclass.ShipClassesViewModel
import com.bajicdusko.startrekfleet.view.SimpleDividerItemDecoration
import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.activity_main.activity_main_shipclasses as shipClassesList

class MainActivity : AppCompatActivity() {

  val tag = this::class.java.simpleName
  private lateinit var shipClassesViewModel: ShipClassesViewModel
  private lateinit var shipClassesAdapter: ShipClassAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportActionBar.apply {
      title = getString(R.string.shipClasses)
    }

    shipClassesViewModel = ViewModelFactory().create(ShipClassesViewModel::class.java)

    shipClassesList.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = ShipClassAdapter().also {
        shipClassesAdapter = it
        it.listener = this@MainActivity
        addItemDecoration(SimpleDividerItemDecoration(this@MainActivity))
      }
    }
  }

  override fun onStart() {
    super.onStart()
    shipClassesViewModel.onLoad().observe(this, Observer { renderResult(it) })
  }

  private fun renderResult(result: ResponseWrapper<List<ShipClass>>?) {
    result?.let {
      if (it.error != null) {
        onError(it.error!!)
      } else {
        onData(it.data)
      }
    }
  }

  fun showShips(shipClass: ShipClass) {
    startActivity<ShipClassActivity>("shipClass" to shipClass)
  }

  private fun onData(data: List<ShipClass>?) {
    data?.let {
      shipClassesAdapter.loadData(it)
    } ?: shipClassesAdapter.clearData()
  }

  private fun onError(error: Throwable) {
    Log.e(tag, "On ship classes loading", error)
  }
}