package com.bajicdusko.startrekfleet.ship

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.interactor.GetShipsPerShipClass

class ShipsViewModel(private val getShipsPerShipClass: GetShipsPerShipClass) : ViewModel(), LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun loadShipsPerShipClass(shipClass: ShipClass) = getShipsPerShipClass.buildUseCase(shipClass)
}