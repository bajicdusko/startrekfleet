package com.bajicdusko.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipsRepository
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.androiddomain.wrappedError

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
class GetShipsPerShipClass constructor(
    private val shipsRepository: ShipsRepository) : UseCase<ShipClass, Ship> {

  override fun buildUseCase(param: ShipClass): LiveData<ResponseWrapper<List<Ship>>> {
    return shipsRepository.getShipsPerShipClass(param)
  }
}