package com.bajicdusko.startrekfleet.ship

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.startrekfleet.R
import com.bajicdusko.startrekfleet.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_ship_class.activity_ship_class_list as shipsList

class ShipClassActivity : AppCompatActivity() {

  val tag = this::class.java.simpleName
  private lateinit var shipsViewModel: ShipsViewModel
  private lateinit var shipsAdapter: ShipsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_ship_class)

    val shipClass = intent.getParcelableExtra<ShipClass>("shipClass")

    shipsViewModel = ViewModelFactory().create(ShipsViewModel::class.java)
    lifecycle.addObserver(shipsViewModel)

    shipsList.apply {
      layoutManager = LinearLayoutManager(this@ShipClassActivity)
      adapter = ShipsAdapter().also {
        shipsAdapter = it
      }
    }

    load(shipClass)
  }

  private fun load(shipClass: ShipClass) {
    shipsViewModel.loadShipsPerShipClass(shipClass).observe(this, Observer { onResult(it) })
  }

  private fun onResult(shipsPerClass: ResponseWrapper<List<Ship>>?) {
    shipsPerClass?.let {
      if (it.error != null) {
        onError(it.error!!)
      } else {
        onData(it.data)
      }
    }
  }

  private fun onData(ships: List<Ship>?) {
    ships?.let {
      shipsAdapter.onData(ships)
    } ?: shipsAdapter.onClear()
  }

  private fun onError(error: Throwable) {
    Log.e(tag, "On loading ships per ship class", error)
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycle.removeObserver(shipsViewModel)
  }
}