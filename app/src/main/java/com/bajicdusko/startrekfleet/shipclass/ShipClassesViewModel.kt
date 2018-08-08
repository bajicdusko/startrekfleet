package com.bajicdusko.startrekfleet.shipclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.interactor.GetShipClasses

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
open class ShipClassesViewModel(private val getShipClasses: GetShipClasses) : ViewModel() {

  fun onLoad(): LiveData<ResponseWrapper<List<ShipClass>>> {
    return getShipClasses.buildUseCase(Unit)
  }
}