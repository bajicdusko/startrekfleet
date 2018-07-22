package com.bajicdusko.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
class GetAllShips constructor(private val shipsRepository: ShipsRepository) : UseCase<Unit, Ship> {

  override fun buildUseCase(param: Unit): LiveData<ResponseWrapper<List<Ship>>> =
      Transformations.map(shipsRepository.getAllShips()) { wrappedMapOfShipsToShipClasses ->
        if (wrappedMapOfShipsToShipClasses.error != null) {
          wrappedError { wrappedMapOfShipsToShipClasses.error!! }
        } else {
          val ships = mutableListOf<Ship>()

          wrappedMapOfShipsToShipClasses.data?.forEach { pair ->
            pair.value.forEach {
              it.shipClass = ShipClass(pair.key)
              ships.add(it)
            }
          }

          wrappedData { ships.toList() }
        }
      }
}