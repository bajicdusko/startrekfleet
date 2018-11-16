package com.bajicdusko.startrekfleet.ship

import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.startrekfleet.R
import com.bajicdusko.startrekfleet.StarTrekFleetApp
import com.bajicdusko.startrekfleet.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_ship_class.activity_ship_class_list as shipsList

class ShipClassActivity : AppCompatActivity() {

  val tag = this::class.java.simpleName

  @VisibleForTesting(otherwise = PRIVATE) lateinit var shipsViewModel: ShipsViewModel
  @VisibleForTesting(otherwise = PRIVATE) lateinit var shipClass: ShipClass
  private lateinit var shipsAdapter: ShipsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_ship_class)

    shipClass = intent.getParcelableExtra<ShipClass>("shipClass")

    supportActionBar?.apply {
      title = shipClass.name
      setDisplayHomeAsUpEnabled(true)
    }

    shipsViewModel = ViewModelFactory(this@ShipClassActivity.applicationContext as StarTrekFleetApp).create(ShipsViewModel::class.java)

    shipsList.apply {
      layoutManager = LinearLayoutManager(this@ShipClassActivity)
      adapter = ShipsAdapter().also {
        shipsAdapter = it
      }
    }
  }

  public override fun onStart() {
    super.onStart()
    shipsViewModel.loadShipsPerShipClass(shipClass).observe(this, Observer { onResult(it) })
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun onResult(shipsPerClass: ResponseWrapper<List<Ship>>?) {
    shipsPerClass?.let {
      if (it.error != null) {
        onError(it.error!!)
      } else {
        onData(it.data)
      }
    }
  }

  @VisibleForTesting(otherwise = PRIVATE)
  internal fun onData(ships: List<Ship>?) {
    ships?.let {
      shipsAdapter.onData(ships)
    } ?: shipsAdapter.onClear()
  }

  private fun onError(error: Throwable) {
    Log.e(tag, "On loading ships per ship class", error)
  }
}