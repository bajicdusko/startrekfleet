package com.bajicdusko.startrekfleet.shipclass

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.bajicdusko.data.interactor.GetShipClasses

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
class ShipClassesViewModel(private val getShipClasses: GetShipClasses) : ViewModel() {

  fun onLoad() = getShipClasses.buildUseCase(Unit)
}