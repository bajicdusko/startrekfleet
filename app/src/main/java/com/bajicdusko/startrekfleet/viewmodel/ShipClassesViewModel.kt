package com.bajicdusko.startrekfleet.viewmodel

import androidx.lifecycle.*
import com.bajicdusko.data.interactor.GetShipClasses
import com.bajicdusko.startrekfleet.StarTrekFleetApp

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
class ShipClassesViewModel(val getShipClasses: GetShipClasses) : ViewModel(), LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onLoad() = getShipClasses.buildUseCase(Unit)
}