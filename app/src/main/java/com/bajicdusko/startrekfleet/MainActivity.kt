package com.bajicdusko.startrekfleet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.startrekfleet.base.ViewModelFactory
import com.bajicdusko.startrekfleet.ship.ShipClassActivity
import com.bajicdusko.startrekfleet.shipclass.ShipClassAdapter
import com.bajicdusko.startrekfleet.shipclass.ShipClassesViewModel
import com.bajicdusko.startrekfleet.view.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.activity_main_pullToRefresh as pullToRefresh
import kotlinx.android.synthetic.main.activity_main.activity_main_shipclasses as shipClassesList

@SuppressLint("LogNotTimber")
open class MainActivity : AppCompatActivity() {

  val tag: String = this::class.java.simpleName

  @VisibleForTesting(otherwise = PRIVATE)
  internal lateinit var shipClassesViewModel: ShipClassesViewModel
  @VisibleForTesting(otherwise = PRIVATE)
  internal lateinit var shipClassesAdapter: ShipClassAdapter

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportActionBar.apply {
      title = getString(R.string.shipClasses)
    }

    shipClassesViewModel = ViewModelFactory(
        this@MainActivity.applicationContext as StarTrekFleetApp).create(
        ShipClassesViewModel::class.java
    )

    shipClassesList.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = ShipClassAdapter().also {
        shipClassesAdapter = it
        it.listener = this@MainActivity
        addItemDecoration(SimpleDividerItemDecoration(this@MainActivity))
      }
    }

    pullToRefresh.setOnRefreshListener {
      shipClassesViewModel.refresh()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(tag, "$this destroyed")
  }

  public override fun onStart() {
    super.onStart()
    shipClassesViewModel.observe(this, Observer { renderResult(it) })
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun renderResult(result: ResponseWrapper<List<ShipClass>>?) {
    pullToRefresh.isRefreshing = false
    result?.let {
      if (it.error != null) {
        onError(it.error!!)
      } else {
        onData(it.data)
      }
    }
  }

  fun showShips(shipClass: ShipClass) {
    val intent = Intent(this, ShipClassActivity::class.java)
    intent.putExtra("shipClass", shipClass)
    startActivity(intent)
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