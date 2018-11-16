package com.bajicdusko.startrekfleet.shipclass

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.interactor.GetShipClasses

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
open class ShipClassesViewModel(private val getShipClasses: GetShipClasses) : ViewModel() {

  private val mediatorLiveData: MediatorLiveData<ResponseWrapper<List<ShipClass>>> = MediatorLiveData()
  private var shipClassesLiveData
      = getShipClasses.buildUseCase(Unit)

  init {
    addShipClassesSource()
  }

  private fun addShipClassesSource() {
    mediatorLiveData.addSource(shipClassesLiveData) {
      mediatorLiveData.value = it
    }
  }

  fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<ResponseWrapper<List<ShipClass>>>) {
    mediatorLiveData.observe(lifecycleOwner, observer)
  }

  fun refresh(){
    mediatorLiveData.removeSource(shipClassesLiveData)
    addShipClassesSource()
  }
}